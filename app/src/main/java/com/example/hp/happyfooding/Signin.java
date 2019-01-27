package com.example.hp.happyfooding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import Common.Common;
import Model.User;

public class Signin extends AppCompatActivity {
    EditText phone, password;
    TextView fgp;
    Button signin;
    private FirebaseAuth mAuth;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        fgp = findViewById(R.id.fpw);
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(Signin.this);
                mDialog.setMessage("Please Wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!phone.getText().toString().isEmpty()) {
                            if (dataSnapshot.child(phone.getText().toString()).exists()) {
                                mDialog.dismiss();
                                User user = dataSnapshot.child(phone.getText().toString()).getValue(User.class);
                                user.setPhone(phone.getText().toString());
                                if(password.getText().toString().isEmpty()){
                                    password.setError("Enter password!");
                                    password.requestFocus();

                                }
                                else {
                                    if (user == null) {
                                        throw new AssertionError();
                                    }
                                    if (user.getPassword().equals(password.getText().toString())) {
                                       // Toast.makeText(Signin.this, "Welcome "+user.getName(), Toast.LENGTH_SHORT).show();
                                        Common.currentUser = user;
                                        if(phone.getText().toString().equals("01748535404")){
                                            Intent homeIntent = new Intent(Signin.this, AdminHome.class);
                                            startActivity(homeIntent);
                                            finish();
                                        }
                                        else {
                                            mAuth.signInWithEmailAndPassword(Common.currentUser.getAddress(), password.getText().toString());
                                            Intent homeIntent = new Intent(Signin.this, UProfile.class);
                                            startActivity(homeIntent);
                                            finish();
                                        }
                                    } else {
                                        password.setError("Incorrect password!");
                                        password.requestFocus();
                                    }
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(Signin.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            mDialog.dismiss();
                            Toast.makeText(Signin.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
                        }
                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        fgp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(Signin.this, ForgotPass.class);
               startActivity(i);

            }
        });
    }
}
