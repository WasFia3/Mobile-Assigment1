package com.example.assigment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    private Context context;
    private List<Product> productList;

    public CheckoutAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.textProductName.setText(product.getName());
        holder.textProductPrice.setText(String.format("$%.2f", product.getPrice()));

        updateQuantityTexts(holder, product); // أول تحديث عند التهيئة

        holder.btnIncrease.setOnClickListener(v -> {
            product.setQuantity(product.getQuantity() + 1);
            updateQuantityTexts(holder, product); // ⬅️ لازم تحدث التكست مباشرة
            notifyItemChanged(position);
            updateCart();
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int currentQty = product.getQuantity();
            if (currentQty > 1) {
                product.setQuantity(currentQty - 1);
                updateQuantityTexts(holder, product); // ⬅️ كمان لازم تحدث التكست هون
                notifyItemChanged(position);
            } else {
                productList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productList.size());
            }
            updateCart();
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void updateCart() {
        Cart.saveCart(productList); //save cart after update
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProductName, textProductPrice, textProductQuantity, textSelectedQuantity;
        Button btnIncrease, btnDecrease;

        public ViewHolder(View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.textProductName);
            textProductPrice = itemView.findViewById(R.id.textProductPrice);
            textProductQuantity = itemView.findViewById(R.id.textProductQuantity);
            textSelectedQuantity = itemView.findViewById(R.id.textSelectedQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncreaseQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecreaseQuantity);
        }
    }

    private void updateQuantityTexts(ViewHolder holder, Product product) {
        String quantityText = "Quantity: " + product.getQuantity();
        holder.textProductQuantity.setText(quantityText);
        holder.textSelectedQuantity.setText(String.valueOf(product.getQuantity()));
    }

}
