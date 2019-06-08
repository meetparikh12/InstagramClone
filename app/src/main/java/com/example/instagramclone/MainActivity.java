package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class MainActivity extends AppCompatActivity {
private TextView getData;
String displayName;
private Button btnNextActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData = findViewById(R.id.getData);
        btnNextActivity = findViewById(R.id.btnNextActivity);
        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpLoginActivity.class));
            }
        });
//        getData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayName="";
//                ParseQuery<ParseObject> query = ParseQuery.getQuery("Fighter");
//                query.findInBackground(new FindCallback<ParseObject>() {
//                    @Override
//                    public void done(List<ParseObject> objects, ParseException e) {
//                        if(e == null && objects.size() > 0){
//                            for(ParseObject getData : objects){
//                                 displayName = displayName +getData.get("name") +" " +getData.get("punchSpeed") +"\n";
//                            }
//                            Toast.makeText(MainActivity.this, displayName, Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(MainActivity.this, e.getCode(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
    }
}
