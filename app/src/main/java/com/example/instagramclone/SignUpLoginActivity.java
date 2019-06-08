package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpLoginActivity extends AppCompatActivity {
    private EditText edtSignUpUsername, edtSignUpEmail, edtSignUpPassword, edtLogInUsername, edtLogInPassword;
    private Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        edtLogInUsername = findViewById(R.id.edtLogInUserName);
        edtLogInPassword = findViewById(R.id.edtLogInPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtSignUpUsername.getText().toString());
                appUser.setEmail(edtSignUpEmail.getText().toString());
                appUser.setPassword(edtSignUpPassword.getText().toString());
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(SignUpLoginActivity.this, appUser.get("username")
                                    + " signed in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpLoginActivity.this,WelcomeActivity.class));
                        } else {
                            Toast.makeText(SignUpLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(edtLogInUsername.getText().toString(), edtLogInPassword.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null) {
                                    Toast.makeText(SignUpLoginActivity.this, user.get("username") + " " +
                                            "logged in successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpLoginActivity.this,WelcomeActivity.class));
                                } else {
                                    Toast.makeText(SignUpLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

