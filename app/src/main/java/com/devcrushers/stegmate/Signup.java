package com.devcrushers.stegmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText conPass = findViewById(R.id.conPass);
        EditText pass = findViewById(R.id.pass);
        EditText email = findViewById(R.id.email);
        EditText phone = findViewById(R.id.phone);
        EditText username = findViewById(R.id.username);
        EditText name = findViewById(R.id.name);

        DBHelper dbhelp= new DBHelper(this);

        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass.getText().toString().equals(conPass.getText().toString())) {
                    Toast.makeText(getBaseContext(), "Successful Sign UP", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Signup.this, RegSuccess.class);

                    dbhelp.addStudent(name.getText().toString(),username.getText().toString(),email.getText().toString(),phone.getText().toString(),pass.getText().toString());

                    startActivity(i);
                    finish();
                }
            }
        });

    }

    public boolean validatePassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}