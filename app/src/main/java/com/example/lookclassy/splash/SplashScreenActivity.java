package com.example.lookclassy.splash;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.lookclassy.R;
import com.example.lookclassy.home.MainActivity;
import com.example.lookclassy.userAccount.UserAccountActivity;
import com.example.lookclassy.utils.SharedPrefUtils;

public class SplashScreenActivity extends AppCompatActivity {
    boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getIsLoggedInOrNot();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
 //               isLogged = SharedPrefUtils.getBool(SplashScreenActivity.this, getString(R.string.isLogged), false);
 //               System.out.println(isLogged);
                if (isLoggedIn)
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                else
                    startActivity(new Intent(SplashScreenActivity.this, UserAccountActivity.class));
                finish();
            }
        }, 1000);
    }

    public void getIsLoggedInOrNot() {
        isLoggedIn = SharedPrefUtils.getBool(this, getString(R.string.isLogged), false);
    }

}