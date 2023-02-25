package frc.robot.framework.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtil {

    /**
     *
     */
    private static final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

    public XMLUtil() {
        dbFactory.setIgnoringComments(true);
    }

    public static Document Parse(String path) {
        File file = new File(path);
        return Parse(file);
    }

    public static Document Parse(File file) {
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            //System.out.println();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<File> listOfFiles(File dirPath) {
        File fileList[] = dirPath.listFiles();
        if (fileList == null) {
            return null;
        }
        ArrayList<File> files = new ArrayList<>();
        for (File file : fileList) {
            if (file.isFile() && file.getName().endsWith("xml")) {
                files.add(file);
            } else {
                ArrayList<File> recursiveFiles = listOfFiles(file);
                if (recursiveFiles != null) {
                    files.addAll(recursiveFiles);
                }
            }
        }
        return files;
    }

    public static void prettyPrint(Element xml) {
        prettyPrint((Node) xml);
    }

    public static void prettyPrint(Document xml) {
        prettyPrint((Node) xml);
    }

    public static void prettyPrint(Node xml) {
        Transformer tf;
        try {
            tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            Writer out = new StringWriter();
            tf.transform(new DOMSource(xml), new StreamResult(out));
            System.out.println(out.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Document mergeNew(File... files) {
        try {

            Document to = Parse(files[0]);
            Element toRoot = to.getDocumentElement();

            Node child = null;
            for (int i = 1; i < files.length; i++) {
                Document from = Parse(files[i]);
                Element fromRoot = from.getDocumentElement();
                while ((child = fromRoot.getFirstChild()) != null) {
                    to.adoptNode(child);
                    toRoot.appendChild(child);
                }
            }

            return to;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static File merger(String element, File... files) {
        try {
            File file = File.createTempFile("temp", ".xml");
            //System.out.println(file.getAbsolutePath());
            file.deleteOnExit();

            Document doc = Parse(files[0]);

            NodeList nodes = doc.getElementsByTagName(element);
            for (int i = 1; i < files.length; i++) {
                Document doc1 = Parse(files[i]);
                NodeList nodes1 = doc1.getElementsByTagName(element);
                for (int j = 0; j < nodes1.getLength(); j = j + 1) {

                    Node n = (Node) doc.importNode(nodes1.item(j), true);
                    nodes.item(j).getParentNode().appendChild(n);

                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");

            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            Writer output = null;

            output = new BufferedWriter(new FileWriter(file.getAbsolutePath()));

            String xmlOutput = result.getWriter().toString();
            output.write(xmlOutput);
            output.close();
            //System.out.println("merge complete");

            return file;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // return finalFile;
    }

}
