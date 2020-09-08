package com.example.shortmaker.DataClasses;

import android.graphics.drawable.Drawable;

public class Shortcut {
    public Shortcut() {
        /* empty public constructor for FireStore */
    }

    public Shortcut(String title, String imageUrl, int imageResource) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.imageResource = imageResource;
    }

    private String title;

    private String imageUrl;

    private int imageResource;

    private Drawable drawable;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDrawable(Drawable newDrawable) {
        drawable = newDrawable;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
