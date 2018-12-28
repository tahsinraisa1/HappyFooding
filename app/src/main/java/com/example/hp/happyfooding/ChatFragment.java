package com.example.hp.happyfooding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Date;

import Common.Common;
import Interface.ItemClickListener;
import Model.Chatdata;
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
    FirebaseRecyclerAdapter<Chatdata, ChatViewHolder> mAdapter;
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
        mReference = database.getReference("Chatdata").child(Common.currentUser.getPhone());

        mChatInput = findViewById(R.id.chat_input);
        mSend = findViewById(R.id.send);
        date = new Date();
        layoutManager = new LinearLayoutManager(this);

        chat = findViewById(R.id.chat_message);
        chat.setHasFixedSize(true);
        chat.setLayoutManager(layoutManager);


        mAdapter = new FirebaseRecyclerAdapter<Chatdata, ChatViewHolder>(Chatdata.class, R.layout.row_chat, ChatViewHolder.class, mReference) {
            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder, Chatdata model, int position) {
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
                        Chatdata data = new Chatdata(date.toString(), mUserId, mChatInput.getText().toString(), mUsername);
                        mReference.child(String.valueOf(new Date().getTime())).setValue(data);
                        mChatInput.getText().clear();

                    }

                    return true;
                }
            });

            mSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mChatInput.getText().toString().isEmpty()){
                        Chatdata data = new Chatdata(date.toString(), mUserId, mChatInput.getText().toString(), mUsername);
                        mReference.child(String.valueOf(new Date().getTime())).setValue(data);
                        mChatInput.getText().clear();
                    }

                }
            });

    }
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("Delete")){
            deleteChat(mAdapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }
    private void deleteChat(String key) {
        mReference.child(key).removeValue();
    }

}
