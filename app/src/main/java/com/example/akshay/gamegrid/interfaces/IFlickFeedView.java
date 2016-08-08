package com.example.akshay.gamegrid.interfaces;

import com.example.akshay.gamegrid.rest.model.FlickrFeed;

/**
 * Created by akshay on 1/8/16.
 */
public interface IFlickFeedView {

    void onFeedFetchSuccess(FlickrFeed flickrFeed);
    void onFeedFetchError();
}
