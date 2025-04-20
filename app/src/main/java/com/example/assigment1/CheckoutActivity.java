package com.example.assigment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckoutAdapter checkoutAdapter;
    private List<Product> cartItems;
    private Button paymentButton;

    private ImageView cartIcon;

    private  ImageView profileIcon;

    private ImageView homeIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerView = findViewById(R.id.checkoutRecyclerView);
        paymentButton = findViewById(R.id.paymentButton);
        cartIcon = findViewById(R.id.cartIcon);
        profileIcon = findViewById(R.id.profileIcon);
        homeIcon = findViewById(R.id.homeIcon);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // getting the cart items
        SharedPreferences cartPrefs = getSharedPreferences("CartPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = cartPrefs.getString("cart_items", null);
        Type type = new TypeToken<List<Product>>() {}.getType();
        cartItems = gson.fromJson(json, type);

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        checkoutAdapter = new CheckoutAdapter(this, cartItems);
        recyclerView.setAdapter(checkoutAdapter);

        // ACTION for pay button
        paymentButton.setOnClickListener(v -> {
            ArrayList<Product> cart = Cart.getCartItems();
            ArrayList<Product> allProducts = ProductStorage.loadProducts();

            Product updatedProduct = null;

            for (Product cartItem : cart) {
                for (Product p : allProducts) {
                    if (p.getName().equals(cartItem.getName())) {
                        int oldQuantity = p.getQuantity();
                        int purchasedQuantity = cartItem.getQuantity();
                        int newQuantity = oldQuantity - purchasedQuantity;

                        if (newQuantity < 0) newQuantity = 0;

                        p.setQuantity(newQuantity);


                        updatedProduct = p;
                    }
                }
            }

            ProductStorage.saveProducts(allProducts);
            Cart.clearCart();

            Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show();

            if (updatedProduct != null) {
                Intent intent = new Intent(CheckoutActivity.this, ProductInfoActivity.class);
                String updatedProductJson = new Gson().toJson(updatedProduct);
                intent.putExtra("product", updatedProductJson);
                startActivity(intent);
            }

            finish();
        });

        // Checkout action!
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(CheckoutActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });

        // Checkout action!
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(CheckoutActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Checkout action!
        homeIcon.setOnClickListener(v -> {
            Intent intent = new Intent(CheckoutActivity.this, ProductListActivity.class);
            startActivity(intent);
        });


    }
}
