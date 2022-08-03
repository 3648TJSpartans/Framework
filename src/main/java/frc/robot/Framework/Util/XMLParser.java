package frc.robot.Framework.Util;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

public class XMLParser{
    private Document doc;
    private DocumentBuilder dBuilder;

    public XMLParser(String path){
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
                    
            doc.getDocumentElement().normalize();
            prettyPrint(doc);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Element getRootElement(){
        return doc.getDocumentElement();
    }
    public Document getDoc(){
        return doc;
    }
    public DocumentBuilder getDocumentBuilder(){
        return dBuilder;
    }
    public static final void prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        System.out.println(out.toString());
    }

}