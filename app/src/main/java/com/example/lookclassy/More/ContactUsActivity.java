package com.example.lookclassy.More;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lookclassy.R;

public class ContactUsActivity extends AppCompatActivity {
    ImageView contactUsback;
    private static final int REQUEST_CALL = 1;
    private TextView phnnumber;
    private TextView emailTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        contactUsback= findViewById(R.id.contactUsback);
        phnnumber = findViewById(R.id.phnnumber);
        phnnumber.setPaintFlags(phnnumber.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        emailTV = findViewById(R.id.emailTV);
        emailOnClickListener();
        contactbackOnClick();
        setOnClickListeners();

    }

    private void emailOnClickListener() {
        emailTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { sendEmail();}
        });
    }

    private void sendEmail() {
        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:lookclassy@gmail.com")));
    }

    private void contactbackOnClick() {contactUsback.setOnClickListener(v -> finish());
    }

    private void setOnClickListeners() {
        phnnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }

    private void makePhoneCall() {

        String num = phnnumber.getText().toString();
        if (ContextCompat.checkSelfPermission(ContactUsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ContactUsActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + num;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }


    }
}