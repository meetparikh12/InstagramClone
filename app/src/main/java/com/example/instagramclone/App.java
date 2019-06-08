package com.example.instagramclone;

import android.app.Application;
import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("8iQVZFMXc68znnHuXGPqYtkutscdds9ORo1SAuSf")
                // if defined
                .clientKey("nFZihGLNyV2ePWFD0gJP12EHTN93aKndZ84P8Lad")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
