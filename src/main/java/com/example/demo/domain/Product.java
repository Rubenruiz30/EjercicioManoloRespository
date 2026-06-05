package com.example.demo.domain;

public class Product {

    private String name;
    private String size;
    private String price;
    private long id;

    public Product() {
    }

    public Product(String name, String size, String price, long id) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
