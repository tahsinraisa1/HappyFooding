package com.example.hp.happyfooding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import Common.Common;
import Interface.ItemClickListener;
import Model.ChatData;
import Model.User;
import ViewHolder.ChatViewHolder;

public class ChatFragment extends AppCompatActivity {

    /** Database instance **/
    private DatabaseReference mReference;
    private DatabaseReference mR;

    /** UI Components **/
    private EditText mChatInput;
    private ImageView mSend;

    /** Class variables **/
    private String mUsername;
    private String mUserId;
    LayoutInflater inflater;
    FirebaseRecyclerAdapter<ChatData, ChatViewHolder> mAdapter;
    RecyclerView chat;
    RecyclerView.LayoutManager layoutManager;
    Date date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_fragment);

        mUsername = Common.currentUser.getName();
        mUserId = Common.currentUser.getPhone();
        inflater = ChatFragment.this.getLayoutInflater();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReference = database.getReference("User");
        mR = mReference.child(Common.currentUser.getPhone()).child("chat");

        mChatInput = findViewById(R.id.chat_input);
        mSend = findViewById(R.id.send);
        date = new Date();
        layoutManager = new LinearLayoutManager(this);

        chat = findViewById(R.id.chat_message);
        chat.setHasFixedSize(true);
        chat.setLayoutManager(layoutManager);


        mAdapter = new FirebaseRecyclerAdapter<ChatData, ChatViewHolder>(ChatData.class, R.layout.row_chat, ChatViewHolder.class, mR) {
            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder, ChatData model, int position) {
                viewHolder.phone.setText(model.getId());
                viewHolder.date.setText(model.getDate());
                viewHolder.message.setText(model.getMessage());
                viewHolder.name.setText("("+model.getName()+")");

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });

            }
        };
        chat.setAdapter(mAdapter);

              mChatInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if(!mChatInput.getText().toString().isEmpty()){
                        ChatData data = new ChatData(date.toString(), mUserId, mChatInput.getText().toString(), mUsername);
                        Common.currentUser.setChat(data);
                        mR.child(String.valueOf(new Date().getTime())).setValue(data);
                        mChatInput.getText().clear();

                    }

                    return true;
                }
            });

            mSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mChatInput.getText().toString().isEmpty()){
                        ChatData data = new ChatData(date.toString(), mUserId, mChatInput.getText().toString(), mUsername);
                        Common.currentUser.setChat(data);
                        mR.child(String.valueOf(new Date().getTime())).setValue(data);
                        mChatInput.getText().clear();
                    }

                }
            });

    }

}
