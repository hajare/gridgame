package com.example.akshay.gamegrid.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.example.akshay.gamegrid.MyApp;
import com.example.akshay.gamegrid.R;
import com.example.akshay.gamegrid.data.Image;
import com.example.akshay.gamegrid.interfaces.IGridImageClickedListener;
import com.example.akshay.gamegrid.interfaces.IGridImagesLoadListener;
import com.example.akshay.gamegrid.utils.DataSingleton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by akshay on 28/7/16.
 */
public class ImageGridAdapter extends BaseAdapter implements View.OnClickListener {

    @Inject
    DataSingleton singleton;

    private IGridImageClickedListener gridItemClickListener;
    private IGridImagesLoadListener imagesLoadedListener;
    private List<Image> imageList = new ArrayList<>();

    private Boolean[] imagesDownloaded = {false, false, false,
            false, false, false, false, false, false};
    private final String TAG = ImageGridAdapter.class.getName();

    Context context;
    public ImageGridAdapter(Context context, List<Image> images) {
        this.imageList = images;
        this.context = context;
        gridItemClickListener = (IGridImageClickedListener)context;
        imagesLoadedListener = (IGridImagesLoadListener) context;

        ((MyApp)context.getApplicationContext()).getAppComponent().inject(this);

    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ivImage);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.imageView.setOnClickListener(this);
        Log.d("ImageGridAdapter", "imageUrl: " + imageList.get(position).getImageUrl());

        //load the image from flickr
        if (imageList.get(position).isVisible()) {

            Picasso.with(context)
                    .load(imageList.get(position).getImageUrl())
                    .centerCrop()
                    .resize(100, 100)
                    .into(viewHolder.imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {


                            viewHolder.imageView.setTag(imageList.get(position).getImageUrl());
                            Log.d("ImageGridAdapter", "image load success");
                            if (!singleton.hasGuessingStarted()) {
                                boolean allImagesDownloaded = true;
                                imagesDownloaded[position] = true;
                                for (int i = 0; i < 9; i++) {
                                    if (!imagesDownloaded[i]) {
                                        allImagesDownloaded = false;
                                        break;
                                    }
                                }
                                if (allImagesDownloaded) {
                                    imagesLoadedListener.onAllGridImagesLoaded();
                                }
                            }

                        }

                        @Override
                        public void onError() {
                            //do something to abort the game
                            Log.d("ImageGridAdapter", "image load failure");
                        }
                    });


        } else {
            viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {

        if(singleton.hasGuessingStarted()) {
            String url = (String)v.getTag();
            gridItemClickListener.onGridImageClicked(url);
        }
    }

    public class ViewHolder {
        private ImageView imageView;
    }
}
