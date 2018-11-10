package com.example.hp.happyfooding;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.concurrent.TimeUnit;

import Common.Common;
import Model.User;

public class Signup extends AppCompatActivity {

    private final String TAG = "VeriCode";
    private String number;
    public EditText phone, name, addr, pass, conpass;
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
            public void onClick(final View v) {
                final ProgressDialog mDialog = new ProgressDialog(Signup.this);
                mDialog.setMessage("Please Wait...");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String p = phone.getText().toString();
                        int len = p.length();
                        if (dataSnapshot.child(phone.getText().toString()).exists() && !phone.getText().toString().isEmpty()) {
                            mDialog.dismiss();
                            phone.setError("Phone number already registered!");
                            phone.requestFocus();
                        }
                       else if(phone.getText().toString().isEmpty() || name.getText().toString().isEmpty() || addr.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || conpass.getText().toString().isEmpty()){
                            if (phone.getText().toString().isEmpty()) {
                                mDialog.dismiss();
                                phone.setError("Required field!");
                                phone.requestFocus();
                            }
                            if(name.getText().toString().isEmpty()){
                                mDialog.dismiss();
                                name.setError("Required field!");
                                name.requestFocus();
                            }
                            if(addr.getText().toString().isEmpty()){
                                mDialog.dismiss();
                                addr.setError("Required field!");
                                addr.requestFocus();
                            }
                            if(pass.getText().toString().isEmpty()){
                                mDialog.dismiss();
                                pass.setError("Required field!");
                                pass.requestFocus();
                            }
                            if(conpass.getText().toString().isEmpty()){
                                mDialog.dismiss();
                                conpass.setError("Required field!");
                                conpass.requestFocus();
                            }
                        }
                        else if(len!=11 || (p.charAt(0)!='0') || (p.charAt(1)!='1') || (p.charAt(2)=='0') || (p.charAt(2)=='2')){
                            mDialog.dismiss();
                            phone.setError("Please enter a valid phone number!");
                            phone.requestFocus();
                        }
                        else if(pass.getText().toString().length() < 6){
                            mDialog.dismiss();
                            pass.setError("Enter at least 6 characters!");
                            pass.requestFocus();
                        }
                        else if (!pass.getText().toString().equals(conpass.getText().toString())) {
                            mDialog.dismiss();
                            conpass.setError("Passwords don't match!");
                            conpass.requestFocus();
                        }
                            else {
                            mDialog.dismiss();
                                number="+88"+phone.getText().toString().trim();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Signup.this);
                                builder1.setMessage("You will receive an SMS to "+number+" with the verification code. Proceed?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                Intent vericode = new Intent(Signup.this, VeriCode.class);
                                                vericode.putExtra("phone", phone.getText().toString().trim());
                                                vericode.putExtra("name", name.getText().toString());
                                                vericode.putExtra("addr", addr.getText().toString());
                                                vericode.putExtra("pass", pass.getText().toString());
                                                vericode.putExtra("conpass", conpass.getText().toString());
                                                startActivity(vericode);
                                            }
                                        });
                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();


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
