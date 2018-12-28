package com.example.hp.happyfooding;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.happyfooding.Database.Database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Common.Common;
import Model.Food;
import Model.Order;
import Model.Request;

public class PayPage extends AppCompatActivity {
    ImageView rocket, bkash;
    LayoutInflater inflater;
    String addr, tprice;
    List<Order> ccart;
    List<Order> cart = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference requests;
    DatabaseReference food;
    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_page);

        database = FirebaseDatabase.getInstance();
        food = database.getReference("Food");
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
                                               ccart,
                                               tid.getText().toString()
                                       );
                                      // Toast.makeText(PayPage.this, ""+ccart.size(), Toast.LENGTH_LONG).show();
                                       requests.child(String.valueOf(System.currentTimeMillis()))
                                               .setValue(request);
                                       new Database(getBaseContext()).cleanCart();
                                       for(int i=0;i<ccart.size();i++){
                                           final String pid = ccart.get(i).getProductId();
                                           final String quant = ccart.get(i).getQuantity();
                                           final int[] x = {0};
                                           food.child(pid).addValueEventListener(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                   if(x[0] ==0){
                                                       Food f = dataSnapshot.getValue(Food.class);
                                                       assert f != null;
                                                       String qq = f.getQuantity();
                                                       int min = Integer.parseInt(qq)-Integer.parseInt(quant);
                                                       f.setQuantity(String.valueOf(min));
                                                       food.child(pid).child("quantity").setValue(String.valueOf(min));
                                                       x[0] =1;
                                                   }
                                               }

                                               @Override
                                               public void onCancelled(@NonNull DatabaseError databaseError) {

                                               }
                                           });
                                           // food.child(pid).child("quantity").setValue(String.valueOf(Integer.parseInt(qq)-Integer.parseInt(quant)));
                                       }

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
                builder1.setMessage("Please send the food purchase cost to the following bKash number: 01643209377 and proceed after receiving Trx. ID.");
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
                                        Toast.makeText(PayPage.this, "Enter valid Trx. Id!", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Request request = new Request(
                                                Common.currentUser.getPhone(),
                                                Common.currentUser.getName(),
                                                addr,
                                                tprice,
                                                ccart,
                                                tid.getText().toString()
                                        );
                                        requests.child(String.valueOf(System.currentTimeMillis()))
                                                .setValue(request);
                                        new Database(getBaseContext()).cleanCart();
                                        for(int i=0;i<ccart.size();i++){
                                            final String pid = ccart.get(i).getProductId();
                                            final String quant = ccart.get(i).getQuantity();
                                            final int[] x = {0};
                                            food.child(pid).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(x[0] ==0){
                                                        Food f = dataSnapshot.getValue(Food.class);
                                                        assert f != null;
                                                        String qq = f.getQuantity();
                                                        int min = Integer.parseInt(qq)-Integer.parseInt(quant);
                                                        f.setQuantity(String.valueOf(min));
                                                        food.child(pid).child("quantity").setValue(String.valueOf(min));
                                                        x[0] =1;
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            // food.child(pid).child("quantity").setValue(String.valueOf(Integer.parseInt(qq)-Integer.parseInt(quant)));
                                        }

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
