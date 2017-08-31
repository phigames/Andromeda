package net.netii.phi.andromeda.perseus;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import net.netii.phi.andromeda.TextUtils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;

public class PerseusSection implements Serializable {

    private String head;
    private String ref;
    private Node contentNode;
    private Spanned spannedText;
    private PerseusSection[] subSections;

    public PerseusSection(String head, String ref) {
        this.subSections = new PerseusSection[0];
        this.head = head;
        this.ref = ref;
    }

    public PerseusSection(String head, PerseusSection[] subSections) {
        this.subSections = subSections;
        this.head = head;
    }

    public void setContent(Node contentNode, TextUtils.WordClickedCallback onWordClicked) {
        String text = getContentString(contentNode);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (Character.isLetter(character)) {
                word.append(character);
            } else {
                TextUtils.WordSpan wordSpan = new TextUtils.WordSpan(word.toString(), onWordClicked);
                int start = stringBuilder.length();
                stringBuilder.append(word);
                int end = stringBuilder.length();
                stringBuilder.setSpan(wordSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                word.setLength(0);
                //if (Character.isWhitespace(character)) {
                //    stringBuilder.append(" ");
                //} else {
                stringBuilder.append(character);
                //}
            }
        }
        this.spannedText = stringBuilder;
        //this.spannedText = new SpannedString(text);
        //setText(contentNode.getTextContent(), onWordClicked);
    }

    private String getContentString(Node contentNode) {
        if (contentNode.getNodeType() == Node.TEXT_NODE) {
            return contentNode.getTextContent().trim();
        } else if (contentNode.getNodeName().equals("lb")) {
            /*NamedNodeMap attributes = contentNode.getAttributes();
            if (attributes.getLength() > 0 && contentNode.getAttributes().getNamedItem("rend").getNodeValue().equals("displayNum")) {
                return contentNode.getAttributes().getNamedItem("n").getNodeValue() + "\t";
            }
            return "\t";*/
            return "\n";
        } else if (contentNode.hasChildNodes()) {
            StringBuilder contentString = new StringBuilder();
            NodeList children = contentNode.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                contentString.append(getContentString(children.item(i)));
            }
            if (contentNode.getNodeName().equals("l")) {
                contentString.append("\n");
            }
            return contentString.toString();
        } else {
            return "";
        }
    }

    public String getHead() {
        return head;
    }

    public PerseusSection[] getSubsections() {
        return subSections;
    }

    public String getRef() {
        return ref;
    }

    public Spanned getText() {
        return spannedText;
    }

}
