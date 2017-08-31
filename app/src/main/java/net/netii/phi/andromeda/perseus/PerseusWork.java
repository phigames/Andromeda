package net.netii.phi.andromeda.perseus;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.concurrent.Callable;

public class PerseusWork {

    public final PerseusWorkInfo info;
    private PerseusTableOfContents toc;

    public PerseusWork(PerseusWorkInfo info) {
        this.info = info;
    }

    public PerseusWork(Node workNode) {
        String id = workNode.getAttributes().getNamedItem("id").getNodeValue();
        String urn = null, author = null, title = null;
        boolean loaded = false;
        int sectionLevel = 0;
        NodeList children = workNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            switch (child.getNodeName()) {
                case "urn":
                    urn = child.getTextContent();
                    break;
                case "author":
                    author = child.getTextContent();
                    break;
                case "title":
                    title = child.getTextContent();
                    break;
                case "section-level":
                    sectionLevel = Integer.parseInt(child.getTextContent());
                    break;
                case "loaded":
                    loaded = Boolean.parseBoolean(child.getTextContent());
            }
        }
        info = new PerseusWorkInfo(id, urn, author, title, sectionLevel, loaded);
    }

    public PerseusTableOfContents getTableOfContents() {
        return toc;
    }

    /*public PerseusWork(String urn, final PerseusWorkLoadedCallback onLoaded) {
        this.info = new PerseusWorkInfo(urn, "n/a", "n/a");
        final PerseusWork self = this;
        PerseusRequest request = PerseusRequest.newGetValidReffRequest(urn, new PerseusRequestCallback() {
            @Override
            public void onFinished(Document response) {
                parseXML(response, new Callable<Void>() {
                    @Override
                    public Void onFinished() throws Exception {
                        onLoaded.onFinished(self);
                        return null;
                    }
                });
            }
        });
        request.start();
        this.lastPassageIndex = 0;
    }*/

    /*private void parseXML(Document xml, final Callable<Void> onFinished) {
        NodeList urnNodes = xml.getElementsByTagName("urn");
        validReff = new String[urnNodes.getLength()];
        for (int i = 0; i < urnNodes.getLength(); i++) {
            validReff[i] = urnNodes.item(i).getTextContent();
        }
        PerseusRequest request = PerseusRequest.newGetPassageRequest(validReff[0], new PerseusRequestCallback() {
            @Override
            public void onFinished(Document response) {
                author = response.getElementsByTagName("cts:groupname").item(0).getTextContent();
                title = response.getElementsByTagName("cts:title").item(0).getTextContent();
                try {
                    onFinished.onFinished();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        request.start();
    }*/

}
