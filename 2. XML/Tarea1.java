package Tarea1;

import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

public class Tarea1 {

    private static final String SEPARADOR = "  ";

    // Método que muestra el contenido de un documento XML
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

    public static void main(String[] args) {

        String ruta = "catalog.xml"; // Se define la ruta del archivo XML

        // Se crea una nueva instancia de DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true); // Se configura para ignorar los comentarios
        dbf.setIgnoringElementContentWhitespace(true); // Se configura para ignorar los espacios en blanco

        try {
            DocumentBuilder db = dbf.newDocumentBuilder(); // Se obtiene un objeto DocumentBuilder de la fábrica
            Document doc = db.parse(new File(ruta)); // Se carga y analiza el archivo XML especificado en la ruta
            muestraNodo(doc, 0, System.out); // Se llama al método muestraNodo
        } catch (FileNotFoundException | ParserConfigurationException | SAXException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
