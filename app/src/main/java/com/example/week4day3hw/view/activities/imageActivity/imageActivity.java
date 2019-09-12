package com.example.week4day3hw.view.activities.imageActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.week4day3hw.R;

public class imageActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.bgImage);

        Intent passedIntent = getIntent();
        Bundle passedBundle = passedIntent.getExtras();
        String passedAddress = passedBundle.getString("picture");

        Glide.with(this).load(passedAddress).apply(new RequestOptions().override(600, 600))
                .into(imageView);
    }
}
