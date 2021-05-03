package com.techelevator.view;

import java.math.BigDecimal;

public class Drink extends Item {
    public Drink(String slotLocation, String productName, BigDecimal price, String productType) {
        super(slotLocation, productName, price, productType);
    }

    @Override
    public String dispenseMessage() {
        return "Glug Glug, Yum!";
    }
}
