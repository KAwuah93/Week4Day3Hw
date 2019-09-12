package com.example.week4day3hw.model.datasource.remote.okhttp;

import android.util.Log;

import com.example.week4day3hw.model.FlickerObj.FlickerObj;
import com.example.week4day3hw.model.event.OkHttpFlickrEvent;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpHelper {
    //Set the URL for the calls
    public static final String FLICKR_IMG_URL = "https://api.flickr.com/services/feeds/photos_public.gne?tag=fish&format=json&nojsoncallback=1";
    public static OkHttpClient getClient() {
        //used to log the information seperate from the actual function
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        //
        return new OkHttpClient.Builder()
                //add this to the build statement to get information back from the request as it streams
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public static void makeAsyncOkHttpCall() {
        //Build the URL for the call, using what we created up top
        Request request = new Request
                .Builder()
                .url(FLICKR_IMG_URL)
                .build();
        //Enqueue the call/request
        getClient().newCall(request).enqueue(new Callback(){
            //Set what happens on failure
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("TAG", "onFailure: ", e);
            }
            //Define what happens when you succeed (parsing the Json)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String jsonResponse = response.body().string();
                //using the Gson to transcribe the json to the item
                final FlickerObj randomMeResponse = new Gson().fromJson(jsonResponse, FlickerObj.class);
                final OkHttpFlickrEvent okHttpFlickrEvent = new OkHttpFlickrEvent (randomMeResponse);
                EventBus.getDefault().post(okHttpFlickrEvent);
            }
        });
    }

    public static FlickerObj getSyncroniousOkHttpResponse() throws IOException {
        Request request = new Request.Builder().url(FLICKR_IMG_URL).build();

        Response response = getClient().newCall(request).execute();
        final FlickerObj randomMeResponse =
                new Gson().fromJson(response.body().string(), FlickerObj.class);
        return randomMeResponse;
    }
}
