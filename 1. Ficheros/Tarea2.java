
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author jairo
 */
public class Tarea2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        // Llama al método para solicitar al usuario la ruta donde se desea crear la estructura de directorios
        String ruta = preguntarRuta();

        // Crea la estructura de directorios en la ruta especificada
        crearEstructura(ruta);

        // Crea un objeto File que representa el directorio en la ruta proporcionada
        File directorio = new File(ruta);

        // Pregunta al usuario qué fichero desea eliminar
        String ficheroEliminar = preguntarFichero();

        // Busca el fichero en el directorio y subdirectorios
        File ficheroEncontrado = recorrerFichero(directorio, ficheroEliminar);

        // Elimina el fichero encontrado, si existe
        eliminarFichero(ficheroEncontrado);
    }

    // Método para preguntar la ruta donde se quiere crear el directorio
    public static String preguntarRuta() {
        Scanner sn_teclado = new Scanner(System.in);
        String ruta;

        String patron = "^[a-zA-Z]:\\\\.*|^[a-zA-Z]:/.*|^/.*$"; // Patrón para validar la ruta

        System.out.println("Introduce la ruta donde deseas crear la estructura de directorios:");
        ruta = sn_teclado.nextLine();

        // Bucle que pide una ruta mientras no sea válida
        while (!ruta.matches(patron)) {

            System.out.println("Ruta invalida. Por favor, introduzca una ruta válida.");
            ruta = sn_teclado.nextLine();

        }

        System.out.println("Ruta valida : " + ruta);
        System.out.println();
        return ruta;
    }

    // Método para crear la estructura de directorios.
    /* Crea una jerarquía de directorios con ficheros dentro siguiendo
       siguiendo una estructura predefinida.
    */
    public static void crearEstructura(String ruta) {
        // Lista que define los nodos principales (Abuelo y Abuela).
        String[] nodos = {"Abuelo", "Abuela"};

        // Lista que define los subnodos (Padre y Madre).
        String[] subNodos = {"Padre", "Madre"};

        // Lista que define los nombres de los hijos (Hijo y Hija).
        String[] hijos = {"Hijo", "Hija"};
        int contador_hijos = 1;

        // Itera sobre cada nodo principal
        for (String a : nodos) {
            // Itera sobre cada subnodo
            for (String b : subNodos) {

                // Crea la estructura de nodo principal y subnodo
                File directorio = new File(ruta + File.separator + a + File.separator + b);
                directorio.mkdirs();

                // Itera sobre cada hijo
                for (String f : hijos) {

                    // Intenta crear los ficheros de los hijos dentro de cada subnodo
                    File fichero = new File(ruta + File.separator + a + File.separator + b + File.separator + f + contador_hijos);
                    try {
                        fichero.createNewFile();
                    } catch (IOException e) {
                        System.out.println("Ocurrio un error al crear el archivo: " + e.getMessage());
                    }
                    contador_hijos++;
                    System.out.println("Fichero creado: " + fichero.getAbsolutePath());
                }
            }

        }
        System.out.println();
    }

    // Método para preguntar que fichero se quiere eliminar.
    public static String preguntarFichero() {
        Scanner sn_teclado = new Scanner(System.in);
        String patron = "^(Hijo|Hija)\\d+$"; // Patrón para validar el nombre del fichero
        String fichero;

        // Bucle que pregunta el fichero a eliminar mientras no sea un nombre correcto
        while (true) {
            System.out.println("¿Que fichero deseas eliminar?");
            fichero = sn_teclado.nextLine();

            // Verifica si el fichero coincide con el patrón
            if (fichero.matches(patron)) {
                return fichero; // Devuelve el fichero si es válido
            } else {
                System.out.println("Fichero invalido. Por favor, introduce un nombre valido.");
            }
        }
    }

    // Método para buscar recursivamente un fichero dentro de un directorio.
    // Si el fichero se encuentra, devuelve un objeto tipo File que representa al fichero.
    // Si el fichero no se encuentra, devuelve null.
    public static File recorrerFichero(File directorio, String fichero) {
        // Lista todos los directorios y ficheros dentro del directorio actual
        File[] contenido = directorio.listFiles();

        // Verifica si el directorio tiene contenido
        if (contenido != null) {
            // Itera sobre cada directorio o archivo en el contenido
            for (File f : contenido) {
                // Si es un subdirectorio, llama recursivamente a recorrerFichero para buscar en él
                if (f.isDirectory()) {
                    File encontrado = recorrerFichero(f, fichero);
                    // Si el fichero se encuentra en algún subdirectorio, lo devuelve
                    if (encontrado != null) {
                        return encontrado;
                    }
                    // Si es un fichero y el nombre coincide con el fichero que se busca, lo devuelve
                } else if (f.isFile() && f.getName().equals(fichero)) {
                    return f;
                }
            }
        }
        // Si no se encuentra el fichero, devuelve null
        return null;
    }

    // Método para eliminar un fichero.
    // Si el fichero existe, procede a eliminarlo.
    public static void eliminarFichero(File fichero) {
        // Verifica si el fichero existe
        if (fichero != null) {
            // Intenta eliminar el fichero acutal
            if (fichero.delete()) {
                System.out.println("Fichero eliminado: " + fichero.getAbsolutePath());
            } else {
                System.out.println("No se pudo eliminar el fichero: " + fichero.getAbsolutePath());
            }
        } else {
            System.out.println("Fichero no encontrado.");
        }
    }
}

