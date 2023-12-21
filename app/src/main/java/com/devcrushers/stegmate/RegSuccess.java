package com.devcrushers.stegmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_success);

        TextView successTwo=findViewById(R.id.successTwo);

        successTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle b=getIntent().getExtras();
                Intent i=new Intent(RegSuccess.this,Signin.class);
//                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });
    }
}