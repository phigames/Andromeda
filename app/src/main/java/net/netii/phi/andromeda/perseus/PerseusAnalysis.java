package net.netii.phi.andromeda.perseus;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PerseusAnalysis {

    private String form;
    private String lemma;
    private String properties;

    // TODO clean up, store individual properties
    public PerseusAnalysis(Node analysisNode) {
        properties = "";
        NodeList children = analysisNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equals("form")) {
                form = children.item(i).getTextContent();
            } else if (children.item(i).getNodeName().equals("lemma")) {
                lemma = children.item(i).getTextContent();
            } else if (!children.item(i).getNodeName().equals("expandedForm")) {
                properties += children.item(i).getTextContent() + " ";
            }
        }
    }

    public String getForm() {
        return form;
    }

    public String getLemma() {
        return lemma;
    }

    public String getProperties() {
        return properties;
    }
}
