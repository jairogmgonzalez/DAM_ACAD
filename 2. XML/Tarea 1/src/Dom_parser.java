
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

    private static final String SEPARADOR = "   ";

    public static void muestraNodo(Node nodo, int level, PrintStream ps) {
        if (nodo.getNodeType() == Node.DOCUMENT_NODE) {
            nodo = ((Document) nodo).getDocumentElement();
        }

        ps.println(SEPARADOR.repeat(level) + nodo.getNodeName());

        NodeList nodosHijos = nodo.getChildNodes();

        for (int i = 0; i < nodosHijos.getLength(); i++) {
            Node nodoHijo = nodosHijos.item(i);

            if (nodoHijo.getNodeType() == Node.ELEMENT_NODE && nodoHijo.getNodeValue() == null) {
                ps.println(SEPARADOR.repeat(level + 1) + nodoHijo.getNodeName());

                NamedNodeMap atributos = nodoHijo.getAttributes();
                if (atributos.getLength() != 0) {
                    for (int j = 0; j < atributos.getLength(); j++) {
                        Node atributo = atributos.item(j);
                        ps.println(SEPARADOR.repeat(level + 1) + atributo.getNodeName() + " = " + atributo.getNodeValue());
                    }
                }
            }

            if (nodoHijo.hasChildNodes()) {
                NodeList subNodos = nodoHijo.getChildNodes();
                for (int k = 0; k < subNodos.getLength(); k++){
                    System.out.println(SEPARADOR.repeat(level + 1) + "pepe");
                }
            }
        }
    }

    public static void main(String[] args) {
        String ruta = args[0];

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
