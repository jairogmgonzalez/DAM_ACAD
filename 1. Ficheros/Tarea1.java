
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author jairo
 */
public class Tarea1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // Ruta donde se trabajará.
        String ruta = "/media";
        if (args.length > 0) {
            ruta = args[0];
        }

        // Se inicializa el archivo con el que se va a trabajar
        File archivo = new File(ruta);
        System.out.println("Ruta utilizada: " + archivo.getAbsolutePath());

        // Se comprueba si el archivo existe y si es un fichero o directorio
        if (!archivo.exists()) {
            System.out.println("No existe el fichero o directorio: " + archivo.getAbsolutePath());
        } else {
            if (archivo.isFile()) {
                System.out.println("Se trada de un fichero.");
                System.out.println();
                contadorVocales(archivo);      
                System.out.println();
            } else {
                System.out.println("Se trata de un directorio. Contenidos: ");
                System.out.println();
                mostrarContenido(archivo, "");
            }
            System.out.println();
            mostrarDetallesArchivo(archivo);
        }
    }

    // Método para listar el contenido del directorio.
    // Muestra los nombres de los archivos y subdirectorios dentro del directorio especificado.
    public static void mostrarContenido(File archivo, String separador) {
        // Lista todos los directorios y ficheros dentro del directorio actual
        File[] contenido = archivo.listFiles();

        // Verifica si el directorio tiene contenido
        if (contenido != null) {
            // Itera sobre cada directorio o archivo en el contenido
            for (File f : contenido) {
                // Si es un subdirectorio, muestra su nombre y llama recursivamente para mostrar su contenido
                if (f.isDirectory()) {
                    System.out.println(separador + "/ " + f.getName());
                    mostrarContenido(f, separador + " ");

                } // Si es un archivo, imprime su nombre y cuenta las vocales
                else if (f.isFile()) {
                    System.out.print(separador + "/ " + f.getName());
                    contadorVocales(f);
                    System.out.println();
                }
            }
        }

    }

    // Método para mostrar varios detalles importantes del archivo.
    /*Este método imprime en consola información relevante sobre el archivo,
      incluyendo su nombre, ruta, tamaño, fecha de última modificación y permisos.
     */
    public static void mostrarDetallesArchivo(File archivo) {
        // Muestra el nombre del archivo
        System.out.println("*** DETALLES DEL ARCHIVO " + archivo.getName() + " ***");
        // Muestra la ruta absoluta del archivo
        System.out.println("Ruta: " + archivo.getAbsolutePath());
        // Muestra el tamaño del archivo en bytes
        System.out.println("Tamano: " + archivo.length() + " bytes.");

        // Mmestra la fecha de última modificación del archivo
        long tiempoModificacion = archivo.lastModified();
        Date fechaModificacion = new Date(tiempoModificacion);
        System.out.println("Ultima modificacion: " + fechaModificacion);

        // Muestra si el archivo es oculto
        System.out.println("Oculto: " + ((archivo.isHidden()) ? "Si" : "No"));
        // Muestra si el archivo es ejecutable
        System.out.println("Ejecutable: " + ((archivo.canExecute()) ? "Si" : "No"));
        // Muestra si el archivo es legible
        System.out.println("Legible: " + ((archivo.canRead()) ? "Si" : "No"));
        // Muestra si el archivo es editable
        System.out.println("Editable: " + ((archivo.canWrite()) ? "Si" : "No"));

        System.out.println();
    }

    // Método para contar las vocales que hay dentro de un fichero.
    /* Este método lee el contenido de un fichero y cuenta cuántas veces aparecen
       cada una de las vocales (a, e, i, o, u) en el texto.
     */
    public static void contadorVocales(File fichero) {
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Crea un FileReader para el fichero y un BufferedReader para leerlo línea por línea
            fr = new FileReader(fichero);
            br = new BufferedReader(fr);

            String linea;
            // Contadores para cada vocal
            int vocal_a = 0, vocal_e = 0, vocal_i = 0, vocal_o = 0, vocal_u = 0;

            // Bucle que lee el fichero línea por línea mientras haya
            while ((linea = br.readLine()) != null) {
                // Convierte la línea a minúsculas y las pasa a una lista de caracteres
                char[] vocales = linea.toLowerCase().toCharArray();
                // Itera sobre cada carácter de la lista
                for (int i = 0; i < vocales.length; i++) {
                    // Incrementa el contador de cada vocal según el carácter
                    switch (vocales[i]) {
                        case 'a':
                            vocal_a++;
                            break;
                        case 'e':
                            vocal_e++;
                            break;
                        case 'i':
                            vocal_i++;
                            break;
                        case 'o':
                            vocal_o++;
                            break;
                        case 'u':
                            vocal_u++;
                            break;
                    }
                }
            }

            // Imprime el resultado del conteo
            System.out.print("   [VOCALES ::");
            System.out.print(" a - " + vocal_a);
            System.out.print(" || e - " + vocal_e);
            System.out.print(" || i - " + vocal_i);
            System.out.print(" || o - " + vocal_o);
            System.out.print(" || u - " + vocal_u + "]");
            
        } catch (IOException e) {

            System.out.println("Error del fichero leyendo " + e.toString());
        } finally {
            try {
                // Intenta Cierra el BufferedReader y FileReader
                br.close();
                fr.close();
            } catch (IOException e2) {
                System.out.println("Error cerrando el fichero " + e2.toString());
            }
        }
    }
}
