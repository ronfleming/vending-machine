package com.techelevator.view;

import java.math.BigDecimal;

public abstract class Item {
    private String slotLocation;
    private String productName;
    private BigDecimal price;
    private String productType;
    private int quantity;

    public Item(String slotLocation, String productName, BigDecimal price, String productType) {
        this.slotLocation = slotLocation;
        this.productName = productName;
        this.price = price;
        this.productType = productType;
        this.quantity = 5;
    }
/*    public void printItem() {
       // System.out.printf("% % %20f %25f", slotLocation, productName, price, quantity);
        System.out.println("(" + slotLocation + ")" + "\t " + productName + "\t\t\t\t" + price + "\t\t " + quantity);
    }*/
    public abstract String dispenseMessage();

    public String getSlotLocation() {
        return slotLocation;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        String openParens = "(";
        String closeParens = ")";
        String dollarSign = "$";
        return String.format("%s%s%-10s %-30s %s%-10s %s%s in stock)", openParens, this.slotLocation, closeParens,
                this.productName, dollarSign, this.price, openParens, this.quantity);
    }

}
