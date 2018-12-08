package com.example.hp.happyfooding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Interface.ItemClickListener;
import Model.Chatdata;
import Model.ReqFood;
import ViewHolder.ChatViewHolder;
import ViewHolder.ReqFoodHolder;

public class OrderItems extends AppCompatActivity {
    private DatabaseReference mReference;
    LayoutInflater inflater;
    FirebaseRecyclerAdapter<ReqFood, ReqFoodHolder> mAdapter;
    RecyclerView newe;
    RecyclerView.LayoutManager layoutManager;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        id = getIntent().getStringExtra("id");
        inflater = OrderItems.this.getLayoutInflater();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReference = database.getReference("Requests").child(id).child("foods");

        layoutManager = new LinearLayoutManager(this);

        newe = findViewById(R.id.newrec);
        newe.setHasFixedSize(true);
        newe.setLayoutManager(layoutManager);

        mAdapter = new FirebaseRecyclerAdapter<ReqFood, ReqFoodHolder>(ReqFood.class, R.layout.row_order, ReqFoodHolder.class, mReference) {
            @Override
            protected void populateViewHolder(ReqFoodHolder viewHolder, ReqFood model, int position) {
                viewHolder.foodname.setText(model.getProductName());
                viewHolder.foodquant.setText(model.getQuantity());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });

            }
        };
        newe.setAdapter(mAdapter);

    }
}
