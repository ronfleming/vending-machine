package com.techelevator.view;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private List<Item> productList = new ArrayList<>();

    public Product(List<Item> productList) {
        this.productList = productList;
    }

    public List<Item> getProductList() {
        return productList;
    }
    /*public void print() {
        for (Item item : productList) {
            item.printItem();
        }
    }*/
}
