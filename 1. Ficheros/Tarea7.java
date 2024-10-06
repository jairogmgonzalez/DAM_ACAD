
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 * Clase principal para convertir archivos de texto de una codificación a otra.
 *
 * @author jairo
 */
public class ACADTarea7 {

    /**
     * Método principal que ejecuta el programa.
     *
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        Scanner sn_teclado = new Scanner(System.in); // Inicializa el escáner para pedir entradas al usuario

        // Solicita al usuario la ruta y codificación del archivo de entrada
        System.out.println("Introduce la ruta del archivo de entrada:");
        String rutaArchivoEntrada = sn_teclado.nextLine() + ".txt"; // Lee la ruta del archivo de entrada

        System.out.println("Introduce el encoding del archivo de entrada:");
        String encodingEntrada = sn_teclado.nextLine(); // Lee la codificación del archivo de entrada

        File ficheroOriginal = new File(rutaArchivoEntrada);

        // Solicita al usuario la ruta y codificación del archivo de salida
        System.out.println("Introduce la ruta donde deseas crear el archivo de salida:");
        String rutaArchivoSalida = sn_teclado.nextLine(); // Lee la ruta del archivo de salida

        System.out.println("Introduce el encoding del archivo de salida:");
        String encodingSalida = sn_teclado.nextLine(); // Lee la codificación del archivo de salida
        
        // Se crea el que va a ser el fichero convertido a la codificación que se pida
        File ficheroFinal = crearFichero(rutaArchivoSalida);
        String rutaFicheroFinal = ficheroFinal.getAbsolutePath();

        // Convierte el archivo de entrada usando las codificaciones proporcionadas
        ficheroFinal = convertirArchivo(ficheroOriginal, encodingEntrada, ficheroFinal, encodingSalida);

        // Comprueba si la conversión se realizó correctamente
        if (ficheroFinal != null && ficheroFinal.exists()) {
            System.out.println("La conversion se completo exitosamente. Archivo convertido: " + ficheroFinal.getAbsolutePath());
        } else {
            System.out.println("La conversión falló.");
        }
    }

    // Método para crear un fichero
    public static File crearFichero(String ruta) {
        Scanner sn_teclado = new Scanner(System.in); // Objeto Scanner para entrada de datos del usuario

        System.out.println("Introduce el nombre del fichero que deseas crear");
        String nombre = sn_teclado.nextLine(); // Leer el nombre del fichero

        // Crear el archivo con la ruta y nombre especificados
        File fichero = new File(ruta + File.separator + nombre + ".txt");

        // Crear el fichero o avisar si ya existe
        try {
            if (fichero.createNewFile()) {
                System.out.println("Fichero creado: " + fichero.getName());
            } else {
                System.out.println("El fichero ya existe. Se reescribira su contenido");
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear o escribir en el fichero: " + e.getMessage());
        }

        return fichero;
    }

    // Método que convierte un archivo de una codificación a otra
    public static File convertirArchivo(File archivoEntrada, String encodingEntrada, File archivoFinal, String encodingSalida) {
        Scanner sn_teclado = new Scanner(System.in);
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            // Inicializa el flujo de lectura desde el archivo de entrada con la codificación especificada
            FileInputStream fis = new FileInputStream(archivoEntrada);
            InputStreamReader isr = new InputStreamReader(fis,encodingEntrada);
            br = new BufferedReader(isr);

            // Inicializa el flujo de escritura hacia el archivo de salida con la codificación especificada
            FileOutputStream fos = new FileOutputStream(archivoFinal);
            OutputStreamWriter osw = new OutputStreamWriter(fos, encodingSalida);
            bw = new BufferedWriter(osw);

            String linea;
            // Lee línea por línea del archivo de entrada y escribe en el archivo de salida
            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine(); // Asegura que las líneas se separen correctamente
            }

            return archivoFinal; // Devuelve el archivo convertido al finalizar

        } catch (FileNotFoundException e) {
            // Muestra un mensaje si el archivo no se encuentra
            System.err.println("Archivo no encontrado: " + e.toString());
        } catch (IOException e) {
            // Muestra un mensaje si hay algún error de lectura o escritura
            System.err.println("Error de lectura/escritura: " + e.toString());
        } finally {
            // Cierra los recursos utilizados para evitar fugas
            try {
                if (br != null) {
                    br.close();
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                System.err.println("Error al cerrar los streams: " + e.getMessage());
            }
        }
        return null; // Retorna null si la conversión falla
    }
}
