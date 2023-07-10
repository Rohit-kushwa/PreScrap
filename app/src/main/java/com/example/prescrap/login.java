package com.example.prescrap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {

    TextInputEditText number;
    ProgressBar progressBar;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        number = findViewById(R.id.numberET);
        nextButton = findViewById(R.id.nextBtn);
        progressBar = findViewById(R.id.progressBar);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.getText().toString().trim().isEmpty()){
                    if ((number.getText().toString().trim()).length() == 10){

                        progressBar.setVisibility(View.VISIBLE);
                        nextButton.setVisibility(View.INVISIBLE);
                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + number.getText().toString(), 60, TimeUnit.SECONDS, login.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        nextButton.setVisibility(View.VISIBLE);
                                    }
                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        nextButton.setVisibility(View.VISIBLE);
                                        Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        nextButton.setVisibility(View.VISIBLE);

                                        Intent intent = new Intent(getApplicationContext(), otp_verifcation.class);
                                        intent.putExtra("num", number.getText().toString());
                                        intent.putExtra("otpForcheck", s);
                                        startActivity(intent);
                                    }
                                }


                        );
                    }
                    else {
                        Toast.makeText(login.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(login.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}