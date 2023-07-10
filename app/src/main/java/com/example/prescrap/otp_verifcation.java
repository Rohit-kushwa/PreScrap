package com.example.prescrap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp_verifcation extends AppCompatActivity {
    TextInputEditText c1, c2, c3, c4, c5, c6;
    String checkOTP;
    ProgressBar progressBar;
    Button submitBtn;
    TextView resendOtp, secTime, mobNumber;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifcation);
        c1 = findViewById(R.id.num1);
        c2 = findViewById(R.id.num2);
        c3 = findViewById(R.id.num3);
        c4 = findViewById(R.id.num4);
        c5 = findViewById(R.id.num5);
        c6 = findViewById(R.id.num6);

        submitBtn = findViewById(R.id.Submit_btn);
        resendOtp = findViewById(R.id.reSend_TextView);
        secTime = findViewById(R.id.time_textView);
        mobNumber = findViewById(R.id.numTextView);
        progressBar = findViewById(R.id.progressBar2);

        resendOtp.setVisibility(View.GONE);

        mobNumber.setText(getIntent().getStringExtra("num"));

        checkOTP = getIntent().getStringExtra("otpForcheck");

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                secTime.setText(""+millisUntilFinished/1000);
            }

            public void onFinish() {
                resendOtp.setVisibility(View.VISIBLE);
            }
        }.start();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!c1.getText().toString().isEmpty() && !c2.getText().toString().isEmpty() && !c3.getText().toString().isEmpty() && !c4.getText().toString().isEmpty() && !c5.getText().toString().isEmpty() && !c6.getText().toString().isEmpty()){
                    String otpCode = c1.getText().toString() + c2.getText().toString() + c3.getText().toString() + c4.getText().toString() + c5.getText().toString() + c6.getText().toString() ;
                    if (otpCode != null){
                        progressBar.setVisibility(View.VISIBLE);
                        submitBtn.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(checkOTP, otpCode);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        submitBtn.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(otp_verifcation.this, "Enter the correct Otp", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else {
                        Toast.makeText(otp_verifcation.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(otp_verifcation.this, "Please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numberOTPmove();

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("num"), 60, TimeUnit.SECONDS, otp_verifcation.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(otp_verifcation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newOtp , @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                checkOTP = newOtp;
                                Toast.makeText(otp_verifcation.this, "Otp Send Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void numberOTPmove(){
        c1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    c2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        c2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    c3.requestFocus();
                }
                if (s.toString().trim().isEmpty()){
                    c1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        c3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    c4.requestFocus();
                }
                if (s.toString().trim().isEmpty()){
                    c2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        c4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    c5.requestFocus();
                }
                if (s.toString().trim().isEmpty()){
                    c3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        c5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    c6.requestFocus();
                }
                if (s.toString().trim().isEmpty()){
                    c4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        c6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    submitBtn.requestFocus();
                }
                if (s.toString().trim().isEmpty()){
                    c5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}