package net.netii.phi.andromeda.perseus;

import android.content.Context;
import android.util.Xml;

import net.netii.phi.andromeda.TextUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PerseusData {

    private static final String WORKS_FILE = "works.xml";
    private static final String TABLE_OF_CONTENTS_FILE = "_toc.xml";

    public static class Works {

        public static void load(final Context context, final DataLoadedCallback<ArrayList<PerseusWork>> onLoaded, boolean update) {
            //File worksFile = new File(context.getFilesDir() + File.separator + "works.xml");
            File worksFile = context.getFileStreamPath(WORKS_FILE);
            if (update || !worksFile.exists()) {
                updateFile(context, new FileUpdatedCallback() {
                    @Override
                    public void onUpdated() {
                        onLoaded.onLoaded(parse(context));
                    }
                });
            } else {
                onLoaded.onLoaded(parse(context));
            }
        }

        private static void updateFile(Context context, FileUpdatedCallback onUpdated) {
            // TODO get works file from server
            try {
                FileOutputStream outputStream = context.openFileOutput(WORKS_FILE, Context.MODE_PRIVATE);
                XmlSerializer serializer = Xml.newSerializer();
                serializer.setOutput(outputStream, "UTF-8");
                serializer.startDocument(null, true);
                serializer.startTag(null, "works");

                serializer.startTag(null, "work");
                serializer.attribute(null, "id", "Hom-Il");
                serializer.startTag(null, "urn");
                serializer.text("urn:cts:greekLit:tlg0012.tlg001");
                serializer.endTag(null, "urn");
                serializer.startTag(null, "author");
                serializer.text("Homer");
                serializer.endTag(null, "author");
                serializer.startTag(null, "title");
                serializer.text("Iliad");
                serializer.endTag(null, "title");
                serializer.startTag(null, "section-level");
                serializer.text("1");
                serializer.endTag(null, "section-level");
                serializer.startTag(null, "loaded");
                serializer.text("false");
                serializer.endTag(null, "loaded");
                serializer.endTag(null, "work");

                serializer.startTag(null, "work");
                serializer.attribute(null, "id", "Her-Hist");
                serializer.startTag(null, "urn");
                serializer.text("urn:cts:greekLit:tlg0016.tlg001");
                serializer.endTag(null, "urn");
                serializer.startTag(null, "author");
                serializer.text("Herodotus");
                serializer.endTag(null, "author");
                serializer.startTag(null, "title");
                serializer.text("Histories");
                serializer.endTag(null, "title");
                serializer.startTag(null, "section-level");
                serializer.text("2");
                serializer.endTag(null, "section-level");
                serializer.startTag(null, "loaded");
                serializer.text("false");
                serializer.endTag(null, "loaded");
                serializer.endTag(null, "work");

                serializer.startTag(null, "work");
                serializer.attribute(null, "id", "Eur-Iph");
                serializer.startTag(null, "urn");
                serializer.text("urn:cts:greekLit:tlg0006.tlg013");
                serializer.endTag(null, "urn");
                serializer.startTag(null, "author");
                serializer.text("Euripides");
                serializer.endTag(null, "author");
                serializer.startTag(null, "title");
                serializer.text("Iphigeneia in Taurus");
                serializer.endTag(null, "title");
                serializer.startTag(null, "section-level");
                serializer.text("0");
                serializer.endTag(null, "section-level");
                serializer.startTag(null, "loaded");
                serializer.text("false");
                serializer.endTag(null, "loaded");
                serializer.endTag(null, "work");

                serializer.endTag(null, "works");

                serializer.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                // TODO handle exceptions
            }
            onUpdated.onUpdated();
        }

        private static ArrayList<PerseusWork> parse(Context context) {
            ArrayList<PerseusWork> parsedWorks = new ArrayList<PerseusWork>();
            try {
                FileInputStream inputStream = context.openFileInput(WORKS_FILE);
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
                inputStream.close();
                NodeList workNodes = document.getElementsByTagName("work");
                for (int i = 0; i < workNodes.getLength(); i++) {
                    parsedWorks.add(new PerseusWork(workNodes.item(i)));
                }
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
                // TODO handle exceptions
            }
            return parsedWorks;
        }

    }

    public static class TableOfContents {

        public static void load(final Context context, final PerseusWork work, final DataLoadedCallback<PerseusTableOfContents> onLoaded) {
            if (!work.info.loaded) {
                updateFile(context, work, new FileUpdatedCallback() {
                    @Override
                    public void onUpdated() {
                        onLoaded.onLoaded(parse(context, work));
                    }
                });
            } else {

            }
        }

        private static void updateFile(final Context context, final PerseusWork work, final FileUpdatedCallback onUpdated) {
            PerseusRequest request = PerseusRequest.newTableOfContentsRequest(work.info.urn, new PerseusRequestCallback() {
                @Override
                public void onFinished(Document response) {
                    Node contentsNode = response.getElementsByTagName("contents").item(0);
                    removeChunksAtLevel(contentsNode, work.info.sectionLevel);
                    try {
                        FileOutputStream outputStream = context.openFileOutput(work.info.id + TABLE_OF_CONTENTS_FILE, Context.MODE_PRIVATE);
                        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(contentsNode), new StreamResult(outputStream));
                        outputStream.close();
                    } catch ( TransformerException | IOException e) {
                        e.printStackTrace();
                        // TODO handle exceptions
                    }
                    onUpdated.onUpdated();
                }

                private void removeChunksAtLevel(Node node, int level) {
                    NodeList children = node.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        Node child = children.item(i);
                        if (!child.getNodeName().equals("head")) {
                            if (level < 0) {
                                node.removeChild(child);
                            } else {
                                removeChunksAtLevel(child, level - 1);
                            }
                        }
                    }
                }
            });
            request.start();
        }

        private static PerseusTableOfContents parse(Context context, PerseusWork work) {
            PerseusTableOfContents toc = null;
            try {
                FileInputStream inputStream = context.openFileInput(work.info.id + TABLE_OF_CONTENTS_FILE);
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
                inputStream.close();
                Node contentsNode = document.getElementsByTagName("contents").item(0);
                NodeList children = contentsNode.getChildNodes();
                ArrayList<PerseusSection> sections = new ArrayList<PerseusSection>();
                for (int i = 0; i < children.getLength(); i++) {
                    if (children.item(i).getNodeName().equals("chunk")) {
                        sections.add(parseSection(children.item(i), 0));
                    }
                }
                toc = new PerseusTableOfContents(sections.toArray(new PerseusSection[0]));
            } catch (SAXException | IOException | ParserConfigurationException e) {
                e.printStackTrace();
                // TODO handle exceptions
            }
            return toc;
        }

        private static PerseusSection parseSection(Node node, int level) {
            NodeList children = node.getChildNodes();
            String head = null;
            ArrayList<PerseusSection> subSections = new ArrayList<PerseusSection>();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeName().equals("head")) {
                    head = child.getTextContent();
                } else if (child.getNodeName().equals("chunk")) {
                    subSections.add(parseSection(child, level + 1));
                }
            }
            if (subSections.size() > 0) {
                return new PerseusSection(head, subSections.toArray(new PerseusSection[0]));
            } else {
                return new PerseusSection(head, node.getAttributes().getNamedItem("ref").getNodeValue());
            }
        }

    }

    public static class Section {

        public static void loadText(final PerseusSection section, final TextUtils.WordClickedCallback onWordClicked, final DataLoadedCallback<PerseusSection> onLoaded) {
            PerseusRequest request = PerseusRequest.newSectionRequest(section.getRef(), new PerseusRequestCallback() {
                @Override
                public void onFinished(Document response) {
                    section.setContent(response.getElementsByTagName("body").item(0), onWordClicked);
                    //section.setText(response.getElementsByTagName("body").item(0).getTextContent(), onWordClicked);
                    onLoaded.onLoaded(section);
                }
            });
            request.start();
        }

    }

    public static class Research {

        public static void loadAnalyses(final String word, final DataLoadedCallback<ArrayList<PerseusAnalysis>> onLoaded) {
            PerseusRequest request = PerseusRequest.newAnalysisRequest(word, new PerseusRequestCallback() {
                @Override
                public void onFinished(Document response) {
                    NodeList analysisNodes = response.getElementsByTagName("analysis");
                    ArrayList<PerseusAnalysis> analyses = new ArrayList<PerseusAnalysis>();
                    for (int i = 0; i < analysisNodes.getLength(); i++) {
                        analyses.add(new PerseusAnalysis(analysisNodes.item(i)));
                    }
                    onLoaded.onLoaded(analyses);
                }
            });
            request.start();
        }

    }

    private interface FileUpdatedCallback {

        void onUpdated();

    }

    /*public interface WorksLoadedCallback {

        public void onLoaded(ArrayList<PerseusWork> works);

    }

    public interface TableOfContentsLoadedCallback {

        public void onLoaded(PerseusTableOfContents toc);

    }

    public interface SectionLoadedCallback {

        public void onLoaded(PerseusSection section);

    }*/

    public interface DataLoadedCallback<T> {

        public void onLoaded(T data);

    }

}
