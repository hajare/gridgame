package com.example.akshay.gamegrid.view;


import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.akshay.gamegrid.MyApp;
import com.example.akshay.gamegrid.R;
import com.example.akshay.gamegrid.adapters.ImageGridAdapter;
import com.example.akshay.gamegrid.data.Image;
import com.example.akshay.gamegrid.interfaces.IGridImageClickedListener;
import com.example.akshay.gamegrid.interfaces.IGridImagesLoadListener;
import com.example.akshay.gamegrid.utils.DataSingleton;
import com.example.akshay.gamegrid.utils.PreferenceHelper;
import com.example.akshay.gamegrid.view.fragment.LoadingImagesDialogFragment;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import javax.inject.Inject;

public class GameGridActivity extends AppCompatActivity implements IGridImageClickedListener, IGridImagesLoadListener {
    private GridView mImageGridView;
    private TextView mTvTimer;
    private ProgressBar progressBar;
    private LinearLayout mTimerBarLayout;
    private LinearLayout mGuessImageLayout;
    private ImageView mGuessImage;
    private TextView mTvNoOfGuesses;
    private TextView mTvGameFinishTimer;

    private ImageGridAdapter mAdapter;
    private LoadingImagesDialogFragment fragment;

    private CustomCountDownTimer mGameFinishTimer;

    private Activity mActivity;

    private final Handler handler = new Handler();
    @Inject
    DataSingleton mDataSingleton;

    @Inject
    PreferenceHelper mPreferenceHelper;
    private List<Image> imageList;

    private final String TAG = GameGridActivity.class.getName();


    /****
     *
     * Activity Lifecycle CallBacks
     *
     *
     * ***/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_grid);
        ((MyApp)getApplication()).getAppComponent().inject(this);
        mActivity = this;
        imageList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Image images = new Image();
            images.setImageUrl(mDataSingleton.getFlickrFeed().getItems().get(i).getMedia().getM());
            images.setVisible(true);
            imageList.add(images);
        }

        mAdapter = new ImageGridAdapter(this, imageList);

        mImageGridView = (GridView) findViewById(R.id.gvImageGrid);
        mTvTimer = (TextView) findViewById(R.id.tvTimer);
        progressBar = (ProgressBar) findViewById( R.id.progressBar);
        mTimerBarLayout = (LinearLayout) findViewById(R.id.llTimerBar);
        mGuessImageLayout = (LinearLayout) findViewById(R.id.llImageLayout);
        mGuessImage = (ImageView) findViewById(R.id.ivGuessImage);
        mTvNoOfGuesses = (TextView) findViewById(R.id.tvNoOfGuesses);
        mTvGameFinishTimer = (TextView) findViewById(R.id.tvGameFinishTimer);

        mTvNoOfGuesses.setText("Total Guesses: " + "0");

        mTimerBarLayout.setVisibility(View.VISIBLE);

        progressBar.setMax(15);

        mTvTimer.setText("15");

        mImageGridView.setAdapter(mAdapter);


        fragment = LoadingImagesDialogFragment.newInstance();
        FragmentManager manager = getFragmentManager();
        fragment.show(manager, "AddOns");



    }



    /***
     *
     * Other Methods
     *
     * ***/
    private void scheduleGameTimer() {
        Log.d(TAG, "scheduleGameTimer Called");
        //count down timer for 15 seconds
        new CountDownTimer(15 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {

                mTvTimer.setText(String.valueOf(millisUntilFinished / 1000));

                progressBar.setProgress(15 - (int)(millisUntilFinished / 1000));
            }

            public void onFinish() {
                mTvTimer.setText("0");
                progressBar.setProgress(15);
                //hide images
                hideAllImages();
                mDataSingleton.setHasGuessingStarted(true);
                //till all images are guessed show image to user
                mTimerBarLayout.setVisibility(View.GONE);
                mGuessImageLayout.setVisibility(View.VISIBLE);

                showImageToUser();
                //ask user to guess

                scheduleGameFinishTimer();
            }
        }.start();

    }

    private void scheduleGameFinishTimer() {
        mGameFinishTimer = new CustomCountDownTimer(60 * 1000, 1000);
        mGameFinishTimer.start();
    }

    private void hideAllImages() {
        for (Image image : imageList) {
            image.setVisible(false);
            mAdapter.notifyDataSetChanged();
        }
    }


    private void showImageToUser() {
        List<Image> hiddenImageList = new ArrayList<>();
        boolean areAllImagesVisible = true;
        for (Image image : imageList) {
            if (!image.isVisible()) {
                areAllImagesVisible = false;
                hiddenImageList.add(image);
            }
        }

        if (!areAllImagesVisible) {

            Image image = pickRandomImageFromHiddenImages(hiddenImageList);
            if (image != null) {
                Picasso.with(this)
                        .load(image.getImageUrl())
                        .centerCrop()
                        .resize(100, 100)
                        .into(mGuessImage);

                mGuessImage.setTag(image.getImageUrl());
            }
        } else {
            //game over
            Toast.makeText(this, "GAME OVER!", Toast.LENGTH_LONG).show();
            if (mDataSingleton.getNoOfGuessesTaken() < mPreferenceHelper.getTopScore()) {
                //new top score
            }



            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent restartIntent = new Intent(mActivity, MainActivity.class);
                    startActivity(restartIntent);
                    mDataSingleton.setNoOfGuessesTaken(0);
                    mDataSingleton.setHasGuessingStarted(false);
                    mActivity.finish();
                }
            }, 3000);


        }
    }


    public Image pickRandomImageFromHiddenImages(List<Image> hiddenImageList) {
        if (hiddenImageList.size() <= 0){
            return null;
        }
        Random random = new Random();
        int imageIndex = random.nextInt(hiddenImageList.size());
        return hiddenImageList.get(imageIndex);
    }


    @Override
    public void onGridImageClicked(String imageUrl) {
        mDataSingleton.setNoOfGuessesTaken(mDataSingleton.getNoOfGuessesTaken() + 1);
        mTvNoOfGuesses.setText("Total Guesses: " + mDataSingleton.getNoOfGuessesTaken());

        //if the guess image url is same as this make it visible
        if(imageUrl.equals((String)mGuessImage.getTag())) {
            for (Image image : imageList) {
                if (image.getImageUrl().equals(imageUrl)) {
                    image.setVisible(true);
                    break;
                }
            }
            mAdapter.notifyDataSetChanged();
            showImageToUser();
        } else {

            final Toast toast = Toast.makeText(this, "Wrong. Try Again!", Toast.LENGTH_SHORT);
            toast.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 500);


        }

    }

    @Override
    public void onAllGridImagesLoaded() {
        fragment.dismiss();
        /**do this only when all the images are loaded**/
        scheduleGameTimer();


    }

    public class CustomCountDownTimer extends CountDownTimer {

        public CustomCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            mTvGameFinishTimer.setText("Time: " + (millisUntilFinished / 1000) );
        }

        @Override
        public void onFinish() {
            //you lost
            Toast.makeText(mActivity, "You lost", Toast.LENGTH_SHORT).show();
        }


    }
}
