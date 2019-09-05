package com.example.week4day3hw.model.event;

import com.example.week4day3hw.model.FlickerObj.FlickerObj;

public class OkHttpFlickrEvent {

    FlickerObj flickerObj;

    public OkHttpFlickrEvent(FlickerObj flickerObj) {
        this.flickerObj = flickerObj;
    }

    public FlickerObj getFlickrResponse() {
        return flickerObj;
    }

    public void setFlickrResponse(FlickerObj flickerObj) {
        this.flickerObj = flickerObj;
    }
}
