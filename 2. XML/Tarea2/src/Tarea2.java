
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

            /*System.out.println("Lista de titulos: \n");
            mostrarTextoNodo(doc, "title"); // Se llama al método mostrarTextoNodo para mostrar los títulos de todos los libros
             */
            // Se crea un instancia de DOMImplementarion
            DOMImplementation domImpl = db.getDOMImplementation();

            Document librosXML = domImpl.createDocument(null, "catalogo", null);

            Element raizOriginal = doc.getDocumentElement();
            traducirYCopiar(doc, librosXML, librosXML.getDocumentElement());

            // Crear un TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            // Crear un nuevo Transformer
            Transformer transformer = transformerFactory.newTransformer();

            // Crear una nueva DOMSource con el documento traducido
            DOMSource source = new DOMSource(librosXML);

            // Crear un nuevo StreamResult para el archivo de salida
            StreamResult result = new StreamResult(new File("libros.xml"));

            // Realizar la transformación
            transformer.transform(source, result);

            System.out.println("El archivo libros.xml ha sido creado con éxito.");

            //muestraNodo(librosXML, 0, System.out);
        } catch (FileNotFoundException | ParserConfigurationException | SAXException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Método que muestra por pantalla el texto de un nodo dado
    public static void mostrarTextoNodo(Document doc, String nombreNodo) {

        // Se obtiene todos los nodos que el nombre del nodo dado por parámetro
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

    private static void traducirYCopiar(Node nodoOriginal, Document docNuevo, Node nodoPadreNuevo) {

        // Se verifica si el nodo original pasado por parámetro es de tipo Documento
        if (nodoOriginal.getNodeType() == Node.DOCUMENT_NODE) {
            nodoOriginal = ((Document) nodoOriginal).getDocumentElement(); // Se obtiene el elemento raíz del documento
        }

        // Se verifica si el nodo es de tipo Elemento
        if (nodoOriginal.getNodeType() == Node.ELEMENT_NODE) {
            String nombreTraducido = traducirNombre(nodoOriginal.getNodeName()); // Se obtiene el nombre del nodo traducido
            Element elementoTraducido = docNuevo.createElement(nombreTraducido);
            nodoPadreNuevo.appendChild(elementoTraducido);

            // Se verifica si el nodo original tiene atributos
            if (nodoOriginal.hasAttributes()) {
                NamedNodeMap atributos = nodoOriginal.getAttributes(); // Se obtiene una lista con los atributos del nodo

                // Bucle que recorre la lista de atributos
                for (int i = 0; i < atributos.getLength(); i++) {
                    Node atributo = atributos.item(i); // Se obtiene el atributo de la iteración
                    String nombreAtributo = atributo.getNodeName(); // Se obtiene el nombre del atributo
                    String valorAtributo = atributo.getNodeValue(); // Se obtiene el valor del atributo
                    elementoTraducido.setAttribute(nombreAtributo, valorAtributo);

                }
            }           // Se verifica si el nodo original tiene hijos
            if (nodoOriginal.hasChildNodes()) {
                NodeList nodosHijo = nodoOriginal.getChildNodes(); // Se obtiene la lista de hijos del nodo

                for (int i = 0; i < nodosHijo.getLength(); i++) {
                    Node nodoHijo = nodosHijo.item(i);
                    traducirYCopiar(nodoHijo, docNuevo, elementoTraducido);
                }
            }

        } else if (nodoOriginal.getNodeType() == Node.TEXT_NODE) {
            Text textoTraducido = docNuevo.createTextNode(nodoOriginal.getNodeValue());
            nodoPadreNuevo.appendChild(textoTraducido);
        }
    }

    private static String traducirNombre(String nombre) {
        switch (nombre) {
            case "book":
                return "Libro";
            case "author":
                return "Autor";
            case "title":
                return "Titulo";
            case "genre":
                return "Genero";
            case "price":
                return "Precio";
            case "publish_date":
                return "Fecha_de_publicacion";
            case "description":
                return "Descripcion";
            default:
                return nombre;
        }
    }

    public static void muestraNodo(Node nodo, int level, PrintStream ps) {

        // Se verifica si el parámetro nodo es de tipo Documento
        if (nodo.getNodeType() == Node.DOCUMENT_NODE) {
            Document doc = (Document) nodo; // Se realiza un cast del nodo actual a tipo Document
            ps.print("<?xml version=\"" + doc.getXmlVersion() + "\""); // Se imprime la versión del XML
            ps.println(" encoding=\"" + doc.getXmlEncoding() + "\"?>"); // Se imprime la codificación del XML

            nodo = ((Document) nodo).getDocumentElement(); // Se obtiene el elemento raíz del documento
            muestraNodo(nodo, 0, ps); // Se llama recursivamente a muestraNodo para imprimir el XML
            return; // Se sale del método
        }

        // Se verifica si el nodo es de tipo Elemento
        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
            ps.print(SEPARADOR.repeat(level) + "<" + nodo.getNodeName()); // Se imprime la etiqueta de apertura del nodo

            // Se verifica si el nodo tiene atributos
            if (nodo.hasAttributes()) {
                NamedNodeMap atributos = nodo.getAttributes(); // Se obtiene la lista de atributos del nodo

                // Se recorre la lista de atributos
                for (int i = 0; i < atributos.getLength(); i++) {
                    Node atributo = atributos.item(i); // Se obtiene el atributo de la iteración
                    ps.print(" " + atributo.getNodeName() + "=\"" + atributo.getNodeValue() + "\""); // Se imprime el nombre y valor del atributo
                }
            }

            NodeList nodosHijo = nodo.getChildNodes(); // Se obtiene la lista de hijos del nodo

            // Se verifica si el nodo no tiene hijos
            if (nodosHijo.getLength() == 0) {
                ps.println("/>"); // Se cierra la etiqueta del nodo vacío
            } else if (nodosHijo.getLength() == 1 && nodosHijo.item(0).getNodeType() == Node.TEXT_NODE) { // Se verifica si el nodo tiene solo 1 hijo y es de tipo Texto
                String texto = nodosHijo.item(0).getNodeValue().trim(); // Se obtiene el texto del nodo
                ps.println(">" + texto + "</" + nodo.getNodeName() + ">"); // Se imprime el texto y se cierra la etiqueta del nodo padre
            } else {
                ps.println(">"); // Se cierra la etiqueta de apertura del nodo

                // Bucle que recorre la lista de nodos hijos
                for (int i = 0; i < nodosHijo.getLength(); i++) {
                    Node nodoHijo = nodosHijo.item(i); // Se obtiene el nodo hijo de la iteración

                    // Se verifica si el nodo hijo es de tipo Elemento
                    if (nodoHijo.getNodeType() == Node.ELEMENT_NODE) {
                        muestraNodo(nodoHijo, level + 1, ps); // Llamada recursiva del método
                    } else if (nodoHijo.getNodeType() == Node.TEXT_NODE) { // Se verifica si el nodo hijo es de tipo texto
                        String texto = nodoHijo.getNodeValue().trim(); // Se obtiene el texto del nodo

                        // Se verifica si el texto no está vacío
                        if (!texto.isEmpty()) {
                            ps.println(SEPARADOR.repeat(level) + "  " + texto); // Se imprime el texto
                        }
                    }
                }

                ps.println(SEPARADOR.repeat(level) + "</" + nodo.getNodeName() + ">"); // Se imprime la etiqueta de cierre del nodo
            }
        }

    }
}
