package com.example.akshay.gamegrid.di;


import com.example.akshay.gamegrid.MyApp;
import com.example.akshay.gamegrid.adapters.ImageGridAdapter;
import com.example.akshay.gamegrid.interactor.FlickrFeedInteractor;
import com.example.akshay.gamegrid.view.GameGridActivity;
import com.example.akshay.gamegrid.view.MainActivity;
import com.example.akshay.gamegrid.view.fragment.LoadingImagesDialogFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by akshay on 28/7/16.
 */
@Singleton
@Component(modules = {AppModule.class, RetrofitModule.class})
public interface AppComponent {

    void inject (MyApp app);
    void inject (FlickrFeedInteractor interactor);
    void inject (MainActivity mainActivity);
    void inject (GameGridActivity activity);
    void inject (ImageGridAdapter adapter);
    void inject (LoadingImagesDialogFragment fragment);


}
