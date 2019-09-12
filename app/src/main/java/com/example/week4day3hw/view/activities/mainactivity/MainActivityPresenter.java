package com.example.week4day3hw.view.activities.mainactivity;

import android.os.AsyncTask;
import android.util.Log;

import com.example.week4day3hw.model.FlickerObj.FlickerObj;
import com.example.week4day3hw.model.datasource.remote.okhttp.OkHttpHelper;
import com.example.week4day3hw.model.datasource.remote.okhttp.OkHttpHelperTask;

import java.net.HttpURLConnection;

import okhttp3.OkHttpClient;

public class MainActivityPresenter {
    private MainActivityContract contract;
    OkHttpHelper okHttpHelper;
    //this is where we tell it to get the damn data

    public MainActivityPresenter(MainActivityContract contract) { this.contract = contract; }

    public void getFlickobj(){
        //nah fam i'm just stup
        new MyAsync().execute("");


        //passing to the contract here
        //Call on main thread exception

    }
    private class MyAsync extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... urls) {
            try{
                FlickerObj flickerObj = new FlickerObj();
                Log.d("FLICKR", "getFlickobj: " + OkHttpHelper.getSyncroniousOkHttpResponse());
                flickerObj = OkHttpHelper.getSyncroniousOkHttpResponse();
                contract.onFlickrReponse(flickerObj);
            }
            catch (Exception e){
                Log.e("ASYNC", "getFlickobj: ", e);
            }

            return "executed";
        }

        protected void onPostExecute(FlickerObj flickerObj) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

}
