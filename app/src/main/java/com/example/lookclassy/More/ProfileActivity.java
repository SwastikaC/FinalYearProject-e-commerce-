package com.example.lookclassy.More;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.lookclassy.R;

public class ProfileActivity extends AppCompatActivity {

    ImageView profilebackIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilebackIV= findViewById(R.id.profilebackIV);
        setOnClickListeners();

    }

    private void setOnClickListeners() {
        profilebackIV.setOnClickListener(v -> finish());
    }
}