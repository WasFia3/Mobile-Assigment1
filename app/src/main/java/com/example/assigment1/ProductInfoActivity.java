package com.example.assigment1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class ProductInfoActivity extends AppCompatActivity {

    private Cart cart;

    private int selectedQuantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        String productJson = getIntent().getStringExtra("product");
        if (productJson != null && !productJson.isEmpty()) {
            Product product = new Gson().fromJson(productJson, Product.class);

            // get updated from checkout
            int updatedQuantity = getIntent().getIntExtra("updatedQuantity", -1);
            if (updatedQuantity != -1) {
                selectedQuantity = updatedQuantity;  // update
            }

            ImageView imageView = findViewById(R.id.product_image);
            TextView nameText = findViewById(R.id.product_name);
            TextView categoryText = findViewById(R.id.product_category);
            TextView descriptionText = findViewById(R.id.product_description);
            TextView quantityText = findViewById(R.id.product_quantity);
            TextView priceText = findViewById(R.id.product_price);
            Button addToCartBtn = findViewById(R.id.btn_add_to_cart);

            Button btnIncrease = findViewById(R.id.btn_increase_quantity);
            Button btnDecrease = findViewById(R.id.btn_decrease_quantity);
            TextView textSelectedQuantity = findViewById(R.id.text_selected_quantity);

            imageView.setImageResource(product.getImageResourceId());
            nameText.setText(product.getName());
            categoryText.setText("Category: " + product.getCategory());
            descriptionText.setText("Description: " + product.getDescription());
            quantityText.setText("Available: " + product.getQuantity());
            priceText.setText("Price: $" + product.getPrice());

            textSelectedQuantity.setText(String.valueOf(selectedQuantity));

            // Increase and decrease quantity logic
            btnIncrease.setOnClickListener(v -> {
                if (selectedQuantity < product.getQuantity()) {
                    selectedQuantity++;
                    textSelectedQuantity.setText(String.valueOf(selectedQuantity));
                } else {
                    Toast.makeText(this, "No more in stock", Toast.LENGTH_SHORT).show();
                }
            });

            btnDecrease.setOnClickListener(v -> {
                if (selectedQuantity > 1) {
                    selectedQuantity--;
                    textSelectedQuantity.setText(String.valueOf(selectedQuantity));
                }
            });

            addToCartBtn.setOnClickListener(v -> {
                Product selectedProduct = new Product(
                        product.getName(),
                        product.getCategory(),
                        product.getDescription(),
                        selectedQuantity,
                        product.getPrice(),
                        product.getImageResourceId()
                );

                Cart.addToCart(selectedProduct);  // add product to cart
                Toast.makeText(this, "Added to cart (" + selectedQuantity + ")", Toast.LENGTH_SHORT).show();

                // send updated product to the previous act
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedProduct", new Gson().toJson(selectedProduct));
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        }
    }
    }

