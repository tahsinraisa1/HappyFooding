package com.example.hp.happyfooding;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.hp.happyfooding.Database.Database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Model.Food;
import Model.Order;

public class FoodDetail extends AppCompatActivity {
    TextView foodname, foodprice, fooddesc;
    ImageView foodimg;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId = "";

    FirebaseDatabase database;
    DatabaseReference food;
    Food currentFood;
    int cnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        food = database.getReference("Food");

        numberButton = findViewById(R.id.number_button);
        foodname = findViewById(R.id.food_name);
        fooddesc = findViewById(R.id.food_description);
        foodprice = findViewById(R.id.food_price);
        foodimg = findViewById(R.id.img_food);
        btnCart = findViewById(R.id.btnCart);

        if(getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty()){
            getDetailFood(foodId);
        }

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(numberButton.getNumber()) <= cnt){
                    new Database(getBaseContext()).addToCart(new Order(
                            foodId,
                            currentFood.getName(),
                            numberButton.getNumber(),
                            currentFood.getPrice()
                    ));
                    cnt -= Integer.parseInt(numberButton.getNumber());
                    Toast.makeText(FoodDetail.this, "Added to cart.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(FoodDetail.this, "Sorry! Requested quantity not available!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);


    }

    private void getDetailFood(final String foodId) {
        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                assert currentFood != null;
                cnt = Integer.parseInt(currentFood.getQuantity());
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(foodimg);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                foodprice.setText(currentFood.getPrice());
                fooddesc.setText(currentFood.getDescription());
                foodname.setText(currentFood.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
