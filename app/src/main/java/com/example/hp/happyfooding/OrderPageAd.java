package com.example.hp.happyfooding;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import Common.Common;
import Interface.ItemClickListener;
import Model.Request;
import ViewHolder.AdOrderViewHolder;

public class OrderPageAd extends AppCompatActivity {

    TextView id, phn, name, stts, addr;
    FirebaseDatabase db;
    DatabaseReference requests;
    MaterialSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page_ad);

        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");



        id = findViewById(R.id.order_id);
        addr = findViewById(R.id.order_addr);
        stts = findViewById(R.id.order_status);
        phn = findViewById(R.id.order_phone);
        name = findViewById(R.id.order_name);

        loadOrders();
    }

    private void loadOrders() {
        id.setText(getIntent().getStringExtra("id"));
        addr.setText(getIntent().getStringExtra("addr"));
        stts.setText(getIntent().getStringExtra("status"));
        phn.setText(getIntent().getStringExtra("phn"));
        name.setText(getIntent().getStringExtra("name"));
    }


}
