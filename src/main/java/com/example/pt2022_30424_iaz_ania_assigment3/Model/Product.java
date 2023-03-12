package com.example.pt2022_30424_iaz_ania_assigment3.Model;

public class Product {
    private int id;
    private String nameProduct;
    private int price;
    private int quantity;

    public Product(int id,String nameProduct,int price,int quantity){
        super();
        this.id=id;
        this.nameProduct=nameProduct;
        this.price=price;
        this.quantity=quantity;
    }

    public Product(String nameProduct, int price, int quantity){
        super();
        this.nameProduct=nameProduct;
        this.price=price;
        this.quantity=quantity;
    }

    public Product(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
