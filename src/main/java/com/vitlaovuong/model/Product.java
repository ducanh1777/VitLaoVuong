package com.vitlaovuong.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int categoryId;
    private double salePrice;
    private Category category; // Optional linkage

    public Product() {
    }

    public Product(int id, String name, String description, double price, String imageUrl, int categoryId) {
        this(id, name, description, price, imageUrl, categoryId, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private double quantity;

    public Product(int id, String name, String description, double price, String imageUrl, int categoryId,
            double quantity) {
        this(id, name, description, price, imageUrl, categoryId, quantity, 0);
    }

    public Product(int id, String name, String description, double price, String imageUrl, int categoryId,
            double quantity, double salePrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.salePrice = salePrice;
    }

    public Product(int id, String name, String description, double price, String imageUrl, int categoryId) {
        this(id, name, description, price, imageUrl, categoryId, 0);
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
