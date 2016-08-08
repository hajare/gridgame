package com.example.akshay.gamegrid.data;

/**
 * Created by akshay on 28/7/16.
 */
public class Image {

    private String mImageUrl;
    private boolean isVisible;


    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
