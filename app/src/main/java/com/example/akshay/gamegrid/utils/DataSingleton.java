package com.example.akshay.gamegrid.utils;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.example.akshay.gamegrid.rest.model.FlickrFeed;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by akshay on 1/8/16.
 */
@Singleton
public class DataSingleton {

    private Application mApplication;
    private FlickrFeed flickrFeed;
    private boolean hasGuessingStarted = false;
    private int noOfGuessesTaken = 0;

    private ObservableList observableList = new ObservableArrayList<>();
    @Inject
    public DataSingleton(Application application) {
        this.mApplication = application;

        for (int i = 0; i < 9; i++) {
            observableList.add(false);
        }
    }

    public boolean hasGuessingStarted() {
        return hasGuessingStarted;
    }

    public void setHasGuessingStarted(boolean hasGuessingStarted) {
        this.hasGuessingStarted = hasGuessingStarted;
    }

    public FlickrFeed getFlickrFeed() {
        return flickrFeed;
    }

    public void setFlickrFeed(FlickrFeed flickrFeed) {
        this.flickrFeed = flickrFeed;
    }


    public int getNoOfGuessesTaken() {
        return noOfGuessesTaken;
    }

    public void setNoOfGuessesTaken(int noOfGuessesTaken) {
        this.noOfGuessesTaken = noOfGuessesTaken;
    }

    public ObservableList getObservableList() {
        return observableList;
    }

    public void setObservableList(ObservableList observableList) {
        this.observableList = observableList;
    }
}
