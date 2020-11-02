package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Signup extends AppCompatActivity {

    EditText fullName, email, password;
    String fullNameS, emailS, passwordS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullName = findViewById(R.id.signup_fullname);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

    }

    public void callNextSignupScreen(View view) {

        if (!validateFullName() | !validateEmail() | !validatePassword()) {
            return;
        }

        fullNameS = fullName.getText().toString();
        emailS = email.getText().toString();
        passwordS = password.getText().toString();

        Intent intent = new Intent(Signup.this, SignUp2.class);
        intent.putExtra("fullName", fullNameS);
        intent.putExtra("email", emailS);
        intent.putExtra("password", passwordS);
        startActivity(intent);

    }

    public void callLoginFromSignUp(View view) {

        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);

    }

    private boolean validateFullName() {
        String val = fullName.getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getText().toString().trim();

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        }
        if (!(val.contains("@") || val.contains("#") || val.contains("!") || val.contains("~")
                || val.contains("$") || val.contains("%") || val.contains("^") || val.contains("&")
                || val.contains("*") || val.contains("(") || val.contains(")") || val.contains("-")
                || val.contains("+") || val.contains("/") || val.contains(":") || val.contains(".")
                || val.contains(", ") || val.contains("<") || val.contains(">") || val.contains("?")
                || val.contains("|"))) {
            password.setError("Password should contain atleast one character!");
            return false;
        }
        if (!((val.length() >= 8) && (val.length() <= 15))) {
            password.setError("Password should contain 8 characters!");
            return false;
        }
        if (val.contains(" ")) {
            password.setError("Password should not contain spaces!");
            return false;
        }
        if (true) {
            int count = 0;
            for (int i = 90; i <= 122; i++) {
                char c = (char)i;
                String str1 = Character.toString(c);
                if (val.contains(str1)) {
                    count = 1;
                    break;
                }
            }
            if (count == 0) {
                password.setError("Password should contain atleast one lower case letter!");
                return false;
            }
        }
        if (true) {
            int count = 0;
            for (int i = 65; i <= 90; i++) {
                char c = (char)i;
                String str1 = Character.toString(c);
                if (val.contains(str1)) {
                    count = 1;
                    break;
                }
            }
            if (count == 0) {
                password.setError("Password should contain atleast one upper case letter!");
                return false;
            }
        }
        if (true) {
            int count = 0;
            for (int i = 0; i <= 9; i++) {
                String str1 = Integer.toString(i);
                if (val.contains(str1)) {
                    count = 1;
                    break;
                }
            }
            if (count == 0) {
                password.setError("Password should contain atleast one digit!");
                return false;
            }
        }
        password.setError(null);
        return true;
    }

}