package com.example.hp.happyfooding;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.icu.text.SelectFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.happyfooding.Database.Database;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import Common.Common;
import Interface.ItemClickListener;
import Model.Category;
import Model.F1;
import Model.Food;
import Model.Order;
import Model.Request;
import ViewHolder.FoodViewHolder;
import ViewHolder.AdMenuViewHolder;

public class AdminHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category, foodList;
    TextView fullname;
    Button newcat;
    String categoryId = "";
    RecyclerView review;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Food,AdMenuViewHolder> adapter;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    View vie;
    LayoutInflater inflater;
    EditText id, name, price, desc, url, menuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("Admin view");
        setSupportActionBar(toolbar);

        newcat = findViewById(R.id.addcat);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");
        foodList = database.getReference("Food");


        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        fullname = headerview.findViewById(R.id.fullname1);
        fullname.setText(Common.currentUser.getName());
        inflater = AdminHome.this.getLayoutInflater();

        review = findViewById(R.id.recyclerview);
        review.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        review.setLayoutManager(layoutManager);
        materialSearchBar = findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter food name");
        //   materialSearchBar.setSpeechMode(false);
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<String>();
                for(String search: suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }

                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){
                    review.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<Food, AdMenuViewHolder>(Food.class, R.layout.admenu_item, AdMenuViewHolder.class, foodList) {

            @Override
            protected void populateViewHolder(AdMenuViewHolder viewHolder, final Food model, final int position) {
                viewHolder.txtmenuname.setText(model.getName().toString());
                viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminHome.this);
                        alertDialog.setTitle("Edit Food");
                        alertDialog.setMessage("Change Food Price:");

                        final EditText editPrice = new EditText(AdminHome.this);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        editPrice.setLayoutParams(lp);
                        editPrice.setHint("Edit price");
                        alertDialog.setView(editPrice);
                        alertDialog.setIcon(R.drawable.ic_edit);

                        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(!editPrice.getText().toString().isEmpty()){
                                    String key = adapter.getRef(position).getKey();
                                    model.setPrice(editPrice.getText().toString());
                                    foodList.child(key).setValue(model);
                                    Toast.makeText(AdminHome.this,"Edit saved", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(AdminHome.this,"No changes.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
                viewHolder.dlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminHome.this);
                        alertDialog.setTitle("Confirm Deletion");
                        alertDialog.setMessage("Are you sure you want to delete this category?" +
                                "(All foods of this category will be deleted!)");

                        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String key = adapter.getRef(position).getKey();
                                foodList.child(key).removeValue();
                                Toast.makeText(AdminHome.this,"Category deleted!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
                final Food clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodList = new Intent(AdminHome.this, FoodDetail.class);
                        foodList.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });
            }
        };
        review.setAdapter(adapter);

        newcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.addcat:
                        v = inflater.inflate(R.layout.dialog_custom, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminHome.this);
                        final Dialog alert = builder.create();
                        builder.setView(v);


                        final View finalV = v;
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                id = finalV.findViewById(R.id.editText6);
                                name = finalV.findViewById(R.id.editText);
                                price = finalV.findViewById(R.id.editText2);
                                desc = finalV.findViewById(R.id.editText3);
                                url = finalV.findViewById(R.id.editText5);
                                menuid = finalV.findViewById(R.id.editText4);

                                if(!id.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && !url.getText().toString().isEmpty() && !desc.getText().toString().isEmpty() && !price.getText().toString().isEmpty() && !menuid.getText().toString().isEmpty()){
                                    Food food = new Food(name.getText().toString(),url.getText().toString(),price.getText().toString(),desc.getText().toString(),menuid.getText().toString());
                                    foodList.child(id.getText().toString()).setValue(food);
                                    Toast.makeText(AdminHome.this,"Food added!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(AdminHome.this,"All fields are required!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                        break;
                }
            }
        });

    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.foodname.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.foodimg);
                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(AdminHome.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId", searchAdapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });

            }
        };
        review.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        foodList.orderByChild("menuId")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            Food item = postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(AdminHome.this, Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(AdminHome.this, OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_signout) {
            Intent signIn = new Intent(AdminHome.this, Signin.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
