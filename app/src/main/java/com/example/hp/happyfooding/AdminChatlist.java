package com.example.hp.happyfooding;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amulyakhare.textdrawable.TextDrawable;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Interface.ItemClickListener;
import Model.User;
import ViewHolder.UViewHolder;

public class AdminChatlist extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference users;
    FirebaseRecyclerAdapter<User, UViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chatlist);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");

        recyclerView = findViewById(R.id.vieew);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadChats();

    }

    private void loadChats() {
        adapter = new FirebaseRecyclerAdapter<User, UViewHolder>(User.class, R.layout.adchat, UViewHolder.class, users) {
            @Override
            protected void populateViewHolder(UViewHolder viewHolder, final User model, int position) {
                viewHolder.uname.setText("("+model.getName()+")");
                viewHolder.uphone.setText(adapter.getRef(position).getKey());
                TextDrawable drawable = TextDrawable.builder().buildRound("0", Color.RED);
              //  viewHolder.count.setImageDrawable(drawable);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent cf = new Intent(AdminChatlist.this, AdChatFragment.class);
                        cf.putExtra("user", adapter.getRef(position).getKey());
                        startActivity(cf);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
