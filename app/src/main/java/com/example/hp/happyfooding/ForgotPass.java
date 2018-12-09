package com.example.hp.happyfooding;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.User;

public class ForgotPass extends AppCompatActivity {
    EditText email, ph, ps;
    Button conf;
    private String TAG;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference mr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        email = findViewById(R.id.mt);
        conf = findViewById(R.id.em);
        ph = findViewById(R.id.mq);
        ps = findViewById(R.id.mp);
        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        mr = database.getReference("User");

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ps.getText().toString().length() <6){
                    Toast.makeText(ForgotPass.this, "Please enter at least 6 chracters!", Toast.LENGTH_LONG).show();
                }
                else if(!email.getText().toString().isEmpty() && !ph.getText().toString().isEmpty() && !ps.getText().toString().isEmpty()){
                    boolean b = isEmailValid(email.getText().toString());
                    if(b){
                        // Toast.makeText(ForgotPass.this, "Entered", Toast.LENGTH_LONG).show();
                        String emailAddress = email.getText().toString().trim();

                        auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ForgotPass.this, "Please check your email and reset password.", Toast.LENGTH_LONG).show();

                                            mr.child(ph.getText().toString()).child("password").setValue(ps.getText().toString());
                                            mr.child(ph.getText().toString()).child("conpass").setValue(ps.getText().toString());

                                            // Toast.makeText(ForgotPass.this, "Entered", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        // Toast.makeText(ForgotPass.this, "Entered", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(ForgotPass.this, "Please enter a valid email address.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(ForgotPass.this, "Please enter all the fields!", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
