
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author jairo
 */
public class Tarea2 {

    private static final String SEPARADOR = " ";

    public static void main(String[] args) {
        String ruta = "catalog.xml"; // Se define la ruta del archivo XML

        // Se crea una nueva instancia de DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true); // Se configura para ignorar los comentarios
        dbf.setIgnoringElementContentWhitespace(true); // Se configura para ignorar los espacios en blanco

        try {
            DocumentBuilder db = dbf.newDocumentBuilder(); // Se obtiene un objeto DocumentBuilder de la fábrica
            Document doc = db.parse(new File(ruta)); // Se carga y analiza el archivo XML especificado en la ruta

            // Se muestra la lista de títulos
            System.out.println("Lista de titulos:");
            mostrarTextoNodo(doc, "title"); // Se llama al método mostrarTextoNodo para mostrar los títulos de todos los libros

            // Se crea un instancia de DOMImplementarion
            DOMImplementation domImpl = db.getDOMImplementation();

            // Se crea un nuevo documento con el elemento raíz "Catalogo"
            Document librosXML = domImpl.createDocument(null, "Catalogo", null);

            // Se llama al método traducirYCopiar para obtener el XML original traducido
            traducirYCopiar(doc, librosXML, librosXML.getDocumentElement());

            // Se crea una nueva instancia de TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            // Se obtiene un objeto Transformer de la fábrica
            Transformer transformer = transformerFactory.newTransformer();

            // Se crea una nueva DOMSource con el documento traducido
            DOMSource source = new DOMSource(librosXML);

            // Se crea un nuevo StreamResult para el archivo de salida
            StreamResult result = new StreamResult(new File("catalogo.xml"));

            // Se realiza la transformación del documento a un archivo
            transformer.transform(source, result);

            System.out.println("\nEl archivo catalogo.xml ha sido creado con exito.");

        } catch (FileNotFoundException | ParserConfigurationException | SAXException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Método que muestra por pantalla el texto de un nodo dado
    public static void mostrarTextoNodo(Document doc, String nombreNodo) {

        // Se obtiene todos los nodos con el nombre del nodo dado por parámetro
        NodeList nodos = doc.getElementsByTagName(nombreNodo);

        // Se verifica si el nodo existe
        if (nodos.getLength() > 0) {

            // Bucle que recorre cada nodo de la lista
            for (int i = 0; i < nodos.getLength(); i++) {
                Node nodo = nodos.item(i); // Se obtiene el nodo de la iteración
                System.out.println(i + ". " + nodo.getTextContent()); // Se imprime por pantalla el texto del nodo
            }
        }

    }

    // Método que traduce los nodos del XML original y copia la estructura en el nuevo XML
    private static void traducirYCopiar(Node nodoOriginal, Document docNuevo, Node nodoPadreNuevo) {

        // Se verifica si el nodo original pasado por parámetro es un documento
        if (nodoOriginal.getNodeType() == Node.DOCUMENT_NODE) {
            nodoOriginal = ((Document) nodoOriginal).getDocumentElement(); // Se obtiene el nodo raíz del documento
        }

        NodeList nodosHijo = nodoOriginal.getChildNodes(); // Se obtiene los nodos hijo del nodo original

        // Se verifica si la lista de nodos no está vacía
        if (nodosHijo.getLength() > 0) {

            // Bucle que recorre la lista de nodos hijo
            for (int i = 0; i < nodosHijo.getLength(); i++) {
                Node nodoHijo = nodosHijo.item(i); // Se obtiene el nodo hijo de la iteración
                Element elementoTraducido = null; // Se inicializa el elemento para el nuevo documento como null

                // Se verifica si el nodo actual es de tipo Elemento
                if (nodoHijo.getNodeType() == Node.ELEMENT_NODE) {
                    String nombreTraducido = traducirNombre(nodoHijo.getNodeName()); // Se obtiene el nombre traducido del nodo actual

                    elementoTraducido = docNuevo.createElement(nombreTraducido); // Se crea el elemento para el nuevo documento
                    nodoPadreNuevo.appendChild(elementoTraducido); // Se agrega el elemento al nodo padre para el nuevo documento

                    // Se verifica si el nodo original tiene atributos
                    if (nodoHijo.hasAttributes()) {
                        NamedNodeMap atributos = nodoHijo.getAttributes(); // Se obtiene los atributos del nodo original

                        // Bucle que recorre la lista de atributos
                        for (int j = 0; j < atributos.getLength(); j++) {
                            Node atributo = atributos.item(j); // Se obtiene el atributo de la iteración

                            // Se agrega el atributo al element
                            elementoTraducido.setAttribute(atributo.getNodeName(), atributo.getNodeValue());
                        }
                    }
                } else if (nodoHijo.getNodeType() == Node.TEXT_NODE) { // Se verifica si el nodo actual es de tipo Texto
                    Text textoTraducido = docNuevo.createTextNode(nodoHijo.getNodeValue()); // Se crea un nodo de tipo Texto
                    nodoPadreNuevo.appendChild(textoTraducido); // Se agrega el texto al nodo padre
                }

                // Se verifica si el elemento para el nuevo documento se ha creado
                if (elementoTraducido != null) {
                    traducirYCopiar(nodoHijo, docNuevo, elementoTraducido); // Llamada recursiva del método
                }
            }
        }
    }

    // Método para obtener el nombre traducido de un nodo
    private static String traducirNombre(String nombre) {

        // Se verifica el nombre original y se traduce
        switch (nombre) {
            case "book":
                return "Libro"; // Traduce "book" a "Libro"
            case "author":
                return "Autor"; // Traduce "author" a "Autor"
            case "title":
                return "Titulo"; // Traduce "title" a "Titulo"
            case "genre":
                return "Genero"; // Traduce "genre" a "Genero"
            case "price":
                return "Precio"; // Traduce "price" a "Precio"
            case "publish_date":
                return "Fecha_publicacion"; // Traduce "publish_date" a "Fecha_publicacion"
            case "description":
                return "Descripcion"; // Traduce "description" a "Descripcion"
            default:
                return nombre; // Devuelve el nombre original si no hay traducción
        }
    }
}
