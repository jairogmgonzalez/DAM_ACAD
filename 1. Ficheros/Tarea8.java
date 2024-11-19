
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author jairo
 */
class GestorRegistros {

    private File f; // Archivo donde se almacenan los registros
    Map<String, Integer> camposYLongitudes; // Mapa de nombres de los campos y sus longitudes
    private long longReg; // Longitud de un registro
    private long numReg = 0; // Número de registros en el archivo

    // Constructor que inicializa el archivo y los atributos
    GestorRegistros(String ruta, Map<String, Integer> entradaCamposLongitud) throws IOException {
        this.f = new File(ruta); // Se inicializa el archivo
        camposYLongitudes = new LinkedHashMap<>(); // Se inicializa el mapa de campos

        // Se itera sobre los campos para establecer sus longitudes
        for (String campo : entradaCamposLongitud.keySet()) {
            Integer longitud = entradaCamposLongitud.get(campo);
            this.camposYLongitudes.put(campo, longitud); // Se guarda el campo y su longitud
            this.longReg += longitud; // Se suma la longitud de cada campo para calcular la longitud total del registro
        }

        // Si el archivo ya existe, se calcula el número de registros
        if (this.f.exists()) {
            this.numReg = this.f.length() / this.longReg; // Se calcula el número de registros basándose en el tamaño del archivo
        }
    }

    // Método para obtener el total de registros
    public long getNumReg() {
        return numReg;
    }

    // Método para insertar un registro al final del fichero
    public void insertar() throws IOException {
        modificar(this.numReg++);
    }

