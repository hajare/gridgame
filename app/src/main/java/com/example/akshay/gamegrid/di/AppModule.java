package com.example.akshay.gamegrid.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by akshay on 28/7/16.
 */
@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return application.getSharedPreferences("sharedPref",
                Context.MODE_PRIVATE);
    }

}
