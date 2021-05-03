package com.techelevator.view;

import java.math.BigDecimal;

public class Candy extends Item {
    public Candy(String slotLocation, String productName, BigDecimal price, String productType) {
        super(slotLocation, productName, price, productType);
    }

    @Override
    public String dispenseMessage() {

        return "Munch Munch, Yum!";
    }
}
