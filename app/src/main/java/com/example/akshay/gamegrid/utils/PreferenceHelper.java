package com.example.akshay.gamegrid.utils;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by akshay on 2/8/16.
 */
public class PreferenceHelper {

    SharedPreferences mSharedPreferences;

    private Application mApplication;
    private final String TOP_SCORER = "topScorer";
    private final String TOP_SCORE = "topScore";


    @Inject
    public PreferenceHelper(SharedPreferences prefs, Application context) {
        this.mSharedPreferences = prefs;
        this.mApplication = context;
    }


    public void putTopScore(int topScore) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(TOP_SCORE, topScore);
        edit.commit();
    }

    public int getTopScore() {
        return mSharedPreferences.getInt(TOP_SCORE, 100);
    }


    public void putTopScorer(String topScorer) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(TOP_SCORER, topScorer);
        edit.commit();
    }

    public String getTopScorer() {
        return mSharedPreferences.getString(TOP_SCORER, null);
    }


}
