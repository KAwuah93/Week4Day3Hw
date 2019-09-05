package com.example.week4day3hw.model.datasource.remote.okhttp;

import android.os.AsyncTask;
import android.util.Log;

import com.example.week4day3hw.model.FlickerObj.FlickerObj;
import com.example.week4day3hw.model.event.OkHttpFlickrEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class OkHttpHelperTask extends AsyncTask<Void,String, FlickerObj> {

    public static final String TAG = "TAG_ASYNC_TASK";

    //Define what we will be doing in the background of the execution
    @Override
    protected FlickerObj doInBackground(Void... voids) {
        try {
            return OkHttpHelper.getSyncroniousOkHttpResponse();
        } catch (IOException e) {
            publishProgress(e.toString());
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d(TAG, "onProgressUpdate: " + values[0]);
    }

    @Override
    protected void onPostExecute(FlickerObj randomMeResponse) {
        super.onPostExecute(randomMeResponse);
        EventBus.getDefault().post(new OkHttpFlickrEvent(randomMeResponse));
    }
}
