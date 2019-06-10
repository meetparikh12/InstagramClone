package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPosts extends AppCompatActivity {
private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);
        linearLayout = findViewById(R.id.linearLayout);
        Intent receivedIntentObject = getIntent();
        final String receivedUserName = receivedIntentObject.getStringExtra("username");
        setTitle(receivedUserName +"'s posts");
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUserName);
        parseQuery.orderByDescending("createdAt");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() > 0 && e==null){
                    for(ParseObject usersPosts: objects){
                         final TextView img_description = new TextView(UsersPosts.this);
                            img_description.setText(usersPosts.get("image_des")+"");

                        ParseFile postImage = (ParseFile) usersPosts.get("picture");
                        postImage.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data != null && e==null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView image_post = new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageViewParams.setMargins(5,5,5,5);
                                    image_post.setLayoutParams(imageViewParams);
                                    image_post.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    image_post.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams textViewParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    textViewParam.setMargins(5,5,5,5);
                                    img_description.setLayoutParams(textViewParam);
                                    img_description.setGravity(Gravity.CENTER);
                                    img_description.setBackgroundColor(Color.BLUE);
                                    img_description.setTextColor(Color.WHITE);
                                    img_description.setTextSize(30f);

                                    linearLayout.addView(image_post);
                                    linearLayout.addView(img_description);
                                }
                            }
                        });
                    }
                }else{
                    FancyToast.makeText(UsersPosts.this, "No posts by " +receivedUserName, Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                    finish();
                }
                progressDialog.dismiss();
            }
        });

    }
}
