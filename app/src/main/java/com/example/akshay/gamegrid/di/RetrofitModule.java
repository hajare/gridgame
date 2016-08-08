package com.example.akshay.gamegrid.di;

import com.example.akshay.gamegrid.rest.ErrorHandlingExecutorCallAdapterFactory;
import com.example.akshay.gamegrid.rest.RetrofitClient;

import com.example.akshay.gamegrid.utils.AppConstants;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by akshay on 28/7/16.
 */
@Module
public class RetrofitModule {


    private final String TAG = RetrofitModule.class.getName();

    public RetrofitModule() {

    }

    @Singleton
    @Provides
    RetrofitClient provideRetrofitClient(Retrofit retrofit) {
        RetrofitClient retrofitClient;
        retrofitClient = retrofit.create(RetrofitClient.class);
        return retrofitClient;
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(
                        new ErrorHandlingExecutorCallAdapterFactory(
                                new ErrorHandlingExecutorCallAdapterFactory.MainThreadExecutor()))
                .baseUrl(AppConstants.BASE_URL)
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(15, TimeUnit.SECONDS);
        client.setWriteTimeout(15, TimeUnit.SECONDS);
        client.setConnectTimeout(15, TimeUnit.SECONDS);

        return client;
    }

}
