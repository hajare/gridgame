package com.example.akshay.gamegrid;

import android.app.Application;


import com.example.akshay.gamegrid.di.AppComponent;
import com.example.akshay.gamegrid.di.AppModule;
import com.example.akshay.gamegrid.di.DaggerAppComponent;
import com.example.akshay.gamegrid.di.RetrofitModule;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by akshay on 28/7/16.
 */
public class MyApp extends Application {

    private AppComponent mAppComponent = createAppComponent();

    @Override
    public void onCreate() {
        super.onCreate();


        /*Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);*/


    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .retrofitModule(new RetrofitModule())
                .appModule(new AppModule(this))
                .build();
    }


    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
