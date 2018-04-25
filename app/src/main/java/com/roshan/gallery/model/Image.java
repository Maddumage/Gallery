package com.roshan.gallery.model;

import java.io.Serializable;


public class Image implements Serializable{
    private String id;
    private String small, regular, full;
    private int like;
    private String description;

    public Image() {
    }

    public Image(String id, String small, String regular, String full, int like, String description) {
        this.id = id;
        this.small = small;
        this.regular = regular;
        this.full = full;
        this.like = like;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
