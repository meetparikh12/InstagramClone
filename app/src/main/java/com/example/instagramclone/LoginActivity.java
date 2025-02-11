package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtLogInEmail, edtLogInPassword;
    private Button btnLogIn,btn2SignUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.log_in);
        setContentView(R.layout.activity_login);
        if(ParseUser.getCurrentUser() != null){
            ParseUser.logOut();
        }
        edtLogInEmail = findViewById(R.id.edtLogInEmail);
        edtLogInPassword = findViewById(R.id.edtLogInPassword);
        edtLogInPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnLogIn);
                }
                return false;
            }
        });
        btn2SignUp = findViewById(R.id.btn2SignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(LoginActivity.this);
        btn2SignUp.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogIn:
                String userEmail = edtLogInEmail.getText().toString();
                String userPassword = edtLogInPassword.getText().toString();
                if (userEmail.equals("") || userPassword.equals("")) {
                    FancyToast.makeText(LoginActivity.this,"Please Enter all the details!",Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                }
                else {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Logging in");
                    progressDialog.show();
                    ParseUser.logInInBackground(userEmail, userPassword, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                FancyToast.makeText(LoginActivity.this, user.get("username") + " " +
                                        "logged in successfully", Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                            }
                            progressDialog.dismiss();

                        }
                    });
                }
                break;
            case R.id.btn2SignUp:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
                break;
        }
    }
    public void rootLayoutTapped(View view){
        try{
            InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void transitionToSocialMediaActivity(){
        Intent intent = new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}

