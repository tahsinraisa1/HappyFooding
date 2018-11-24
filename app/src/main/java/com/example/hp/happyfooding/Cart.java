package com.example.hp.happyfooding;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.happyfooding.Database.Database;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Common.Common;
import Model.Order;
import Model.Request;
import ViewHolder.CartAdapter;


public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtTotalPrice;
    Button btnplace;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnplace = findViewById(R.id.btnPlaceOrder);

        btnplace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                showAlertDialog();
            }
        });
        loadListFood();

    }
    private void loadListFood(){
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);
        final int[] total = {0};
        for(Order order:cart)
            total[0] +=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));

        txtTotalPrice.setText(Integer.toString(total[0]));
        adapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                String id = cart.get(position).getProductId();
                int p = (Integer.parseInt(cart.get(position).getPrice()))*(Integer.parseInt(cart.get(position).getQuantity()));
                new Database(getBaseContext()).oneClean(id);
                cart.remove(position);
                total[0] -=p;
                txtTotalPrice.setText(Integer.toString(total[0]));
                adapter.notifyItemRemoved(position);
            }
        });
    }

    private void showAlertDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address: ");

        final EditText editAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editAddress.setLayoutParams(lp);
        alertDialog.setView(editAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!editAddress.getText().toString().isEmpty()){
                    new Database(getBaseContext()).cleanCart();
                    //Toast.makeText(Cart.this,"Thank you, order Placed!", Toast.LENGTH_SHORT).show();
                    Intent pay = new Intent(Cart.this, PayPage.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("cart", (ArrayList<? extends Parcelable>) cart);
                    pay.putExtra("address", editAddress.getText().toString());
                    pay.putExtra("price", txtTotalPrice.getText().toString());
                    pay.putExtras(bundle);

                    startActivity(pay);
                    finish();
                }
                else {
                    Toast.makeText(Cart.this,"Shipping address required!", Toast.LENGTH_LONG).show();
                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }



}