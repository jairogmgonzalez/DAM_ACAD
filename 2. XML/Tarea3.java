
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author jairo
 */
public class Tarea3 {

    public static void main(String[] args) {
        String ruta = "catalogo.xml"; // Se define la ruta del archivo XML

        // Se define un array de solicitudes de consultas para el documento XML
        String[] solicitudes = {
            "1. Muestra los titulos de los libros con ID desde bk105 hasta bk108",
            "2. Muestra los titulos de los libros cuyo precio es menor a 10 euros",
            "3. Muestra los autores de todos los libros que pertenecen al genero \"Fantasy\" y cuyo precio es mayor o igual a 5 euros",
            "4. Muestra los titulos de los libros publicados despues del 2000",
            "5. Muestra los titulos de los libros cuyos autores tienen el apellido 'Corets'"
        };

        // Se define un array de expresiones XPath correspondientes a cada solicitud
        String[] expresiones = {
            "//Libro[number(substring(@id, 3)) >= 105 and number(substring(@id, 3)) <= 108]/Titulo/text()",
            "//Libro[Precio < 10]/Titulo/text()",
            "//Libro[Genero = 'Fantasy' and Precio >= 5]/Autor/text()",
            "//Libro[number(substring(Fecha_publicacion, 1, 4)) > 2000]/Titulo/text()",
            "//Libro[contains(Autor,'Corets')]/Titulo/text()"
        };

        // Se crea un mapa para asociar cada solicitud con su expresión XPath
        Map<String, String> consultas = new LinkedHashMap<>();

        // Se recorre el array de solicitudes para llenar el mapa de consultas
        for (int i = 0; i < solicitudes.length; i++) {
            String solicitud = solicitudes[i]; // Se obtiene la solicitud
            String expresion = expresiones[i]; // Se obtiene la expresión XPath correspondiente

            // Se añade la solicitud y la expresión al mapa
            consultas.put(solicitud, expresion);
        }

        try {
            // Se crea una nueva instancia de DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringComments(true); // Se configura para ignorar los comentarios en el XML
            dbf.setIgnoringElementContentWhitespace(true); // Se configura para ignorar los espacios en blanco

            // Se obtiene un objeto DocumentBuilder de la fábrica
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Se carga y analiza el archivo XML especificado en la ruta
            Document doc = db.parse(new File(ruta));

            // Bucle que recorre cada solicitud del mapa de consultas
            for (String solicitud : consultas.keySet()) {
                String expresionXPATH = consultas.get(solicitud); // Se obtiene la expresión XPath
                mostrarSolicitud(doc, solicitud, expresionXPATH); // Se llama al método mostrarSolicitud
                System.out.println();
            }

        } catch (FileNotFoundException | ParserConfigurationException | SAXException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Método que muestra una solicitud XPath de un documento
    public static void mostrarSolicitud(Document doc, String solicitud, String consulta) {
        XPathFactory xpf = XPathFactory.newInstance(); // Se crea una instancia de XPathFactory
        XPath xpath = xpf.newXPath(); // Se crea un objeto XPath

        try {
            // Se compila la expresión XPath
            XPathExpression expr = xpath.compile(consulta);
            Object result = expr.evaluate(doc, XPathConstants.NODESET); // Se evalúa la expresión y se obtiene el resultado como un conjunto de nodos
            NodeList nodos = (NodeList) result; // Se convierte el resultado a NodeList

            System.out.println(solicitud); // Se imprime la solicitud

            // Se verifica si la lista de nodos no está vacía
            if (nodos.getLength() > 0) {

                // Bucle que recorre la lista de nodos
                for (int i = 0; i < nodos.getLength(); i++) {
                    System.out.println(nodos.item(i).getTextContent()); // Se imprime el contenido de cada nodo
                }
            } else { // Si la lista de nodos está vacía
                System.out.println("No se encontraron resultados para esta consulta.");
            }
        } catch (XPathExpressionException e) {
            System.err.println("Error al evaluar la expresión XPath: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
        }
    }
}
