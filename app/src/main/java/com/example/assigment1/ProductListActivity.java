package com.example.assigment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    private SearchView searchView;
    private Spinner spinnerCategory;

    private ImageView cartIcon;

    private  ImageView profileIcon;

    private ImageView homeIcon;


    private String selectedCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        cartIcon = findViewById(R.id.cartIcon);
        profileIcon = findViewById(R.id.profileIcon);
        homeIcon = findViewById(R.id.homeIcon);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // menu of products
        productList = new ArrayList<>();
        productList.add(new Product("Canvas", "Surfaces", "A4 size canvas", 10, 4.99, R.drawable.canvas));
        productList.add(new Product("Coloring Pencils", "Painting", "Set of 12 pencils", 15, 3.49, R.drawable.coloring_pencils));
        productList.add(new Product("Coloring Pens", "Painting", "Pack of 10 pens", 12, 2.99, R.drawable.coloring_pens));
        productList.add(new Product("Drawing Pens", "Drawing", "Fine line pens", 8, 5.99, R.drawable.drawing_pens));
        productList.add(new Product("Water Colors", "Painting", "Watercolor palette", 20, 6.49, R.drawable.water_colors));


        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

        // spinner
        String[] categories = {"All", "Surfaces", "Drawing", "Painting", "Planning", "Sculpting", "Blending"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(spinnerAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories[position];
                filter(searchView.getQuery().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        // Checkout action!
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });

        // Checkout action!
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        homeIcon.setOnClickListener(v ->{
            Intent intent = new Intent(ProductListActivity.this, ProductListActivity.class);
            startActivity(intent);
        });


    }



    private void filter(String text) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            boolean matchesText = product.getName().toLowerCase().contains(text.toLowerCase());
            boolean matchesCategory = selectedCategory.equals("All") || product.getCategory().equalsIgnoreCase(selectedCategory);

            if (matchesText && matchesCategory) {
                filteredList.add(product);
            }
        }
        productAdapter.setFilteredList(filteredList);
    }
}
