package com.example.myapps.meditrack;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapps.meditrack.Helper.AppPrefManager;

public class Splash extends FragmentActivity {
    AppPrefManager prefManager;
    private String TAG="MediTrack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefManager=new AppPrefManager(this);
        new Thread() {

            @Override
            public void run() {

                try {
                    sleep(2 * 1000);
                    Log.i(TAG,
                            "wait done and transferring to Login activity wherer first launch is true");
                    if(prefManager.isLoggedIn()){
                        if(prefManager.getSOSMobileNumber()!=null) {
                            Intent sendIntent = new Intent(Splash.this, HomeScreen.class);
                            startActivity(sendIntent);
                            finish();
                        }else{
                            Intent sendIntent = new Intent(Splash.this, SOSEntryScreen.class);
                            startActivity(sendIntent);
                            finish();
                        }
                    }else{
                        Intent sendIntent=new Intent(Splash.this,PersonInfo.class);
                        startActivity(sendIntent);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
