package com.devcrushers.stegmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        DBHelper dbHelp=new DBHelper(this);


        EditText USN=findViewById(R.id.USN),pass=findViewById(R.id.pass);
        Button btnSignIn=findViewById(R.id.btnSignIn);
        TextView flashTwo=findViewById(R.id.flashTwo);


        flashTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Signin.this,Signup.class);
                startActivity(i);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                ArrayList<StudentModel> arrStudent = dbHelp.fetchStudent();

                int iter;

                SharedPreferences preferences=getSharedPreferences("Login",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();

                SharedPreferences usn=getSharedPreferences("USN",MODE_PRIVATE);
                SharedPreferences.Editor edit=usn.edit();

                for(iter=0;iter<arrStudent.size();iter++){
                    if(arrStudent.get(iter).usn.equals(USN.getText().toString()) && arrStudent.get(iter).pass.equals(pass.getText().toString())){
                        Intent i=new Intent(Signin.this,MainActivity.class);
                        String myUsn= USN.getText().toString();

                        edit.putString("USNID",myUsn);
                        edit.apply();

                        editor.putBoolean("flag",true);
                        editor.apply();

                        startActivity(i);
                        finish();
                    }
                }
                if(iter>arrStudent.size()){
                    Toast.makeText(getBaseContext(), "Login Unsuccessful",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}