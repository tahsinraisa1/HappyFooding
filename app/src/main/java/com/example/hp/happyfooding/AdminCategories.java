package com.example.hp.happyfooding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Interface.ItemClickListener;
import Model.Category;
import ViewHolder.AdCatViewHolder;
import ViewHolder.AdMenuViewHolder;

public class AdminCategories extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference category;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, AdCatViewHolder> adapter;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categories);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        recyclerView = findViewById(R.id.reccat);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FirebaseRecyclerAdapter<Category, AdCatViewHolder>(Category.class, R.layout.adcat_item, AdCatViewHolder.class, category) {
            @Override
            protected void populateViewHolder(AdCatViewHolder viewHolder, Category model, int position) {
                viewHolder.txtmenuname.setText(model.getName());
                viewHolder.id.setText(adapter.getRef(position).getKey());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
