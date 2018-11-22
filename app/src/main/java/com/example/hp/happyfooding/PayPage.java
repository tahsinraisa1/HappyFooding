package com.example.hp.happyfooding;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Common.Common;
import Model.Order;
import Model.Request;

public class PayPage extends AppCompatActivity {
    ImageView rocket, bkash;
    LayoutInflater inflater;
    String addr, tprice;
    List<Order> ccart;
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_page);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        inflater = PayPage.this.getLayoutInflater();

        rocket = findViewById(R.id.rocket);
        bkash = findViewById(R.id.bkash);
        addr = getIntent().getStringExtra("address");
        tprice = getIntent().getStringExtra("price");
        ccart = getIntent().getParcelableArrayListExtra("cart");

        rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(PayPage.this);
                builder1.setMessage("Please send the food purchase cost to the following Rocket number: 016432093779 and proceed after receiving Txn. ID.");
                builder1.setCancelable(true);

                builder1.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       View view;
                       view = inflater.inflate(R.layout.paydialog, null );
                       AlertDialog.Builder b = new AlertDialog.Builder(PayPage.this);
                       final Dialog alert = b.create();
                       b.setView(view);
                        final EditText amount, tid;
                        amount = view.findViewById(R.id.amount);
                        tid = view.findViewById(R.id.tid);

                       b.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               if(!amount.getText().toString().isEmpty() && !tid.getText().toString().isEmpty()){
                                   if(!amount.getText().toString().equals(tprice)){
                                       Toast.makeText(PayPage.this, "Enter the correct amount!", Toast.LENGTH_SHORT).show();
                                   }
                                   else if(tid.getText().toString().length()!=10){
                                       Toast.makeText(PayPage.this, "Enter valid Txn. Id!", Toast.LENGTH_SHORT).show();
                                   }
                                   else {
                                       Request request = new Request(
                                               Common.currentUser.getPhone(),
                                               Common.currentUser.getName(),
                                               addr,
                                               tprice,
                                               ccart
                                       );
                                       requests.child(String.valueOf(System.currentTimeMillis()))
                                               .setValue(request);

                                       Toast.makeText(PayPage.this,"Thanks! Your order will be processed after verification of payment.", Toast.LENGTH_LONG).show();
                                   }
                               }
                               else {
                                   Toast.makeText(PayPage.this, "Fill up all fields!", Toast.LENGTH_SHORT).show();
                               }

                           }
                       });
                       b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       });
                       b.show();
                    }
                });
                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.show();

            }
        });

        bkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(PayPage.this);
                builder1.setMessage("Please send the food purchase cost to the following bKash number: 01643209377 and proceed after receiving Txn. ID.");
                builder1.setCancelable(true);

                builder1.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View view;
                        view = inflater.inflate(R.layout.paydialog, null );
                        AlertDialog.Builder b = new AlertDialog.Builder(PayPage.this);
                        final Dialog alert = b.create();
                        b.setView(view);
                        final EditText amount, tid;
                        amount = view.findViewById(R.id.amount);
                        tid = view.findViewById(R.id.tid);

                        b.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!amount.getText().toString().isEmpty() && !tid.getText().toString().isEmpty()){
                                    if(!amount.getText().toString().equals(tprice)){
                                        Toast.makeText(PayPage.this, "Enter the correct amount!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(tid.getText().toString().length()!=9){
                                        Toast.makeText(PayPage.this, "Enter valid Txn. Id!", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Request request = new Request(
                                                Common.currentUser.getPhone(),
                                                Common.currentUser.getName(),
                                                addr,
                                                tprice,
                                                ccart
                                        );
                                        requests.child(String.valueOf(System.currentTimeMillis()))
                                                .setValue(request);

                                        Toast.makeText(PayPage.this,"Thanks! Your order will be processed after verification of payment.", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(PayPage.this, "Fill up all fields!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        b.show();
                    }
                });
                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.show();
            }
        });
    }
}
