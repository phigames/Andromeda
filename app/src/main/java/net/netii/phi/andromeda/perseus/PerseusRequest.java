package net.netii.phi.andromeda.perseus;

import android.os.AsyncTask;

import net.netii.phi.andromeda.TextUtils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PerseusRequest extends AsyncTask<String, Void, Document> {

    private String url;
    private PerseusRequestCallback onFinished;

    private PerseusRequest(String url, PerseusRequestCallback onFinished) {
        this.url = url;
        this.onFinished = onFinished;
    }

    public static PerseusRequest newGetCapabilitiesRequest(PerseusRequestCallback onFinished) {
        return new PerseusRequest("http://www.perseus.tufts.edu/hopper/CTS?request=GetCapabilities", onFinished);
    }

    public static PerseusRequest newGetValidReffRequest(String urn, PerseusRequestCallback onFinished) {
        return new PerseusRequest("http://www.perseus.tufts.edu/hopper/CTS?request=GetValidReff&urn=" + urn, onFinished);
    }

    public static PerseusRequest newGetPassageRequest(String urn, PerseusRequestCallback onFinished) {
        return new PerseusRequest("http://www.perseus.tufts.edu/hopper/CTS?request=GetPassage&urn=" + urn, onFinished);
    }

    public static PerseusRequest newTableOfContentsRequest(String urn, PerseusRequestCallback onFinished) {
        return new PerseusRequest("http://www.perseus.tufts.edu/hopper/xmltoc?doc=" + urn, onFinished);
    }

    public static PerseusRequest newSectionRequest(String ref, PerseusRequestCallback onFinished) {
        return new PerseusRequest("http://www.perseus.tufts.edu/hopper/xmlchunk?doc=" + ref, onFinished);
    }

    public static PerseusRequest newAnalysisRequest(String word, PerseusRequestCallback onFinished) {
        return new PerseusRequest("http://www.perseus.tufts.edu/hopper/xmlmorph?lang=greek&lookup=" + TextUtils.greekToBeta(word, false), onFinished);
    }

    public static PerseusRequest newLemmaRequest(String lemma, PerseusRequestCallback onFinished) {
        return new PerseusRequest("http://archimedes.fas.harvard.edu/cgi-bin/dict?word=" + TextUtils.greekToBeta(lemma, true), onFinished);
    }

    public void start() {
        execute(url);
    }

    @Override
    protected Document doInBackground(String... urls) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urls[0]).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream stream = connection.getInputStream();
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Document responseXML) {
        try {
            onFinished.onFinished(responseXML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}