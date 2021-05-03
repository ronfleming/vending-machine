package com.techelevator.view;

import java.math.BigDecimal;

public class Chip extends Item {
    public Chip(String slotLocation, String productName, BigDecimal price, String productType) {
        super(slotLocation, productName, price, productType);
    }

    @Override
    public String dispenseMessage() {

        return "Crunch Crunch, Yum!";
    }

}
