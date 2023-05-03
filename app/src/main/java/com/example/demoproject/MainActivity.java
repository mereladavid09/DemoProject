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
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.search.SearchBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private UserAdapter userAdapter;

    private CartAdapter cartAdapter;

    private SearchView searchView;


    private final String ALL_USERS = "https://dummyjson.com/users?limit=0";
    private final String ALL_CARTS = "https://dummyjson.com/carts";
    private final String ALL_PRODUCTS = "https://dummyjson.com/products?limit=0";

    private final String ALL_CATEGORIES = "https://dummyjson.com/products/categories";

    private final String GET_PRODUCT_BY_CATEGORY ="https://dummyjson.com/products/category/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.drawerLayout = findViewById(R.id.drawerLayout);
        this.navigationView = findViewById(R.id.navView);
        this.toolbar = findViewById(R.id.toolbar);
        this.recyclerView = findViewById(R.id.recyclerView);
        this.searchView = findViewById(R.id.searchBar);

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
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (recyclerView.getAdapter() instanceof ProductAdapter) {
            productAdapter.getFilter().filter(query);
        }
        else if (recyclerView.getAdapter() instanceof UserAdapter) {
            userAdapter.getFilter().filter(query);
        }
        else if (recyclerView.getAdapter() instanceof CartAdapter) {
           cartAdapter.getFilter().filter(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("QUERY TEXT", newText);
        if (newText.isEmpty()){
            try {
                JsonFetcher jsonFetcher = new JsonFetcher();
                if (recyclerView.getAdapter() instanceof ProductAdapter) {
                    ((ProductAdapter) recyclerView.getAdapter()).setProductList(jsonFetcher.execute(ALL_PRODUCTS,"Products").get());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                else if (recyclerView.getAdapter() instanceof UserAdapter) {
                    ((UserAdapter) recyclerView.getAdapter()).setUserList(jsonFetcher.execute(ALL_USERS,"Users").get());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                else if (recyclerView.getAdapter() instanceof CartAdapter) {
                    ((CartAdapter) recyclerView.getAdapter()).setCartList(jsonFetcher.execute(ALL_CARTS,"Carts").get());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }catch (Exception e){
                Log.d("JsonFetcher", e.getMessage());
            }
        }
        return false;
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

                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.nav_user_menu);

                return true;

            } else if (itemId == R.id.Carts) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Cart> carts = jsonFetcher.execute(ALL_CARTS,"Carts").get();
                cartAdapter = new CartAdapter(carts);
                recyclerView.setAdapter(cartAdapter);
                return true;

            } else if (itemId == R.id.Products || itemId == R.id.all_products) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Product> products = jsonFetcher.execute(ALL_PRODUCTS,"Products").get();
                productAdapter = new ProductAdapter(products);
                recyclerView.setAdapter(productAdapter);

                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.nav_product_menu);

                return true;
            } else if (itemId == R.id.electronics) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Product> electronics = new ArrayList<>();
                electronics.addAll(jsonFetcher.execute(GET_PRODUCT_BY_CATEGORY,"Electronics").get());
                ProductAdapter electronicAdapter = new ProductAdapter(electronics);
                recyclerView.setAdapter(electronicAdapter);
                return true;
            }
             else if (itemId == R.id.groceries) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Product> groceries = new ArrayList<>();
                groceries.addAll(jsonFetcher.execute(GET_PRODUCT_BY_CATEGORY,"Groceries").get());
                ProductAdapter groceriesAdapter = new ProductAdapter(groceries);
                recyclerView.setAdapter(groceriesAdapter);
                return true;
            }
            else if (itemId == R.id.beauty) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Product> beauty = new ArrayList<>();
                beauty.addAll(jsonFetcher.execute(GET_PRODUCT_BY_CATEGORY,"Beauty").get());
                ProductAdapter beautyAdapter = new ProductAdapter(beauty);
                recyclerView.setAdapter(beautyAdapter);
                return true;
            }
            else if (itemId == R.id.home) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Product> home = new ArrayList<>();
                home.addAll(jsonFetcher.execute(GET_PRODUCT_BY_CATEGORY,"Home").get());
                ProductAdapter homeAdapter = new ProductAdapter(home);
                recyclerView.setAdapter(homeAdapter);
                return true;
            }
            else if (itemId == R.id.clothing) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Product> clothing = new ArrayList<>();
                clothing.addAll(jsonFetcher.execute(GET_PRODUCT_BY_CATEGORY,"Clothing").get());
                ProductAdapter clothingAdapter = new ProductAdapter(clothing);
                recyclerView.setAdapter(clothingAdapter);
                return true;
            }

            else if (itemId == R.id.autoMoto) {
                JsonFetcher jsonFetcher = new JsonFetcher();
                List<Product> autoMoto = new ArrayList<>();
                autoMoto.addAll(jsonFetcher.execute(GET_PRODUCT_BY_CATEGORY,"AutoMoto").get());
                ProductAdapter autoMotoAdapter = new ProductAdapter(autoMoto);
                recyclerView.setAdapter(autoMotoAdapter);
                return true;
            }

            else if (itemId == R.id.back) {
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.nav_header_menu);
                return true;
            }
            else if (itemId == R.id.back_users) {
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.nav_header_menu);
                return true;
            }

        }catch (Exception e){
            Log.d("ERROR", "onNavigationItemSelected: " + e.getMessage());
        }
        return false;
    }

}