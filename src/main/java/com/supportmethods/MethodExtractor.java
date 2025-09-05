package com.supportmethods;

import org.w3c.dom.*;
import javax.xml.parsers.*;

public class MethodExtractor {
    public static void main(String[] args) throws Exception {
        String xmlFilePath = "C:\\Innovation Project\\CodeCoverageWithAI\\target\\site\\jacoco\\jacoco.xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFilePath);

        NodeList methodNodes = doc.getElementsByTagName("method");
        for (int i = 0; i < methodNodes.getLength(); i++) {
            Element method = (Element) methodNodes.item(i);
            NodeList counters = method.getElementsByTagName("counter");
            for (int j = 0; j < counters.getLength(); j++) {
                Element counter = (Element) counters.item(j);
                if ("METHOD".equals(counter.getAttribute("type")) && "0".equals(counter.getAttribute("covered"))) {
                    String name = method.getAttribute("name");
                    if(!name.equals("main") && !name.equals("<init>")) {
                        String desc = method.getAttribute("desc");
                        // Get parent <class> and <package>
                        Element classElem = (Element) method.getParentNode();
                        Element packageElem = (Element) classElem.getParentNode();
                        String packageName = packageElem.getAttribute("name");
                        System.out.println("Uncovered Method: " + name + " in class: "+ classElem.getAttribute("name")+" in package: " + packageName);
                    }
                }
            }
        }
    }
}


