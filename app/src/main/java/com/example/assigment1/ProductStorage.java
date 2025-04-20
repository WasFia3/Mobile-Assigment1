package com.example.assigment1;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProductStorage {
    private static final String PREF_NAME = "ProductPrefs";
    private static final String KEY_PRODUCTS = "products";

    public static void saveProducts(ArrayList<Product> products) {
        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(products);
        editor.putString(KEY_PRODUCTS, json);
        editor.apply();
    }

    public static ArrayList<Product> loadProducts() {
        SharedPreferences prefs = MyApp.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_PRODUCTS, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        if (json != null) {
            return new Gson().fromJson(json, type);
        }
        return new ArrayList<>();
    }
}