    // Método para modificar un registro en una posición dada
    public void modificar(long posicion) {
        if (!posicionValida(posicion)) { // Se verifica si la posición es válida
            System.out.println("Posicion no valida"); // Mensaje de error si no es válida
            return; // Se sale del método si la posición no es válida
        }

        Scanner sn_teclado = new Scanner(System.in); // Se inicializa el escáner para la entrada del usuario
        Map<String, String> registro = new LinkedHashMap<>(); // Se crea un mapa para almacenar el nuevo registro

        System.out.println("Introduce el valor de cada campo: "); // Mensaje para solicitar los campos

        // Se itera sobre los campos para obtener los valores del usuario
        for (String campo : this.camposYLongitudes.keySet()) {
            Integer longitudCampo = this.camposYLongitudes.get(campo); // Se obtiene la longitud del campo
            System.out.print("{" + campo + "}: "); // Se muestra el campo del que se va a pedir el valor
            String valor = sn_teclado.nextLine(); // Se lee la entrada del usuario
            // Se valida la longitud del valor ingresado
            while (valor.length() > longitudCampo) {
                System.out.println("La longitud maxima para el valor es " + longitudCampo); // Mensaje de error
                System.out.print("{" + campo + "}: "); // Se solicita el valor nuevamente
                valor = sn_teclado.nextLine(); // Se lee la nueva entrada
            }
            registro.put(campo, valor); // Se guarda el valor en el mapa
        }

        // Se abre el archivo para escribir el registro en el fichero
        try (RandomAccessFile ficheroRandom = new RandomAccessFile(this.f, "rws")) {
            ficheroRandom.seek(posicion * this.longReg); // Se posiciona en la ubicación del registro a modificar

            // Se escribe cada campo en la posición correcta del archivo
            for (String campo : this.camposYLongitudes.keySet()) {
                Integer longitud = this.camposYLongitudes.get(campo); // Se obtiene la longitud del campo
                String valor = registro.get(campo); // Se obtiene el valor del campo del mapa

                if (valor == null) { // Si el valor es nulo, se establece como vacío
                    valor = "";
                }

                // Se formatea el valor para que ocupe la longitud correcta
                String formaCampoValor = String.format("%1$-" + longitud + "s", valor);
                ficheroRandom.write(formaCampoValor.getBytes("UTF-8"), 0, longitud); // Se escribe el valor en el archivo
                System.out.println("Registro modificado"); // Mensaje para confirmar que se ha modificado el registro
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado: " + ex.toString()); // Mensaje de error si el archivo no se encuentra
        } catch (IOException ex) {
            System.out.println("Error insertando registro: " + ex.toString()); // Mensaje de error al insertar el registro
        }
    }

    // Método para leer un registro en una posición dada
    public Map<String, String> leer(long posicion) {
        Map<String, String> registro = new LinkedHashMap<>(); // Se crea un mapa para almacenar el registro leído

        try (RandomAccessFile ficheroRandom = new RandomAccessFile(this.f, "r")) {
            ficheroRandom.seek(posicion * this.longReg); // Se posiciona en la ubicación del registro a leer

            // Se itera sobre cada campo para leer su valor
            for (String campo : this.camposYLongitudes.keySet()) {
                Integer longitud = this.camposYLongitudes.get(campo); // Se obtiene la longitud del campo
                byte[] bytes = new byte[longitud]; // Se crea un array de bytes para almacenar el valor
                ficheroRandom.read(bytes); // Se leen los bytes del archivo y se almacenan en el array
                String valor = new String(bytes, "UTF-8").trim(); // Se convierte los bytes a String y se eliminan los espacios

                registro.put(campo, valor); // Se guarda el valor en el mapa
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado: " + ex.toString()); // Mensaje de error si el archivo no se encuentra
        } catch (IOException ex) {
            System.out.println("Error leyendo registro: " + ex.toString()); // Mensaje de error al leer el registro
        }

        return registro; // Se devuelve el registro leído
    }

    // Método para verificar si una posición es válida
    public boolean posicionValida(long posicion) {
        return posicion >= 0 && posicion < this.numReg; // Se verifica que la posición esté dentro del rango válido
    }

    // Método para mostrar un registro leido en consola
    public void mostrarRegistro(Map<String, String> registro) {
        System.out.println("----- Registro leido -----");
        // Se itera sobre cada campo del registro para mostrar cada campo y su valor
        for (String campo : registro.keySet()) {
            Object valor = registro.get(campo); // Se obtiene el valor del campo
            System.out.print("{" + campo + "}:" + valor + " "); // Se muestra el campo y su valor
        }
        System.out.println();
    }

// Método para mostrar el menú de opciones en consola
    public void mostrarMenu() {
        System.out.println("----- GESTOR REGISTROS -----");
        System.out.println("Elige una opcion:");
        System.out.println("1. Insertar un nuevo registro en el fichero");
        System.out.println("2. Modificar un registro en el fichero.");
        System.out.println("3. Leer un registro");
        System.out.println("0. Salir");
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sn_teclado = new Scanner(System.in); 
        String ruta = "/media"; 

        if (args.length > 0) {
            ruta = args[0]; 
        }

        GestorRegistros gestor = null; // Se declara la variable para el gestor de registros

        // Se intenta inicializar el gestor de registros
        try {
            Map<String, Integer> camposLongitud = new LinkedHashMap<>(); // Se crea un mapa para almacenar los campos y sus longitudes
            // Se definen los campos y sus longitudes
            camposLongitud.put("NOMBRE", 32);
            camposLongitud.put("EDAD", 3);
            camposLongitud.put("DNI", 9);
            camposLongitud.put("FECHA_NACIMIENTO", 10);
            camposLongitud.put("DIRECCION", 40);
            camposLongitud.put("CP", 5);

            // Se inicializa el gestor de registros con su constructor
            gestor = new GestorRegistros(ruta, camposLongitud);
        } catch (IOException e) {
            System.out.println("Error al inicializar el gestor: " + e.getMessage()); // Mensaje de error si falla la inicialización
            return; // Se sale del método si hay un error
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); // Mensaje de errors
            return; // Se sale del método si hay un error
        }

        // Bbucle infinito para mostrar el menú y gestionar las opciones
        while (true) {
            gestor.mostrarMenu(); // Se muestra el menú de opciones
            int opcion = sn_teclado.nextInt(); // Se lee la opción elegida 

            // Se verifica si la opción elegida es válida
            if (opcion < 0 || opcion > 3) {
                System.out.println("Introduce una opción válida"); // Mensaje de error si la opción no es válida
                continue; // Se vuelve a mostrar el menú
            }

            // Switch para gestionar las diferentes opciones elegidas
            switch (opcion) {
                case 1: // Opción para insertar un nuevo registro
                    try {
                        gestor.insertar(); // Se llama al método para insertar
                    } catch (IOException e) {
                        System.out.println("Error insertando en el fichero: " + e.toString()); // Mensaje de error si falla al insertar el registro
                    }
                    break;

                case 2: // Opción para modificar un registro
                    System.out.println("Elige la posicion del registro que deseas modificar el registro"); // Se pide la posición del registro
                    long posicion = sn_teclado.nextLong(); // Se lee la posición introducida
                    // Se verifica si la posición es válida
                    if (!gestor.posicionValida(posicion)) {
                        System.out.println("No existe la posicion " + posicion + " en el fichero"); // Mensaje de error si la posición no es válida
                    } else {
                        gestor.modificar(posicion); // Se llama al método para modificar el registro
                    }
                    break;

                case 3: // Opción para leer un registro
                    System.out.println("Elige la posicion que deseas leer"); // Se pide la posición del registro
                    posicion = sn_teclado.nextLong(); // Se lee la posición introducida
                    // Se verifica si la posición es válida
                    if (!gestor.posicionValida(posicion)) {
                        System.out.println("No existe la posicion " + posicion + " en el fichero"); // Mensaje de error si la posición no es válida
                    } else {
                        Map<String, String> registro = new LinkedHashMap<>(); // Se crea un mapa para almacenar el registro leído
                        registro = gestor.leer(posicion); // Se llama al método para leer el registro
                        System.out.println();
                        // Se verifica si el registro se ha leído correctamente
                        if (registro != null) {
                            gestor.mostrarRegistro(registro); // Se muestra el registro leído
                        } else {
                            System.out.println("No se pudo leer el registro."); // Mensaje de error si no se puede leer el registro
                        }
                    }
                    break;

                case 0: // Opción para salir del programa
                    return; // Se sale del método y termina la ejecución
            }
            System.out.println();
        }
    }
}
