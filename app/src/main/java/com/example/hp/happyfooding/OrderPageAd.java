package com.example.hp.happyfooding;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import Common.Common;
import Interface.ItemClickListener;
import Model.Food;
import Model.ReqFood;
import Model.Request;
import ViewHolder.AdOrderViewHolder;

public class OrderPageAd extends AppCompatActivity {

    TextView id, phn, name, stts, addr, items, trx;
    FirebaseDatabase db;
    DatabaseReference requests, f;
    MaterialSpinner spinner;
    String pass;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page_ad);

        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");
        f = requests.child("foods");

        pass = getIntent().getStringExtra("id");
        id = findViewById(R.id.order_id);
        addr = findViewById(R.id.order_addr);
        stts = findViewById(R.id.order_status);
        phn = findViewById(R.id.order_phone);
        name = findViewById(R.id.order_name);
        items = findViewById(R.id.order_items);
        trx = findViewById(R.id.trxid);

        loadOrders();
    }

    private void loadOrders() {
        id.setText(getIntent().getStringExtra("id"));
        addr.setText(getIntent().getStringExtra("addr"));
        stts.setText(getIntent().getStringExtra("status"));
        phn.setText(getIntent().getStringExtra("phn"));
        name.setText(getIntent().getStringExtra("name"));
        trx.setText(getIntent().getStringExtra("trx"));

        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderPageAd.this, OrderItems.class);
                i.putExtra("id", pass);
                startActivity(i);
            }
        });
    }


}
