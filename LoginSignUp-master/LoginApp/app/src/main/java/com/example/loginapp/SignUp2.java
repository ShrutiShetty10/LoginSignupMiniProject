package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class SignUp2 extends AppCompatActivity {

    EditText phoneNumber;
    CountryCodePicker countryCodePicker;
    String fullName, email, password, phoneNumberS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        Intent intent = getIntent();

        fullName = intent.getStringExtra("fullName");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        phoneNumber = findViewById(R.id.signup_phone_number);
        countryCodePicker = findViewById(R.id.country_code_picker);

    }

    public void callVerifyOTPScreen(View view) {

        if (!validatePhoneNumber())
            return;

        phoneNumberS = phoneNumber.getText().toString().trim();
        phoneNumberS = "+" + countryCodePicker.getFullNumber() + phoneNumberS;

        Intent intent = new Intent(SignUp2.this, VerifyOTP.class);
        intent.putExtra("fullName", fullName);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("phoneNumber", phoneNumberS);
        intent.putExtra("whereToGo", "toSignUpSuccessfull");
        startActivity(intent);

    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getText().toString().trim();

        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        }
        if (val.contains(" ")) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        }
        if (!(val.charAt(0) == '7' || val.charAt(0) == '8' || val.charAt(0) == '9')) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        }
        phoneNumber.setError(null);
        return true;
    }

}