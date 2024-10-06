/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jairo
 */
public class ACADTarea4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Ruta por defecto donde se trabajará
        String ruta = "/media";

        // Si se proporciona una ruta como argumento, se utiliza esa ruta
        if (args.length > 0) {
            ruta = args[0];
        }

        // Se crea un objeto de tipo File con la ruta especificada
        File archivo = new File(ruta);
        // Variable para almacenar el texto a buscar
        String texto;
        // Lista para almacenar los ficheros dentro de un directorio
        ArrayList<File> listaFicheros = new ArrayList<>();

        // Se muestra la ruta especificada
        System.out.println("Ruta: " + ruta);
                
        // Comprobamos si el archivo existe
        if (!archivo.exists()) {
            // Mensaje de error si no se encuentra la ruta
            System.out.println("No se ha encontrado la siguiente ruta: " + archivo.getAbsolutePath());
        } else {
            // Si el archivo existe y es un fichero
            if (archivo.isFile()) {
                // Mensaje indicando que se trata de un fichero
                System.out.println("La ruta introducida dirige a un fichero.");
                System.out.println();
                // Pedir el texto a buscar
                texto = pedirTexto();
                System.out.println();
                // Buscar el texto en el fichero
                buscarTexto(archivo, texto);
            } // Si el archivo es un directorio
            else {
                // Mensaje indicando que se trata de un directorio y mostrar su contenido
                System.out.println("La ruta introducida dirige a un directorio. Contenidos:");
                System.out.println();
                mostrarContenido(archivo, ""); // Muestra el contenido del directorio
                System.out.println();
                // Obtener todos los ficheros dentro del directorio
                listaFicheros = obtenerFicheros(archivo);
                // Pedir el texto a buscar
                texto = pedirTexto();
                System.out.println();

                // Buscar el texto en cada fichero de la lista
                for (File fichero : listaFicheros) {
                    buscarTexto(fichero, texto); // Llama al método para buscar texto en cada fichero.
                }
            }
        }
    }

    // Método para mostrar el contenido de un directorio.
    // Muestra los nombres de los archivos y subdirectorios dentro del directorio especificado.
    public static void mostrarContenido(File archivo, String separador) {
        // Lista todos los directorios y ficheros dentro del directorio actual
        File[] contenido = archivo.listFiles();

        // Verifica si el directorio tiene contenido
        if (contenido != null) {
            // Itera sobre cada directorio o archivo en el contenido
            for (File f : contenido) {
                // Si es un subdirectorio, muestra su nombre y se llama recursivamente
                if (f.isDirectory()) {
                    System.out.println(separador + "|-- " + f.getName());
                    mostrarContenido(f, separador + "    "); // Llamada recursiva para subdirectorios
                } else if (f.isFile()) {
                    // Si es un fichero, se muestra su nombre
                    System.out.println(separador + "|-- " + f.getName());
                }
            }
        }
    }

    // Método que devuelve una lista con todos los ficheros de un directorio.
    private static ArrayList<File> obtenerFicheros(File archivo) {
        // Se crea una lista para almacenar los ficheros encontrados
        ArrayList<File> ficheros = new ArrayList<>();

        // Lista todos los directorios y ficheros dentro del directorio actual
        File[] contenido = archivo.listFiles();

        // Recorre los elementos en el directorio
        if (contenido != null) {
            for (File f : contenido) {
                // Si se encuentra un directorio, se llama recursivamente
                if (f.isDirectory()) {
                    ficheros.addAll(obtenerFicheros(f)); // Añade los ficheros encontrados en el subdirectorio
                } else if (f.isFile()) {
                    // Si es un fichero, se agrega a la lista
                    ficheros.add(f);
                }
            }
        }

        return ficheros; // Devuelve la lista de ficheros
    }

    // Método para pedir el texto en minúscula que se quiere buscar.
    public static String pedirTexto() {
        // Se crea un objeto Scanner para leer la entrada del usuario
        Scanner sn_teclado = new Scanner(System.in);

        System.out.println("Introduce una palabra o texto que deseas buscar:");
        String texto = sn_teclado.nextLine(); // Lee la línea de entrada

        return texto.toLowerCase(); // Devuelve el texto en minúsculas
    }

    // Método para buscar el texto en un fichero.
    // Muestra las líneas y columnas donde se encuentra el texto buscado.
    public static void buscarTexto(File fichero, String texto) {
        String linea;
        int numLinea = 0;

        FileReader fr = null;
        BufferedReader br = null;

        // Mensaje de análisis del fichero
        System.out.println("Analizando " + fichero.getName() + "...");

        try {
            // Inicializa el FileReader y BufferedReader
            fr = new FileReader(fichero);
            br = new BufferedReader(fr);

            // Lee el fichero línea por línea
            while ((linea = br.readLine()) != null) {
                linea = linea.toLowerCase(); // Convierte la línea a minúsculas
                numLinea++; // Incrementa el número de línea
                int numColumna = linea.indexOf(texto.toLowerCase()); // Busca el texto en la línea

                // Mientras se encuentre el texto en la línea
                while (numColumna >= 0) {
                    // Muestra la línea y columna donde se encuentra el texto
                    System.out.println("''" + texto + "''" + " se ha encontrado en el fichero " + fichero.getName()
                            + ", en la linea " + numLinea + " y columna " + (numColumna + 1));

                    // Busca el texto en la línea a partir de la última posición encontrada
                    numColumna = linea.indexOf(texto.toLowerCase(), numColumna + 1);
                }
            }
        } catch (IOException e) {
            // Mensaje de error en caso de fallo en la lectura del fichero
            System.out.println("Error leyendo el fichero: " + e.toString());
        } finally {
            try {
                // Cierra los streams en caso de haber sido abiertos
                if (br != null) {
                    br.close(); // Cierra el BufferedReader
                }
                if (fr != null) {
                    fr.close(); // Cierra el FileReader
                }
            } catch (IOException e2) {
                // Mensaje de error al cerrar el fichero
                System.out.println("Error cerrando el fichero: " + e2.toString());
            }
        }
    }
}
