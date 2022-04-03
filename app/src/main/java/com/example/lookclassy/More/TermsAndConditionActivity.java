package com.example.lookclassy.More;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.lookclassy.R;

public class TermsAndConditionActivity extends AppCompatActivity {
    ImageView termsAndConditionbackIV;

   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        termsAndConditionbackIV= findViewById(R.id.termsAndConditionbackIV);
        setOnClickListeners();

    }

    private void setOnClickListeners() {termsAndConditionbackIV.setOnClickListener(v -> finish());
    }
}