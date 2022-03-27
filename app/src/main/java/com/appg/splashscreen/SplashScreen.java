package com.appg.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.appg.BottomNavigation.Home.FruitNinja.SelectGame.SelectGame;
import com.appg.Firebase.Login.Login;
import com.appg.MainActivity;
import com.appg.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ActivityCompat.requestPermissions(SplashScreen.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS},
                1);




        getSupportActionBar().hide();
        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(SplashScreen.this, Login.class));
                    finish();
                }
                else {

                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            startActivity(new Intent(SplashScreen.this, SplashScreen.class));
                            finish();
                        }
                    }, secondsDelayed * 1000);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS,Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    }



                }
            }
        }, secondsDelayed * 1000);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(SplashScreen.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}