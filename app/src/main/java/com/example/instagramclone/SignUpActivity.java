package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtSignUpUsername, edtSignUpEmail, edtSignUpPassword;
    private Button btnSignUp, btnLogIn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.sign_up);
        setContentView(R.layout.activity_sign_up);
        if(ParseUser.getCurrentUser() != null){
          //  ParseUser.logOut();
            transitionToSocialMediaActivity();
        }
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        edtSignUpPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp.setOnClickListener(SignUpActivity.this);
        btnLogIn.setOnClickListener(SignUpActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                String userName = edtSignUpUsername.getText().toString();
                String userEmail = edtSignUpEmail.getText().toString();
                String userPassword = edtSignUpPassword.getText().toString();
                if (userName.equals("") || userEmail.equals("") || userPassword.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please Enter all the fields!", Toast.LENGTH_SHORT).show();
                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setUsername(userName);
                    appUser.setEmail(userEmail);
                    appUser.setPassword(userPassword);
                    progressDialog = new ProgressDialog(SignUpActivity.this);
                    progressDialog.setMessage("Signing up");
                    progressDialog.show();
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SignUpActivity.this, appUser.get("username")
                                        + " signed in successfully", Toast.LENGTH_SHORT).show();
                                transitionToSocialMediaActivity();
                            } else {
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.btnLogIn:
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                break;
        }
    }
    public void rootLayoutTapped(View view){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void transitionToSocialMediaActivity(){
        startActivity(new Intent(SignUpActivity.this,SocialMediaActivity.class));
    }

}
