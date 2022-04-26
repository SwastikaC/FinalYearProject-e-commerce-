package com.example.lookclassy.More;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lookclassy.More.ChangePassword.ChangePasswordActivity;
import com.example.lookclassy.R;
import com.example.lookclassy.api.ApiClient;
import com.example.lookclassy.api.response.RegisterResponse;
import com.example.lookclassy.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    ImageView profilebackIV;
    EditText fullnameET, emailET, bodET, phnnumberET;
    LinearLayout updateLL, changePasswordLL;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilebackIV = findViewById(R.id.profilebackIV);
        fullnameET = findViewById(R.id.fullnameET);
        emailET = findViewById(R.id.emailET);
        bodET = findViewById(R.id.bodET);
        phnnumberET = findViewById(R.id.phnnumberET);
        changePasswordLL = findViewById(R.id.changePasswordLL);
        updateLL = findViewById(R.id.updateLL);

        fullnameET.setText(SharedPrefUtils.getString(this, getString(R.string.name_key)));
        emailET.setText(SharedPrefUtils.getString(this, getString(R.string.email_id)));
        bodET.setText(SharedPrefUtils.getString(this, getString(R.string.dateofbirth)));
        phnnumberET.setText(SharedPrefUtils.getString(this, getString(R.string.phonenumber)));
//        setOnClickListeners();
//
//    }
//
//    private void setOnClickListeners() {
//        profilebackIV.setOnClickListener(v -> finish());
//    }
        profilebackIV.setOnClickListener(v -> finish());

        updateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    callResponse(fullnameET.getText().toString(), emailET.getText().toString(), bodET.getText().toString(), phnnumberET.getText().toString());
                    fullnameET.setText("");
                    emailET.setText("");
                    bodET.setText("");
                    phnnumberET.setText("");

                }
            }

            private void callResponse(String names, String email, String dateofbirth, String phonenumber) {
                String key = SharedPrefUtils.getString(ProfileActivity.this, getString(R.string.api_key));
                Call<RegisterResponse> registerResponseCall = ApiClient.getClient().updateProfile(key, names, email, dateofbirth, phonenumber);

                registerResponseCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            if (!response.body().getError()) {
                                Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
            }


            private boolean validate() {
                if (phnnumberET.getText().toString().length() < 10) {
                    Toast.makeText(getApplicationContext(), "Contact number cannot be less than 10 letters", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    Toast.makeText(getApplicationContext(), "You have successfully updated your profile", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });

        setOnclickListeners();
    }

    public void message(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setOnclickListeners() {

        profilebackIV.setOnClickListener(v -> finish());

        changePasswordLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}