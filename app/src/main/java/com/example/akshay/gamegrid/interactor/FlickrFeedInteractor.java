package com.example.akshay.gamegrid.interactor;

import android.app.Application;


import com.example.akshay.gamegrid.MyApp;
import com.example.akshay.gamegrid.interfaces.IFlickFeedView;
import com.example.akshay.gamegrid.rest.RetrofitClient;
import com.example.akshay.gamegrid.rest.model.FlickrFeed;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by akshay on 1/8/16.
 */
public class FlickrFeedInteractor {

    @Inject
    RetrofitClient client;

    private IFlickFeedView flickFeedView;
    private Application application;

    public FlickrFeedInteractor(IFlickFeedView flickFeedView, Application application) {
        this.flickFeedView = flickFeedView;
        this.application = application;
        ((MyApp)application).getAppComponent().inject(this);
    }

    public void fetchFlickrFeed() {
        Call<FlickrFeed> flickrFeedCall = client.getFlickrFeed();
        flickrFeedCall.enqueue(new Callback<FlickrFeed>() {
            @Override
            public void onResponse(Response<FlickrFeed> response, Retrofit retrofit) {
                FlickrFeed flickrFeed = response.body();
                flickFeedView.onFeedFetchSuccess(flickrFeed);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                flickFeedView.onFeedFetchError();
            }
        });
    }
}
