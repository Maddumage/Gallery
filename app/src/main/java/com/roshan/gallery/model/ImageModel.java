package com.roshan.gallery.model;

import java.io.Serializable;


public class ImageModel implements Serializable{
    private String id;
    private String small, regular, full;
    private int like;
    private String description;
    private boolean isFavorite;
    private int width;
    private int height;
    private String owner;


    public ImageModel() {
    }

    public ImageModel(String id, String small, String regular, String full, int like, String description) {
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
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
