package com.example.lookclassy.More.ChangePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lookclassy.R;
import com.example.lookclassy.api.ApiClient;
import com.example.lookclassy.api.response.RegisterResponse;
import com.example.lookclassy.utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText newPasswordET, confirmPasswordET;
    TextView changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        newPasswordET = findViewById(R.id.newPasswordET);
        confirmPasswordET = findViewById(R.id.confirmPasswordET);
        changeButton = findViewById(R.id.changeButton);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }

            private void updatePassword() {

//                if (validateAll()) {
                String key = SharedPrefUtils.getString(ChangePasswordActivity.this, getString(R.string.api_key));
                Call<RegisterResponse> changePassword = ApiClient.getClient().forgotpassword(key, newPasswordET.getText().toString());
                changePassword.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            if (!response.body().getError()) {
                                Toast.makeText(ChangePasswordActivity.this, "Password Successfully Changed", Toast.LENGTH_SHORT).show();
//                                    SharedPrefUtils.setString(ChangePWActivity.this, DataHolder.PASSWORD_KEY, newPasswordET.getText().toString());
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}