package com.techelevator.view;

import com.techelevator.VendingMachineCLI;
import com.techelevator.utilities.VendingMachineLog;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class Purchase {
    private BigDecimal balance = new BigDecimal(0);
    private Product products;
    private BigDecimal bigPrice = new BigDecimal(BigInteger.ZERO);
    private HashMap<String, BigDecimal> map;

    private static BigDecimal totalSales = new BigDecimal(BigInteger.ZERO);

    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    String formattedDate = myDateObj.format(myFormatObj);

    public Purchase() {

    }

    public Purchase(Product products) {

        this.products = products;
    }

    public BigDecimal getBalance() {

        return balance;
    }

    public static void setTotalSales(BigDecimal totalSales) {
        Purchase.totalSales = totalSales;
    }

    public static BigDecimal getTotalSales() {
        return totalSales;
    }

    public boolean isProductAvailable(String choiceOfProduct) {
        boolean isProductAvailable = false;
        for (int i = 0; i < products.getProductList().size(); i++) {
            if (products.getProductList().get(i).getSlotLocation().equalsIgnoreCase(choiceOfProduct)
                    && products.getProductList().get(i).getQuantity() > 0) {
                isProductAvailable = true;
            }
        }
        return isProductAvailable;
    }


    public void feedMoney(BigDecimal moneyInserted) {
        if (moneyInserted.intValue() == 1|| moneyInserted.intValue() == 2 ||
                moneyInserted.intValue() == 5 || moneyInserted.intValue() == 10) {
            this.balance = this.balance.add(moneyInserted);
        } else {
            System.out.println("\nPlease enter a valid denomination");
        }
    }

    public boolean isBalanceSufficient(String productChoice) {
        boolean isBalanceSufficient = false;
        //Get price of product
        try {
            for (int i = 0; i < products.getProductList().size(); i++) {
                if (products.getProductList().get(i).getSlotLocation().equalsIgnoreCase(productChoice)) {
                    this.bigPrice = products.getProductList().get(i).getPrice();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Invalid choice!");
        }
        BigDecimal difference = balance.subtract(this.bigPrice);
        int result = difference.compareTo(BigDecimal.ZERO);
        if (result >= 0) {
            isBalanceSufficient = true;
        }
        return isBalanceSufficient;
    }

    public String[] isDispenseSuccessful(String productChoice) {

        for (int i = 0; i < products.getProductList().size(); i++) {
            if (products.getProductList().get(i).getSlotLocation().equalsIgnoreCase(productChoice)) {
                int qty = products.getProductList().get(i).getQuantity();
                products.getProductList().get(i).setQuantity(qty - 1);
                BigDecimal beginningBalance = this.balance;
                this.balance = this.balance.subtract(this.bigPrice);
                System.out.println(products.getProductList().get(i).getProductName() + " $" +
                        this.bigPrice + " Balance: $" + this.balance);
                System.out.println(products.getProductList().get(i).dispenseMessage());
                VendingMachineLog.log(formattedDate + " " + products.getProductList().get(i).getProductName() + " "
                        + productChoice.toUpperCase() + " $" + beginningBalance + " $" + this.balance);

                totalSales = totalSales.add(this.bigPrice);
                String keyCheck = products.getProductList().get(i).getProductName();
                BigDecimal qtyOfProduct;

                /*Couldn't figure out why this line broke our tests
                qtyOfProduct = VendingMachineCLI.salesMap.get(keyCheck);*/

                for (Map.Entry<String, BigDecimal> map : VendingMachineCLI.salesMap.entrySet()) {
                    if (map.getKey().equalsIgnoreCase(keyCheck)) {
                        qtyOfProduct = map.getValue();
                        BigDecimal newQtyOfProduct;
                        int valueOfQty = qtyOfProduct.compareTo(BigDecimal.ZERO);
                        if(valueOfQty <= 0){
                            newQtyOfProduct = new BigDecimal(1);
                        }
                        else{
                            newQtyOfProduct = qtyOfProduct.add(BigDecimal.ONE);
                        }
                        VendingMachineCLI.salesMap.put(keyCheck, newQtyOfProduct);
                        break;
                    }
                }

            }
        }
        String[] modifiedArray = VendingMachineCLI.productToString(products);
        return modifiedArray;

    }

    public String giveChange() {
        String change = "";
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;
        int remainder;
        BigDecimal endingBalance = this.balance;
        BigDecimal interimBalance = this.balance.multiply(BigDecimal.valueOf(100));
        int convertedBalance = interimBalance.intValue();
        if (convertedBalance < 5) {
            System.out.println("No change due.");
        } else {
            while (convertedBalance >= 5) {
                if (convertedBalance >= 25) {
                    quarters = convertedBalance / 25;
                    remainder = convertedBalance % 25;
                    convertedBalance = remainder;
                } else if (convertedBalance >= 10) {
                    dimes = convertedBalance / 10;
                    remainder = convertedBalance % 10;
                    convertedBalance = remainder;
                } else {
                    nickels = convertedBalance / 5;
                    convertedBalance = 0;
                    this.balance = BigDecimal.ZERO;
                }
            }
            String q = "Quarters: ";
            String d = "Dimes: ";
            String n = "Nickels: ";
            change = "Here's your change: " + q + quarters + " \t" + d + dimes + " \t" + n + nickels;
            VendingMachineLog.log(formattedDate + " " + "GIVE CHANGE" + " $" + endingBalance + " $" + this.balance);

        }
        return change;

    }

}

