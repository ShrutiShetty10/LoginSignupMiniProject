package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ForgotPasswordSuccessMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_success_message);
    }

    public void backToLogin(View view) {

        Intent intent = new Intent(ForgotPasswordSuccessMessage.this, Login.class);
        startActivity(intent);

    }

}