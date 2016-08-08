package com.example.akshay.gamegrid.view.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import com.example.akshay.gamegrid.MyApp;
import com.example.akshay.gamegrid.R;
import com.example.akshay.gamegrid.interfaces.IGridImagesLoadListener;
import com.example.akshay.gamegrid.utils.DataSingleton;

import javax.inject.Inject;

/**
 * Created by akshay on 1/8/16.
 */
public class LoadingImagesDialogFragment extends DialogFragment {


    private final String TAG = LoadingImagesDialogFragment.class.getName();
    public static LoadingImagesDialogFragment newInstance() {
        return  new LoadingImagesDialogFragment();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        //request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_loading_images_dialog, container, false);

        //cannot dismiss dialog by clicking elsewhere
        setCancelable(false);
        return rootView;


    }
}
