
import java.io.File; 
import java.io.FileWriter; 
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException; 
import java.io.PrintWriter; 
import java.util.Scanner; 

public class ACADTarea6 {

    public static void main(String[] args) {
        String ruta = "/media"; // Ruta predeterminada para el archivo

        // Si se proporciona una ruta en los argumentos, se usa esa
        if (args.length > 0) {
            ruta = args[0];
        }

        // Muestra la ruta que se va a utilizar
        System.out.println("Ruta: " + ruta);

        // Crea el archivo original en la ruta especificada y lee su contenido
        File ficheroOriginal = new File(ruta + File.separator + "Fichero.txt");
        String contenido = leerContenido(ficheroOriginal); // Leer el contenido del archivo
        String contenidoModificado = modificarContenido(contenido); // Modificar el contenido

        // Crear un archivo temporal, renombrarlo y escribir el contenido modificado en él
        File ficheroTemporal = crearFicheroTemp(ruta);
        File ficheroTemporalRenombrado = renombrarFichero(ficheroTemporal);
        ficheroTemporalRenombrado = escribirContenido(ficheroTemporalRenombrado, contenidoModificado);
    }

    // Método para crear un archivo temporal
    public static File crearFicheroTemp(String ruta) {
        File tempFile = null; // Inicializa el archivo temporal
        try {
            // Crea el archivo temporal en la ruta indicada
            tempFile = File.createTempFile("FicheroTemporal", ".txt", new File(ruta));
            System.out.println("Fichero temporal creado: " + tempFile.getAbsolutePath());
        } catch (IOException e) {
            // Manejar errores en la creación del archivo temporal
            System.out.println("Error creando el fichero temporal: " + e.toString());
        }

        return tempFile; // Devuelve el archivo temporal creado
    }

    // Método para renombrar el archivo temporal
    public static File renombrarFichero(File fichero) {
        File ficheroRenombrado = null; // Inicializa el archivo renombrado

        // Muestra el nombre del archivo temporal actual
        System.out.println("Nombre actual del fichero temporal: " + fichero.getName());

        Scanner sn_teclado = new Scanner(System.in); // Solicita entrada del usuario

        // Pide al usuario un nuevo nombre para el archivo
        System.out.println("Introduce un nuevo nombre para el fichero: ");
        String nuevoNombre = sn_teclado.nextLine();

        // Crea un nuevo archivo con el nombre indicado
        ficheroRenombrado = new File(fichero.getParent() + File.separator + nuevoNombre + ".txt");

        // Intenta renombrar el archivo y muestra el resultado
        if (fichero.renameTo(ficheroRenombrado)) {
            System.out.println("Fichero renombrado con exito: " + ficheroRenombrado.getName());
        } else {
            System.out.println("No se ha podido renombrar el fichero");
        }

        return ficheroRenombrado; // Devuelve el archivo renombrado
    }

    // Método para leer el contenido de un archivo
    public static String leerContenido(File fichero) {
        FileReader fr = null;
        BufferedReader br = null;
        String contenido = ""; // Variable para almacenar el contenido leído

        try {
            // Abre el archivo para lectura
            fr = new FileReader(fichero);
            br = new BufferedReader(fr);

            String linea; // Variable para leer línea a línea el contenido

            // Lee el contenido línea a línea
            while ((linea = br.readLine()) != null) {
                contenido += linea;
            }
        } catch (IOException e) {
            // Maneja errores en la lectura del archivo
            System.out.println("Error leyendo el fichero: " + e.toString());
        } finally {
            // Cierra los lectores del archivo
            try {
                br.close();
                fr.close();
            } catch (IOException e2) {
                System.out.println("Error cerrando el fichero: " + e2.toString());
            }
        }

        return contenido; // Devuelve el contenido leído
    }

    // Método para modificar el contenido de un archivo
    public static String modificarContenido(String contenido) {
        String contenidoModificado = ""; // Almacena el nuevo contenido
        boolean hayPunto = false; // Controla si el último carácter leído es un punto
        boolean hayEspacio = false; // Controla si se ha leído un espacio

        // Itera sobre cada carácter del contenido original
        for (int i = 0; i < contenido.length(); i++) {
            char actual = contenido.charAt(i);

            // Si el carácter es un punto, lo añade y activa el flag de punto
            if (actual == '.') {
                contenidoModificado += actual;
                hayPunto = true;
                continue;
            }

            // Si es un espacio, solo añade uno si no se ha añadido antes
            if (actual == ' ') {
                if (!hayEspacio) {
                    contenidoModificado += actual;
                    hayEspacio = true;
                }
                continue;
            }

            // Si se encontró un punto antes, convierte el siguiente carácter a mayúscula
            if (hayPunto) {
                contenidoModificado += (Character.toUpperCase(actual));
                hayPunto = false;
            } else {
                contenidoModificado += actual; // Añade el carácter tal cual si no había punto antes
            }

            hayEspacio = false; // Reinicia el control de espacios
        }

        return contenidoModificado; // Devuelve el contenido modificado
    }

    // Método para escribir contenido en un archivo
    public static File escribirContenido(File fichero, String contenido) {
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            // Abre el archivo para escribir el nuevo contenido
            fw = new FileWriter(fichero);
            pw = new PrintWriter(fw);

            // Escribe el contenido modificado en el archivo
            pw.print(contenido);
            System.out.println("Contenido modificado anadido al fichero temporal");
        } catch (IOException e) {
            // Maneja errores al escribir en el archivo
            System.out.println("Error escribiendo en el fichero: " + e.toString());
        } finally {
            // Cierra los escritores del archivo
            try {
                if (pw != null) {
                    pw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e2) {
                System.out.println("Error cerrando el fichero: " + e2.toString());
            }
        }

        return fichero; // Devuelve el archivo modificado
    }
}
