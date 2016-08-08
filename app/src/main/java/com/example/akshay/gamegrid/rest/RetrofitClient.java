package com.example.akshay.gamegrid.rest;

import com.example.akshay.gamegrid.rest.model.FlickrFeed;
import com.example.akshay.gamegrid.utils.AppConstants;


import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by akshay on 28/7/16.
 */
public interface RetrofitClient {

    @GET(AppConstants.FEED_URL)
    Call<FlickrFeed> getFlickrFeed();




}
