package com.mphstar.androidnative;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

public class ListItems {
    private int images_path;
    private String title;
    private String desc;

    private Class<?> classDestination;

    public ListItems(int images_path, String title, String desc, Class<?> classDestination) {
        this.images_path = images_path;
        this.title = title;
        this.desc = desc;
        this.classDestination = classDestination;
    }

    public int getImages_path() {
        return images_path;
    }

    public void setImages_path(int images_path) {
        this.images_path = images_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Class<?> getClassDestination() {
        return classDestination;
    }

    public void setClassDestination(Class<?> classDestination) {
        this.classDestination = classDestination;
    }
}
