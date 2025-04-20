package com.example.assigment1;

import java.io.Serializable;

public class Product implements Serializable {

    private static int nextId = 1; // static counter to auto-increment IDs

    private int id;
    private String name;
    private String category;
    private String description;
    private int quantity;
    private double price;
    private int imageResourceId;

    private int selectedQuantity;

    // Constructor
    public Product(String name, String category, String description, int quantity, double price, int imageResourceId) {
        this.id = nextId++; // Assign and increment
        this.name = name;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

}
