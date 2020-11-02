package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText phoneNumber, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);

    }

    public void callSignUpFromLogin(View view) {

        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);

    }

    public void letTheUserLoggedIn(View view) {

        CheckInternet checkInternet = new CheckInternet();
        if(!checkInternet.isConnected(this)) {
            showCustomDialog();
            return;
        }

        if(!validatePhoneNumber() | !validatePassword())
            return;

        String _phoneNumber = phoneNumber.getText().toString().trim();
        final String _password = password.getText().toString().trim();

        if(_phoneNumber.charAt(0) == '0')
            _phoneNumber = _phoneNumber.substring(1);

        final String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    phoneNumber.setError(null);

                    String systemPassword = dataSnapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                    if (systemPassword.equals(_password)) {
                        password.setError(null);

                        String _fullName = dataSnapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _email = dataSnapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _phoneNo = dataSnapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);

                        Toast.makeText(Login.this, _fullName + "\n" + _email + "\n" + _phoneNo, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Login.this,"Password does not match", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Login.this,"No such user exists!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(Login.this, SuccessfullLogin.class);
        startActivity(intent);

    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet to proceed further").setCancelable(false).setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

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

    public void callForgetPassword(View view) {

        Intent intent = new Intent(Login.this, ForgotPassword.class);
        startActivity(intent);

    }

}
