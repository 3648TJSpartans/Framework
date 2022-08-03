package frc.robot.Framework.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLMerger {
    public XMLMerger() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
        
        
    }
    public String merger(String element, String... strings){
        try{

            File file = File.createTempFile("temp", ".xml");
            System.out.println(file.getAbsolutePath());
            file.deleteOnExit();

            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();   
            domFactory.setIgnoringComments(true);  
            DocumentBuilder builder = domFactory.newDocumentBuilder();   
            Document doc = builder.parse(new File("/home/lvuser/deploy/" + strings[0]));   

            NodeList nodes = doc.getElementsByTagName(element);  

            for(int i = 1; i < strings.length; i++){
                Document doc1 = builder.parse(new File("/home/lvuser/deploy/" + strings[i]));
                NodeList nodes1 = doc1.getElementsByTagName(element);
                for(int j=0;j<nodes1.getLength();j=j+1){  

                    Node n= (Node) doc.importNode(nodes1.item(j), true);  
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
            System.out.println("merge complete");

            return file.getAbsolutePath();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //return finalFile;
    }
    
}
