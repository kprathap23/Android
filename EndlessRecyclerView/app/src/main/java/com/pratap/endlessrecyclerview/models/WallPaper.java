package com.pratap.endlessrecyclerview.models;

import java.io.Serializable;

/**
 * Created by pratap.kesaboyina on 29-06-2015.
 */
public class WallPaper implements Serializable {

    private String id;

    public WallPaper() {

    }

    public WallPaper(String id, String origUrl, String thumbUrl, String downloads, String fav) {
        this.id = id;
        this.origUrl = origUrl;
        this.thumbUrl = thumbUrl;
        this.downloads = downloads;
        this.fav = fav;
    }

    private String origUrl;
    private String thumbUrl;
    private String downloads;
    private String fav;


    public String getOrigUrl() {
        return origUrl;
    }

    public void setOrigUrl(String origUrl) {
        this.origUrl = origUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }


}
