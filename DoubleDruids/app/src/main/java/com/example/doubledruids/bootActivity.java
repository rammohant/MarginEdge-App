package com.example.doubledruids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doubledruids.ui.login.LoginActivity;

public class bootActivity extends AppCompatActivity {

    private Button logInScreenButton;
    private Button signUpScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        logInScreenButton = (Button) findViewById(R.id.loginScreenbutton);
        signUpScreenButton = (Button) findViewById(R.id.signUpScreenButton);

        logInScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginScreen();
            }
        });
        signUpScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpScreen();
            }
        });

    }

    public void openLoginScreen() {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    public void openSignUpScreen() {
        Intent intent = new Intent(this, signUpActivity.class);
        startActivity(intent);
    }
}