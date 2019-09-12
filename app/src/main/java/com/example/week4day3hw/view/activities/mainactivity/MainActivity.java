package com.example.week4day3hw.view.activities.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.week4day3hw.view.adapters.ItemsRVAdapter;
import com.example.week4day3hw.R;
import com.example.week4day3hw.model.FlickerObj.FlickerObj;
import com.example.week4day3hw.model.FlickerObj.ItemsItem;
import com.example.week4day3hw.model.event.OkHttpFlickrEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityContract {

    public static final String TAG = "TAG_ACT_MAIN";
    RecyclerView recyclerView;
    ItemsRVAdapter adapter;
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Starting the threading for ok
//        OkHttpHelper.makeAsyncOkHttpCall();
//        OkHttpHelperTask okHttpHelperTask = new OkHttpHelperTask();
//        okHttpHelperTask.execute();

        recyclerView = findViewById(R.id.rvFlickr);

        //I hate everything
        //adapter = findViewById(R.id.rvRandomUserList);
        adapter = new ItemsRVAdapter(new ArrayList<ItemsItem>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        presenter = new MainActivityPresenter(this);

        presenter.getFlickobj();
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

    @Override
    public void onFlickrReponse(final FlickerObj flickerObj) {
        if(flickerObj != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.addToList(flickerObj);
                }
            });

        }
    }
}
