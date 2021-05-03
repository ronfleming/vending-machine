package com.techelevator.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PurchaseTest {
    private ByteArrayOutputStream output;

    @Before
    public void setup() {
        output = new ByteArrayOutputStream();
    }

    @Test
    public void checks_Chip_availability_and_returns_true() {
        BigDecimal price = new BigDecimal(3.99);
        Chip chip = new Chip("A1", "Potato Chips", price, "Chip");
        List<Item> productList = new ArrayList<>();
        productList.add(chip);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        boolean actual = purchase.isProductAvailable("A1");
        Assert.assertTrue(actual);
    }

    @Test
    public void checks_Candy_availability_and_returns_true() {
        BigDecimal price = new BigDecimal(1.75);
        Candy candy = new Candy("B4", "Crunchie", price, "Candy");
        List<Item> productList = new ArrayList<>();
        productList.add(candy);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        boolean actual = purchase.isProductAvailable("B4");
        Assert.assertTrue(actual);
    }

    @Test
    public void checks_product_availability_with_zero_inventory_and_returns_false() {
        BigDecimal price = new BigDecimal(3.99);
        Chip chip = new Chip("A1", "Potato Chips", price, "Chip");
        chip.setQuantity(0);
        List<Item> productList = new ArrayList<>();
        productList.add(chip);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        boolean actual = purchase.isProductAvailable("A1");
        Assert.assertFalse(actual);
    }

    @Test
    public void checks_candy_availability_with_zero_inventory_and_returns_false() {
        BigDecimal price = new BigDecimal(1.50);
        Candy candy = new Candy("B2", "Cowtales", price, "Candy");
        candy.setQuantity(0);
        List<Item> productList = new ArrayList<>();
        productList.add(candy);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        boolean actual = purchase.isProductAvailable("B2");
        Assert.assertFalse(actual);
    }

    @Test
    public void checks_drink_availability_with_zero_inventory_and_returns_false() {
        BigDecimal price = new BigDecimal(1.50);
        Drink drink = new Drink("C4", "Heavy", price, "Drink");
        drink.setQuantity(0);
        List<Item> productList = new ArrayList<>();
        productList.add(drink);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        boolean actual = purchase.isProductAvailable("C4");
        Assert.assertFalse(actual);
    }

    @Test
    public void checks_gum_availability_with_zero_inventory_and_returns_false() {
        BigDecimal price = new BigDecimal(0.95);
        Gum gum = new Gum("D2", "Little League Chew", price, "Gum");
        gum.setQuantity(0);
        List<Item> productList = new ArrayList<>();
        productList.add(gum);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        boolean actual = purchase.isProductAvailable("D2");
        Assert.assertFalse(actual);
    }


    @Test
    public void feed_10_dollars_and_expect_10_dollar_balance() {
        BigDecimal feedMoney = new BigDecimal(10);
        Purchase purchase = new Purchase();
        purchase.feedMoney(feedMoney);
        BigDecimal actual = purchase.getBalance();
        BigDecimal expected = new BigDecimal(10);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void feed_2_dollars_and_expect_2_dollar_balance() {
        BigDecimal feedMoney = new BigDecimal(2);
        Purchase purchase = new Purchase();
        purchase.feedMoney(feedMoney);
        BigDecimal actual = purchase.getBalance();
        BigDecimal expected = new BigDecimal(2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void feed_5_dollars_and_expect_5_dollar_balance() {
        BigDecimal feedMoney = new BigDecimal(5);
        Purchase purchase = new Purchase();
        purchase.feedMoney(feedMoney);
        BigDecimal actual = purchase.getBalance();
        BigDecimal expected = new BigDecimal(5);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void feed_0_dollars_and_expect_0_dollar_balance() {
        BigDecimal feedMoney = new BigDecimal(0);
        Purchase purchase = new Purchase();
        purchase.feedMoney(feedMoney);
        BigDecimal actual = purchase.getBalance();
        BigDecimal expected = new BigDecimal(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void feed_4_dollars_and_expect_0_dollar_balance() {
        BigDecimal feedMoney = new BigDecimal(4);
        Purchase purchase = new Purchase();
        purchase.feedMoney(feedMoney);
        BigDecimal actual = purchase.getBalance();
        BigDecimal expected = new BigDecimal(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void feed_20_dollars_and_expect_0_dollar_balance() {
        BigDecimal feedMoney = new BigDecimal(20);
        Purchase purchase = new Purchase();
        purchase.feedMoney(feedMoney);
        BigDecimal actual = purchase.getBalance();
        BigDecimal expected = new BigDecimal(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checks_for_sufficient_balance_and_returns_false() {
        BigDecimal price = new BigDecimal(3.99);
        Chip chip = new Chip("A1", "Potato Chips", price, "Chip");
        List<Item> productList = new ArrayList<>();
        productList.add(chip);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        boolean actual = purchase.isBalanceSufficient("A1");
        Assert.assertFalse(actual);
    }

    @Test
    public void checks_for_sufficient_balance_and_returns_true() {
        BigDecimal price = new BigDecimal(3.99);
        BigDecimal feedMoney = new BigDecimal(10);
        Chip chip = new Chip("A1", "Potato Chips", price, "Chip");
        List<Item> productList = new ArrayList<>();
        productList.add(chip);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        purchase.feedMoney(feedMoney);
        boolean actual = purchase.isBalanceSufficient("A1");
        Assert.assertTrue(actual);
    }

    @Test // Will never actually occur in normal program operation
    public void attempts_purchase_with_invalid_slot_location_and_returns_true() {
        BigDecimal price = new BigDecimal(3.99);
        BigDecimal feedMoney = new BigDecimal(10);
        Chip chip = new Chip("A1", "Potato Chips", price, "Chip");
        List<Item> productList = new ArrayList<>();
        productList.add(chip);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        purchase.feedMoney(feedMoney);
        boolean actual = purchase.isBalanceSufficient("A2");
        Assert.assertTrue(actual);
    }

    @Test
    public void original_quantity_of_product_is_5_expected_method_to_return_4_in_array() {

        BigDecimal price = new BigDecimal(3.05);
        Chip chip = new Chip("A1", "Potato Crisps", price, "Chip");
        List<Item> productList = new ArrayList<>();
        productList.add(chip);
        Product product = new Product(productList);
        HashMap<String, BigDecimal> map = new HashMap<>();
        BigDecimal value = new BigDecimal(5);
        map.put("Potato Crisps", value);
        Purchase purchase = new Purchase(product);
        BigDecimal feedMoney = new BigDecimal(10);
        purchase.feedMoney(feedMoney);
        String[] actual = purchase.isDispenseSuccessful("A1");
        String[] expected = new String[]{"(A1)          Potato Crisps                  $3.04999999999999982236431605997495353221893310546875 (4 in stock)"};

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void original_quantity_of_Candy_is_5_expected_method_to_return_4_in_array() {

        BigDecimal price = new BigDecimal(1.50);
        Candy candy = new Candy("B3", "Wonka Bar", price, "Candy");
        List<Item> productList = new ArrayList<>();
        productList.add(candy);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        BigDecimal feedMoney = new BigDecimal(10);
        purchase.feedMoney(feedMoney);
        String[] actual = purchase.isDispenseSuccessful("B3");
        String[] expected = new String[]{"(B3)          Wonka Bar                      $1.5        (4 in stock)"};

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void original_quantity_of_Drink_is_5_expected_method_to_return_4_in_array() {

        BigDecimal price = new BigDecimal(1.25);
        Drink drink = new Drink("C1", "Cola", price, "Drink");
        List<Item> productList = new ArrayList<>();
        productList.add(drink);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        BigDecimal feedMoney = new BigDecimal(10);
        purchase.feedMoney(feedMoney);
        String[] actual = purchase.isDispenseSuccessful("C1");
        String[] expected = new String[]{"(C1)          Cola                           $1.25       (4 in stock)"};
        Assert.assertArrayEquals(expected, actual);
    }


    @Test
    public void feed_money_5_expect_16_quarters_1_dime_1_nickel_after_gum_purchase() {
        BigDecimal price = new BigDecimal(0.85);
        Gum gum = new Gum("D1", "U-Chews", price, "Gum");
        List<Item> productList = new ArrayList<>();
        productList.add(gum);
        Product product = new Product(productList);
        Purchase purchase = new Purchase(product);
        BigDecimal feedMoney = new BigDecimal(5);
        purchase.feedMoney(feedMoney);
        boolean balance = purchase.isBalanceSufficient("D1");
        String[] mA = purchase.isDispenseSuccessful("D1");
        String actual = purchase.giveChange();
        String expected = "Here's your change: Quarters: 16 	Dimes: 1 	Nickels: 1";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void set_total_sales_to_20_expect_20() {
        BigDecimal sales = new BigDecimal(20);
        Purchase.setTotalSales(sales);
        BigDecimal actual = Purchase.getTotalSales();
        BigDecimal expected = new BigDecimal(20);
        Assert.assertEquals(expected, actual);

    }

}
