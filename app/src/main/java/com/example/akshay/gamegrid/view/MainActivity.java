package com.example.akshay.gamegrid.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.akshay.gamegrid.MyApp;
import com.example.akshay.gamegrid.R;
import com.example.akshay.gamegrid.interfaces.IFlickFeedView;
import com.example.akshay.gamegrid.utils.AppUtils;
import com.example.akshay.gamegrid.utils.DataSingleton;

import com.example.akshay.gamegrid.interactor.FlickrFeedInteractor;

import com.example.akshay.gamegrid.rest.model.FlickrFeed;


import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IFlickFeedView {

    private Button mButton;

    @Inject
    DataSingleton singleton;

    private final String TAG  = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.bStartGame);
        mButton.setOnClickListener(this);

        ((MyApp)getApplication()).getAppComponent().inject(this);

        AppUtils.showSimpleProgressDialog(this, "Fetching Data", "Getting Flickr Feed", false);


        FlickrFeedInteractor flickrFeedInteractor = new FlickrFeedInteractor(this, getApplication());
        flickrFeedInteractor.fetchFlickrFeed();




    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bStartGame) {
            Intent gameActivityIntent = new Intent(this, GameGridActivity.class);
            startActivity(gameActivityIntent);
            this.finish();
        }
    }

    @Override
    public void onFeedFetchSuccess(FlickrFeed flickrFeed) {
        singleton.setFlickrFeed(flickrFeed);
        AppUtils.removeSimpleProgressDialog();
        Log.d(TAG, "got the feed");
    }

    @Override
    public void onFeedFetchError() {
        AppUtils.removeSimpleProgressDialog();
        Log.d(TAG, "error getting the feed");
        Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show();
    }
}
