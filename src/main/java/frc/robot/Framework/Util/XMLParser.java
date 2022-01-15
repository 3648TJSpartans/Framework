package frc.robot.Framework.Util;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLParser{
    private Document doc;

    public XMLParser(String path){
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
                    
            doc.getDocumentElement().normalize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Element getRootElement(){
        return doc.getDocumentElement();
    }
    public void parse(String TagName, Element system){
        // NodeList buttonNodes = system.getElementsByTagName(TagName);
        //     for(int i = 0; i < buttonNodes.getLength(); i++){
        //         Node currentButton = buttonNodes.item(i);
        //         if(currentButton.getNodeType() == Node.ELEMENT_NODE){
        //             Element buttonElement = (Element)currentButton;
        //             return (Element)currentButton;
                    
                    
        //         }
        //     }
            return;
    }

}