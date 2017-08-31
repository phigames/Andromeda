package net.netii.phi.andromeda.perseus;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

// TODO remove, replace with simple sections array
public class PerseusTableOfContents {

    private PerseusSection[] sections;

    public PerseusTableOfContents(PerseusSection[] sections) {
        this.sections = sections;
    }

    public PerseusSection[] getSections() {
        return sections;
    }

}
