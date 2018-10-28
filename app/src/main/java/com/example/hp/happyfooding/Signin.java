package com.example.hp.happyfooding;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.User;

public class Signin extends AppCompatActivity {
    EditText phone, password;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);

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
                                if(password.getText().toString().isEmpty()){
                                    Toast.makeText(Signin.this, "Please enter Password!", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    if (user == null) {
                                        throw new AssertionError();
                                    }
                                    if (user.getPassword().equals(password.getText().toString())) {
                                        Toast.makeText(Signin.this, "Welcome " + user.getName(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Signin.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
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
    }
}
