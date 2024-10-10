/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tarea9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author jairo
 */
public class GestorRegistrosLB {

    private File f; // Archivo donde se almacenan los registros
    Map<String, Integer> camposYLongitudes; // Mapa de nombres de los campos y sus longitudes
    private long longReg; // Longitud de un registro
    private long numReg = 0; // Número de registros en el archivo

    // Constructor que inicializa el archivo y los atributos
    GestorRegistrosLB(String ruta, Map<String, Integer> entradaCamposLongitud) throws IOException {
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

    // Método para actualizar el número de registros total
    public void actualizarNumReg() {
        this.numReg = this.f.length() / this.longReg;
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

    // Método para verificar si un campo existe
    public boolean existeCampo(String nombreColumna) {
        return this.camposYLongitudes.containsKey(nombreColumna); // Se verifica que el registro contiene el campo
    }

    // Método para verificar si la longitud del valor es correcta para un campo
    public boolean longitudValor(String columna, String valor) {
        if (this.camposYLongitudes.containsKey(columna)) { // Se verifica que el registro contiene el campo
            int longitudCampo = this.camposYLongitudes.get(columna); // Se guarda la longitud que debe tener el campo
            return valor.length() <= longitudCampo; // Se devuelve true si la longitud del valor es correcta
        }

        return false; // Se devuelve falso si es mayor a la correcta
    }

    // Método para validar la longitud de todos los valores en un registro
    public boolean longitudValores(Map<String, String> registro) {
        for (String campo : registro.keySet()) { // Se itera sobre ada campo del registro
            int longitudValor = registro.get(campo).length(); // Se obtiene la longitud del valor
            // Se verifica si la longitud del valor es mayor que la longitud correcta
            if (longitudValor > this.camposYLongitudes.get(campo)) {
                return false; // Se devuelve falso si es mayor a la correcta
            }
        }
        return true; // Se devuelve true si la longitud del valor es correcta
    }

    // Método para seleccionar un valor específico de un campo en un registro
    public String selectCampo(long numRegistro, String nombreColumna) {
        if (!posicionValida(numRegistro) || !existeCampo(nombreColumna)) { // Se verifica si la posición y el campo son válidos
            System.out.println("Posicion o nombre de columna no validos"); // Mensaje de error
            return null; // Se devuelve null si no son válidos
        }

        try (RandomAccessFile ficheroRandom = new RandomAccessFile(this.f, "r")) {
            ficheroRandom.seek(numRegistro * this.longReg); // Se posiciona en la ubicación del registro

            // Se itera sobre cada campo para buscar el valor del campo especificado
            for (String campo : this.camposYLongitudes.keySet()) {
                if (campo.equalsIgnoreCase(nombreColumna)) { // Se compara sin tener en cuenta mayúsculas
                    Integer longitud = this.camposYLongitudes.get(campo); // Se obtiene la longitud del campo
                    byte[] bytes = new byte[longitud]; // Se crea un array de bytes para almacenar el valor
                    ficheroRandom.read(bytes); // Se leen los bytes del archivo
                    String valor = new String(bytes, "UTF-8").trim(); // Se convierte los bytes a String y se eliminan los espacios
                    return valor; // Se devuelve el valor del campo encontrado
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado: " + ex.toString()); // Mensaje de error si el archivo no se encuentra
        } catch (IOException ex) {
            System.out.println("Error leyendo el campo: " + ex.toString()); // Mensaje de error al leer el registro
        }

        return null; // Se devuelve null si no se encuentra el valor
    }

    // Método para seleccionar todos los valores de una columna específica
    public ArrayList<String> selectColumna(String nombreColumna) {
        if (!existeCampo(nombreColumna)) { // Se verifica si el nombre de la columna es válido
            System.out.println("Nombre de columna no valida"); // Mensaje de error si la columna no es válida
            return null; // Se devuelve null si la columna no es válida
        }

        ArrayList<String> valores = new ArrayList<>(); // Se crea una lista para almacenar los valores de la columna

        // Se itera sobre todos los registros del fuichero para obtener los valores de la columna especificada
        for (long i = 0; i < this.numReg; i++) {
            String valor = selectCampo(i, nombreColumna); // Se selecciona el valor del campo en la posición actual
            if (valor != null) {
                valores.add(valor); // Se agrega el valor a la lista si no es nulo
            }
        }

        return valores; // Se devuelve la lista de valores de la columna
    }

    // Método para seleccionar todos los valores de una fila como una lista
    public ArrayList<String> selectRowList(long numRegistro) {
        if (!posicionValida(numRegistro)) { // Se verifica si la posición es válida
            System.out.println("Posicion no valida"); // Mensaje de error si la posición no es válida
            return null; // Se devuelve null si la posición no es válida
        }

        ArrayList<String> fila = new ArrayList<>(); // Se crea una lista para almacenar los valores de la fila

        try (RandomAccessFile ficheroRandom = new RandomAccessFile(this.f, "r")) {
            ficheroRandom.seek(numRegistro * this.longReg); // Se posiciona en la ubicación del registro

            // Se itera sobre los campos para leer sus valores
            for (String campo : this.camposYLongitudes.keySet()) {
                Integer longitud = this.camposYLongitudes.get(campo); // Se obtiene la longitud del campo
                byte[] bytes = new byte[longitud]; // Se crea un array de bytes para almacenar el valor
                ficheroRandom.read(bytes); // Se leen los bytes del archivo
                String valor = new String(bytes, "UTF-8").trim(); // Se convierte los bytes a String y se eliminan los espacios
                fila.add(valor); // Se agrega el valor a la lista de la fila
            }

            return fila; // Se devuelve la lista de la fila

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado: " + ex.toString()); // Manejo de errores si el archivo no se encuentra
        } catch (IOException ex) {
            System.out.println("Error leyendo registro: " + ex.toString()); // Manejo de errores de entrada/salida
        }

        return null; // Se devuelve null si ocurre un error al leer
    }

    // Método para seleccionar una fila como un mapa de columna-valor
    public Map<String, String> selectRowMap(long posicion) {
        if (!posicionValida(posicion)) { // Se verifica si la posición es válida
            System.out.println("Posicion no valida"); // Mensaje de error si la posición no es válida
            return null; // Se devuelve null si la posición no es válida
        }

        Map<String, String> columnaValores = new LinkedHashMap<>(); // Se crea un mapa para almacenar los valores de la fila
        ArrayList<String> valores = selectRowList(posicion); // Se obtiene los valores de la fila

        int contador = 0; // Contador para iterar sobre los valores

        // Se itera sobre cada campo para llenar el mapa de columna-valor
        for (String columna : this.camposYLongitudes.keySet()) {
            columnaValores.put(columna, valores.get(contador)); // Se agrega el valor de la columna al mapa
            contador++; // Se incrementa el contador
        }

        return columnaValores; // Se devuelve el mapa de valores de la fila
    }

    // Método para actualizar un registro a partir de un mapa de columna-valor
    public void update(long posicion, Map<String, String> registro) {
        if (!posicionValida(posicion)) { // Se verifica si la posición es válida
            System.out.println("Posicion no valida"); // Mensaje de error si no es válida
            return; // Se sale del método si la posición no es válida
        }

        if (!longitudValores(registro)) { // Se verifica si los valores del registro son válidos
            System.out.println("El valor introducido tiene una longitud mayor a la establecida"); // Mensaje de error si hay un valor demasiado largo
        }

        try (RandomAccessFile ficheroRandom = new RandomAccessFile(this.f, "rws")) {
            ficheroRandom.seek(posicion * this.longReg); // Se posiciona en la ubicación del registro

            // Se itera sobre cada campo para escribir sus valores
            for (String campo : this.camposYLongitudes.keySet()) {
                Integer longitud = this.camposYLongitudes.get(campo); // Se obtiene la longitud del campo
                String valor = registro.get(campo); // Se obtiene el valor del campo del mapa

                if (valor == null) { // Si el valor es nulo, se establece como vacío
                    valor = "";
                }

                // Se formatea el valor para que ocupe la longitud correcta
                String formaCampoValor = String.format("%1$-" + longitud + "s", valor);
                ficheroRandom.write(formaCampoValor.getBytes("UTF-8"), 0, longitud); // Se escribe el valor en el archivo
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado: " + ex.toString()); // Mensaje de error si el archivo no se encuentra
        } catch (IOException ex) {
            System.out.println("Error insertando registro: " + ex.toString()); // Mensaje de error al insertar
        }
    }

    // Método para actualizar un registro en una posición dada
    public void update(long posicion, String campo, String valorNuevo) {
        if (!posicionValida(posicion) || !existeCampo(campo) || !longitudValor(campo, valorNuevo)) {
            System.out.println("Hay un error con uno de los parametros"); // Mensaje de error si hay un parámetro inválido
            return; // Se sale del método si hay un error
        }

        if (valorNuevo == null) {
            System.out.println("El valor introducido por parametro es null, el registro se actualizara con el valor introducido por parametro vacio"); // Mensaje informativo
            valorNuevo = ""; // Se establece el valor como vacío
        }

        Map<String, String> registroOriginal = leer(posicion); // Se lee el registro original
        Map<String, String> registroActualizado = new LinkedHashMap<>(); // Se crea un mapa para almacenar el registro actualizado

        // Se itera sobre cada columna del registro original
        for (String columna : registroOriginal.keySet()) {
            if (columna.equalsIgnoreCase(campo)) { // Se verifica si se encuentra el campo
                registroActualizado.put(columna, valorNuevo); // Se actualiza el campo con el nuevo valor
            } else { // Si no es el campo del valor que se desea actualizar
                String valor = registroOriginal.get(columna); // Se obtiene el valor del campo
                registroActualizado.put(columna, valor); // Se guarda el valor del campo
            }
        }

        update(posicion, registroActualizado); // Se llama al método update para guardar el registro actualizado
    }

    // Método para borrar un registro del fichero
    public void delete(long posicion) {
        if (!posicionValida(posicion)) { // Se verifica si la posición es válida
            System.out.println("Posicion no valida"); // Mensaje de error si no es válida
            return; // Se sale del método si la posición no es válida
        }

        ArrayList<Map<String, String>> contenidoFicheroActualizado = new ArrayList<>(); // Se crea una lista para almacenar el contenido actualizado

        try (RandomAccessFile ficheroRandom = new RandomAccessFile(this.f, "rws")) {
            // Se itera sobre los registros existentes
            for (long i = 0; i < this.numReg; i++) {
                if (i == posicion) { // Si se encuentra la posición a eliminar, se salta la iteración
                    continue; // Se salta a la siguiente iteración
                } else {  // Si no es la posición que queremos eliminar
                    Map<String, String> registro = leer(i); // Se lee el registro actual
                    contenidoFicheroActualizado.add(registro); // Se guarda el registro actual
                }
            }

            long contadorReg = 0; // Contador para las posiciones de los registros

            // Se itera sobre cada registro del contenido ya actualizado para volver a escribirlo en el fichero
            for (Map<String, String> registro : contenidoFicheroActualizado) {
                update(contadorReg, registro); // Se actualiza el registro en la posición correspondiente
                contadorReg++; // Se incrementa el contador de registros (posicion)
            }

            actualizarNumReg(); // Se actualiza el número de registros del fichero

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado: " + ex.toString()); // Mensaje de error si el archivo no se encuentra
        } catch (IOException ex) {
            System.out.println("Error insertando registro: " + ex.toString()); // Mensaje de error al reinsertar los registros en el fichero
        }
    }
}
