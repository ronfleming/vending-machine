package com.techelevator.view;

import java.math.BigDecimal;

public class Gum extends Item {
    public Gum(String slotLocation, String productName, BigDecimal price, String productType) {
        super(slotLocation, productName, price, productType);
    }

    @Override
    public String dispenseMessage() {
        return "Chew Chew, Yum!";
    }
}
