package com.example.week4day3hw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.week4day3hw.model.FlickerObj.ItemsItem;
import com.example.week4day3hw.model.datasource.remote.okhttp.OkHttpHelper;
import com.example.week4day3hw.model.datasource.remote.okhttp.OkHttpHelperTask;
import com.example.week4day3hw.model.event.OkHttpFlickrEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG_ACT_MAIN";
    RecyclerView recyclerView;
    //TODO create the object to translate this over-
    ItemsRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Starting the threading for ok
        OkHttpHelper.makeAsyncOkHttpCall();
        OkHttpHelperTask okHttpHelperTask = new OkHttpHelperTask();
        okHttpHelperTask.execute();

        recyclerView = findViewById(R.id.rvFlickr);

        //I hate everything
        //adapter = findViewById(R.id.rvRandomUserList);
        adapter = new ItemsRVAdapter(new ArrayList<ItemsItem>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOkHttpRandomUserEvent(OkHttpFlickrEvent event) {
        adapter.addToList(event.getFlickrResponse());
    }

}
