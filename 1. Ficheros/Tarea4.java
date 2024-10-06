/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException; 
import java.io.PrintWriter; 
import java.util.Scanner; 
/**
 *
 * @author jairo
 */
public class ACADTarea5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Crear un objeto Scanner para la entrada del usuario
        Scanner sn_teclado = new Scanner(System.in);

        // Bucle principal que muestra el menú hasta que el usuario decida salir
        while (true) {
            mostrarMenu(); // Mostrar opciones del menú
            System.out.println("Elige una opcion"); // Pedir al usuario que elija una opción
            int opcion = sn_teclado.nextInt(); // Leer la opción seleccionada
            System.out.println();

            // Ejecutar la opción elegida
            switch (opcion) {
                case 1:
                    primeraFuncion(); // Llamar al método que crea un archivo y escribe contenido
                    break;
                case 2:
                    segundaFuncion(); // Llamar al método que añade contenido al final de un archivo existente
                    break;
                case 3:
                    terceraFuncion(); // Llamar al método que añade contenido al principio de un archivo
                    break;
                case 0:
                    return; // Terminar el programa
            }
        }
    }

    // Método para mostrar el menú con las diferentes opciones disponibles
    public static void mostrarMenu() {
        System.out.println("----- MENU FICHEROS -----");
        System.out.println("1. Crear un fichero y escribir contenido en el.");
        System.out.println("2. Elegir un fichero y anadir contenido al final.");
        System.out.println("3. Elegir un fichero y anadir contenido al principio.");
        System.out.println("0. Salir");
        System.out.println();
    }

    // Método para crear un fichero y escribir contenido en él
    public static void primeraFuncion() {
        Scanner sn_teclado = new Scanner(System.in); // Objeto Scanner para entrada de datos del usuario

        System.out.println("Introduce la ruta donde deseas crear el fichero");
        String ruta = sn_teclado.nextLine(); // Leer la ruta ingresada por el usuario

        // Validar que la ruta introducida sea correcta
        String patron = "^[a-zA-Z]:\\\\.*|^[a-zA-Z]:/.*|^/.*$";
        while (!ruta.matches(patron)) {
            System.out.println("Por favor, introduce una ruta valida");
            ruta = sn_teclado.nextLine(); // Leer nuevamente la ruta si es incorrecta
        }
        
        System.out.println();
        System.out.println("Ruta valida");
        System.out.println();

        System.out.println("Introduce el nombre del fichero que deseas");
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

        // Variables para escribir en el archivo
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(fichero); // Abrir el archivo para escritura
            pw = new PrintWriter(fw);

            System.out.println("Introduce el contenido que deseas escribir en el fichero");
            String contenido = sn_teclado.nextLine(); // Leer el contenido a escribir

            pw.print(contenido); // Escribir el contenido en el archivo
            System.out.println("Contenido escrito.");
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error leyendo el fichero" + e.toString());
        } finally {
            // Cerrar el FileWriter y PrintWriter
            try {
                if (pw != null) {
                    pw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e2) {
                System.out.println("Error cerrando el fichero" + e2.toString());
            }
        }
    }

    // Método para añadir contenido al final de un fichero existente
    public static void segundaFuncion() {
        Scanner sn_teclado = new Scanner(System.in);

        System.out.println("Introduce la ruta del fichero que deseas anadir contenido al final");
        String ruta = sn_teclado.nextLine(); // Leer la ruta del archivo

        // Validar la ruta
        String patron = "^[a-zA-Z]:\\\\.*|^[a-zA-Z]:/.*|^/.*$";
        while (!ruta.matches(patron)) {
            System.out.println("Por favor, introduce una ruta valida");
            ruta = sn_teclado.nextLine();
        }

        System.out.println("Ruta valida");

        // Crear objeto File con la ruta
        File fichero = new File(ruta + ".txt");

        System.out.println("Introduce el contenido que deseas escribir en el fichero");
        String contenido = sn_teclado.nextLine(); // Leer el contenido a añadir

        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(fichero, true); // Abrir archivo en modo añadir
            pw = new PrintWriter(fw);

            pw.print(" " + contenido); // Añadir el contenido al final del archivo
            System.out.println("Contenido anadido");
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error leyendo el fichero" + e.toString());
        } finally {
            // Cerrar los streams de escritura
            try {
                if (pw != null) {
                    pw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e2) {
                System.out.println("Error cerrando el fichero" + e2.toString());
            }
        }
    }

    // Método para añadir contenido al principio de un fichero existente
    public static void terceraFuncion() {
        Scanner sn_teclado = new Scanner(System.in);

        System.out.println("Introduce la ruta del fichero que deseas anadir contenido al principio");
        String ruta = sn_teclado.nextLine(); // Leer la ruta del archivo

        // Validar la ruta
        String patron = "^[a-zA-Z]:\\\\.*|^[a-zA-Z]:/.*|^/.*$";
        while (!ruta.matches(patron)) {
            System.out.println("Por favor, introduce una ruta valida");
            ruta = sn_teclado.nextLine();
        }

        System.out.println("Ruta valida");

        // Crear objeto File con la ruta
        File fichero = new File(ruta + ".txt");

        System.out.println("Introduce el contenido que deseas escribir en el fichero");
        String contenido = sn_teclado.nextLine(); // Leer el nuevo contenido

        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        PrintWriter pw = null;

        String linea;
        String contenido_existente = ""; // Variable para almacenar el contenido actual del archivo

        // Leer el contenido existente del archivo
        try {
            fr = new FileReader(fichero);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                contenido_existente += linea;
            }
        } catch (IOException e) {
            System.out.println("Error del fichero leyendo " + e.toString());
        } finally {
            // Cerrar los streams de lectura
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e2) {
                System.out.println("Error cerrando el fichero " + e2.toString());
            }
        }
        
        System.out.println();
        System.out.println("Este es el contenido del fichero actualmente:");
        System.out.println(contenido_existente); // Mostrar el contenido actual del archivo
        System.out.println();

        // Escribir el nuevo contenido al principio y luego el contenido existente
        try {
            fw = new FileWriter(fichero);
            pw = new PrintWriter(fw);

            pw.print(contenido); // Añadir el nuevo contenido al principio
            pw.print(" " + contenido_existente); // Añadir el contenido antiguo al final
            System.out.println("Contenido anadido");
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error leyendo el fichero" + e.toString());
        } finally {
            // Cerrar los streams de escritura
            try {
                if (pw != null) {
                    pw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e2) {
                System.out.println("Error cerrando el fichero" + e2.toString());
            }
        }
    }
}