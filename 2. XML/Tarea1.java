
import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

public class Dom_parser {

    private static final String SEPARADOR = " ";

    public static void muestraNodo(Node nodo, int level, PrintStream ps) {
        // Se verifica si el parámetro nodo es de tipo Documento o no
        if (nodo.getNodeType() == Node.DOCUMENT_NODE) {
            Document doc = (Document) nodo; // Se realiza un cast del nodo actual a tipo Documnet

            ps.println("Documento XML");
            ps.println("Codificacion: " + doc.getXmlEncoding()); // Se imprime la codificación del XML
            ps.println("Version: " + doc.getXmlVersion()); // Se imprime la versión del XML
            ps.println();

            nodo = ((Document) nodo).getDocumentElement(); // Se obtiene el elemento raíz del documento
            ps.println(SEPARADOR.repeat(level) + nodo.getNodeName().toUpperCase()); // Se imprime nombre el nodo raíz

        } else {
            // Se imprime el nombre del nodo
            ps.print(SEPARADOR.repeat(level) + nodo.getNodeName().toUpperCase() + ": ");
        }

        // Se verifica si el nodo es de tipo Elemento
        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
            NamedNodeMap atributos = nodo.getAttributes(); // Se obtiene los atributos del nodo

            // Se verifica si la lista de atributos no está vacía
            if (atributos != null && atributos.getLength() > 0) {
                // Bucle que recorre la lista de atributos
                for (int i = 0; i < atributos.getLength(); i++) {
                    Node atributo = atributos.item(i); // Se define el atributo
                    ps.println("[" + atributo.getNodeName() + " = " + atributo.getNodeValue() + "] ");
                }
            }

            NodeList nodosHijos = nodo.getChildNodes(); // Se obtiene los nodos hijos del nodo

            // Se verifica si la lista de nodos hijos no está vacía
            if (nodosHijos != null) {
                // Bucle que recorre la lista de nodos hijos
                for (int j = 0; j < nodosHijos.getLength(); j++) {
                    Node nodoHijo = nodosHijos.item(j); // Se define el nodo hijo
                    // Se verifica si el nodo hijo es de tipo Elemento o Texto
                    if (nodoHijo.getNodeType() == Node.ELEMENT_NODE) {
                        // Llamada recursiva del método para los nodos hijos
                        muestraNodo(nodoHijo, level + 1, ps);
                    } else if (nodoHijo.getNodeType() == Node.TEXT_NODE) {
                        String valorTexto = nodoHijo.getNodeValue().trim(); // Se obtiene el texto del nodo
                        // Se verifica si el nodo no está vacío
                        if (!valorTexto.isEmpty()) {
                            ps.print(valorTexto); // Imprime el texto sin saltos de línea
                        }
                    }
                }
            }
        }

        ps.println();
    }

    public static void main(String[] args) {

        String ruta = "catalog.xml"; // Se define la ruta del archivo XML

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(ruta));
            muestraNodo(doc, 0, System.out);
        } catch (FileNotFoundException | ParserConfigurationException | SAXException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
