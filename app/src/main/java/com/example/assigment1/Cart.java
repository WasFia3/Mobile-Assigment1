package com.example.assigment1;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static final String PREF_NAME = "CartPrefs";
    private static final String KEY_CART = "cart_items";

    public static void addToCart(Product product) {
        ArrayList<Product> cart = getCartItems();
        cart.add(product);
        saveCart(cart);
    }

    public static ArrayList<Product> getCartItems() {
        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_CART, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        if (json != null) {
            return new Gson().fromJson(json, type);
        }
        return new ArrayList<>();
    }

    public static void saveCart(List<Product> cartItems) {
        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(cartItems);
        editor.putString(KEY_CART, json);
        editor.apply();
    }

    public static void clearCart() {
        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_CART).apply();
    }

    private static void saveCart(ArrayList<Product> cartItems) {
        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(cartItems);
        editor.putString(KEY_CART, json);
        editor.apply();
    }
}
