package com.example.hp.happyfooding;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.service.autofill.RegexValidator;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;


import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import Model.User;
import io.michaelrocks.libphonenumber.android.internal.RegexBasedMatcher;

import static android.util.Patterns.PHONE;

public class Signup extends AppCompatActivity {

    EditText phone, name, addr, pass, conpass;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        addr = findViewById(R.id.addr);
        pass = findViewById(R.id.password);
        conpass = findViewById(R.id.conpassword);

        signup = findViewById(R.id.signup);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(Signup.this);
                mDialog.setMessage("Please Wait...");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String p = phone.getText().toString();
                        int len = p.length();
                        if (dataSnapshot.child(phone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(Signup.this, "Phone Number already registered!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (phone.getText().toString().isEmpty() || name.getText().toString().isEmpty() || addr.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || conpass.getText().toString().isEmpty()) {
                            mDialog.dismiss();
                            Toast.makeText(Signup.this, "Please fill up all the fields!", Toast.LENGTH_SHORT).show();
                            }
                            else if(len!=11 || (p.charAt(0)!='0') || (p.charAt(1)!='1') || (p.charAt(2)=='0') || (p.charAt(2)=='2')){
                                mDialog.dismiss();
                                Toast.makeText(Signup.this, "Please enter a valid phone number!", Toast.LENGTH_SHORT).show();
                            }
                            else if (!pass.getText().toString().equals(conpass.getText().toString())) {
                            mDialog.dismiss();
                            Toast.makeText(Signup.this, "Passwords didn't match!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                            mDialog.dismiss();
                                User user = new User(name.getText().toString(), addr.getText().toString(), pass.getText().toString(), conpass.getText().toString());
                                table_user.child(phone.getText().toString()).setValue(user);
                                Toast.makeText(Signup.this, "Successful registration!", Toast.LENGTH_SHORT).show();

                            }
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }



}
