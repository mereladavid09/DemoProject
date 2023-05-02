package com.example.demoproject;

import adapters.CartAdapter;
import adapters.ProductAdapter;
import adapters.UserAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import json.JsonFetcher;
import models.Cart;
import models.Product;
import models.user.User;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private UserAdapter userAdapter;

    private CartAdapter cartAdapter;

    private final String ALL_USERS = "https://dummyjson.com/users";
    private final String ALL_CARTS = "https://dummyjson.com/carts";
    private final String ALL_PRODUCTS = "https://dummyjson.com/products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.drawerLayout = findViewById(R.id.drawerLayout);
        this.navigationView = findViewById(R.id.navView);
        this.toolbar = findViewById(R.id.toolbar);
        this.recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        try {
            if (itemId == R.id.Users) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<User> users = jsonFetcher.execute(ALL_USERS,"Users").get();
                userAdapter = new UserAdapter(users);
                recyclerView.setAdapter(userAdapter);
                Toast.makeText(this, "Users selected from menu", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.Charts) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Cart> carts = jsonFetcher.execute(ALL_CARTS,"Carts").get();
                cartAdapter = new CartAdapter(carts);
                recyclerView.setAdapter(cartAdapter);

                Toast.makeText(this, "Charts selected from menu", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.Products) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Product> products = jsonFetcher.execute(ALL_PRODUCTS,"Products").get();
                productAdapter = new ProductAdapter(products);
                recyclerView.setAdapter(productAdapter);
                Toast.makeText(this, "Products selected from menu", Toast.LENGTH_SHORT).show();
                return true;
            }

        }catch (Exception e){
            Log.d("ERROR", "onNavigationItemSelected: " + e.getMessage());
        }
        return false;
    }
}