package com.devcrushers.stegmate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Decoded extends AppCompatActivity {
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoded);

        message = findViewById(R.id.message);

        Intent intent = getIntent();

        String msg = (String) intent.getStringExtra("msg");

        message.setText(msg);

    }
}