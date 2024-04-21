package com.devcrushers.stegmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

public class Encoded extends AppCompatActivity {

    ShapeableImageView encoded_image;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoded);

        encoded_image=findViewById(R.id.displayEncodedImage);

        intent= getIntent();

        Uri uri =(Uri) intent.getParcelableExtra("image");

        encoded_image.setImageURI(uri);


    }
}