package com.example.hp.happyfooding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import Common.Common;
import Model.User;
import io.smooch.core.Settings;
import io.smooch.core.Smooch;

public class VeriCode extends AppCompatActivity {
    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button confirm;
    private EditText vcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veri_code);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.probar);
        confirm = findViewById(R.id.confirm);
        vcode = findViewById(R.id.code);

        String phonenumber = getIntent().getStringExtra("phone");

        sendVerificationCode("+88"+phonenumber);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = vcode.getText().toString().trim();
                if(code.isEmpty() || code.length()<6){
                    vcode.setError("Please enter code...");
                    vcode.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });

    }
    private void verifyCode(String code){
           PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
           signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
           mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       String phone = getIntent().getStringExtra("phone");
                       String name = getIntent().getStringExtra("name");
                       String addr = getIntent().getStringExtra("addr");
                       String pass = getIntent().getStringExtra("pass");
                       String conpass = getIntent().getStringExtra("conpass");
                       FirebaseDatabase database = FirebaseDatabase.getInstance();
                       final DatabaseReference table_user = database.getReference("User");
                       User user = new User(name, addr, pass, conpass);
                       table_user.child(phone).setValue(user);
                       Common.currentUser = user;
                       Toast.makeText(VeriCode.this, "Successful registration!"+name, Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(VeriCode.this, UProfile.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                       startActivity(intent);
                   }else
                       Toast.makeText(VeriCode.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
               }
           });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
                );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VeriCode.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };



}
