package com.example.hp.happyfooding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Common.Common;

public class ChangePass extends AppCompatActivity {

    EditText np, cp;
    Button save;
    FirebaseDatabase db;
    DatabaseReference user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        np = findViewById(R.id.mmp);
        cp = findViewById(R.id.mnp);
        save = findViewById(R.id.emm);

        db = FirebaseDatabase.getInstance();
        user = db.getReference("User");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(np.getText().toString().equals(cp.getText().toString())){
                    user.child(Common.currentUser.getPhone()).child("password").setValue(np.getText().toString());
                    user.child(Common.currentUser.getPhone()).child("conpass").setValue(np.getText().toString());
                    Toast.makeText(ChangePass.this, "Password successfully changed!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ChangePass.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
