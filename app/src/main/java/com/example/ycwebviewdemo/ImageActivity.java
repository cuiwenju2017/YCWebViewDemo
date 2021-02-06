package com.example.ycwebviewdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ycwebviewdemo.utils.ModelStorage;

public class ImageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView imageView = findViewById(R.id.iv_image);
        Bitmap bitmap = ModelStorage.getInstance().getBitmap();
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ModelStorage.getInstance().setBitmap(null);
    }
}
