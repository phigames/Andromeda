package net.netii.phi.andromeda.perseus;

import java.io.Serializable;

public class PerseusWorkInfo implements Serializable {

    public final String id;
    public final String urn;
    public final String author;
    public final String title;
    public final int sectionLevel;
    public final boolean loaded;

    public PerseusWorkInfo(String id, String urn, String author, String title, int sectionLevel, boolean loaded) {
        this.id = id;
        this.urn = urn;
        this.author = author;
        this.title = title;
        this.sectionLevel = sectionLevel;
        this.loaded = loaded;
    }

}