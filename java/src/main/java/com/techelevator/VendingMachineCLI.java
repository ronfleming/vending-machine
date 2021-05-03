package com.techelevator;

import com.techelevator.utilities.VendingMachineLog;
import com.techelevator.view.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_OPTION_SALES_REPORT = "Sales Report";

    private static final String[] MAIN_MENU_OPTIONS1 = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String[] MAIN_MENU_OPTIONS2 = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};

    private static String PRODUCT_1 = "Chip";
    private static String PRODUCT_2 = "Candy";
    private static String PRODUCT_3 = "Drink";
    private static String PRODUCT_4 = "Gum";

    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH = "Finish";

    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH};

    static List<Item> productList = new ArrayList<>();
    static Product product = new Product(productList);
    Purchase purchase = new Purchase(product);
    private String[] itemsForPurchase;
    public static Map<String, BigDecimal> salesMap = new HashMap<>();
    static String sourceForSalesReport = "java/src/main/java/com/techelevator/utilities/SalesReport.txt";
    static File salesReportSourceFile = new File(sourceForSalesReport);

    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a");
    String formattedDate = myDateObj.format(myFormatObj);

    private static boolean userAuthentic = false;


    private Menu menu;

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }


    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        loadFile();
        userAuthentic = cli.isUserAuthentic();
        cli.itemsForPurchase = productToString(product);
        cli.run();
    }

    public void run() {
        boolean shouldLoop = true;
        while (shouldLoop) {
            String choice;
            if (userAuthentic) {
                choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS2);
            } else {
                choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS1);
            }
            switch (choice) {
                case MAIN_MENU_OPTION_DISPLAY_ITEMS: {

                    String[] itemsForPurchase = productToString(product);
                    for (int i = 0; i < itemsForPurchase.length; i++) {
                        System.out.println(itemsForPurchase[i]);
                    }

                    break;
                }
                case MAIN_MENU_OPTION_PURCHASE: {
                    //Call menu classes getChoiceFromOptions by passing in Purchase menu string array

                    boolean shouldLoopForPurchase = true;
                    while (shouldLoopForPurchase) {
                        String choiceForPurchaseMenu = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

                        switch (choiceForPurchaseMenu) {
                            case PURCHASE_MENU_OPTION_FEED_MONEY: {
                                System.out.println("\nCurrent money provided: $" + purchase.getBalance());
                                System.out.println("Please enter one of the following dollar amounts: 1, 2, 5, 10");
                                Scanner input = new Scanner(System.in); //We need to close this resource
                                String inputLine = input.nextLine();

                                String[] decimal = inputLine.split("\\.");
                                if(decimal.length > 1){
                                    System.out.println("\nPlease enter a valid denomination");
                                }
                                else{
                                    try{int fM = Integer.parseInt(decimal[0]);
                                        BigDecimal bigFeedMoney = new BigDecimal(fM);
                                        purchase.feedMoney(bigFeedMoney);
                                        System.out.println("\nCurrent money provided: $" + purchase.getBalance());
                                        VendingMachineLog.log(formattedDate + " FEED MONEY: " + "$" + bigFeedMoney + " $" + purchase.getBalance());
                                    }catch(NumberFormatException e){
                                        System.out.println("\nPlease enter a valid denomination");
                                    }
                                }
                                break;
                            }

                            case PURCHASE_MENU_OPTION_SELECT_PRODUCT: {
                                String choiceOfProduct = (String) menu.getChoiceFromOptions(itemsForPurchase);

                                //Call a Purchase class method to check the availability of the product
                                boolean productAvailability = purchase.isProductAvailable(choiceOfProduct);
                                if (!productAvailability) {
                                    System.out.println("Product is out of stock!");
                                    break;
                                }

                                //Call to isBalanceSufficient() of Purchase class
                                boolean isBalanceSufficient = purchase.isBalanceSufficient(choiceOfProduct);

                                if (isBalanceSufficient) {
                                    String[] modifiedProductList = purchase.isDispenseSuccessful(choiceOfProduct);
                                    itemsForPurchase = modifiedProductList;
                                } else {
                                    System.out.println("Balance is insufficient!");
                                }
                                break;
                            }

                            case PURCHASE_MENU_OPTION_FINISH: {
                                String changeToBeReturned = purchase.giveChange();
                                System.out.println(changeToBeReturned);
                                BigDecimal newTotalSales = VendingMachineCLI.salesMap.get("Total Sales").add(Purchase.getTotalSales());
                                VendingMachineCLI.salesMap.put("Total Sales", newTotalSales);
                                Purchase.setTotalSales(BigDecimal.ZERO);

                                shouldLoopForPurchase = false;
                                break;
                            }


                        }//end of switch
                    }//end of while loop for purchase
                    break;
                }//end case for purchase
                case MAIN_MENU_OPTION_EXIT: {
                    try (PrintWriter salesReportWriter = new PrintWriter(salesReportSourceFile)) {
                        String reportHolder = "";
                        for (Map.Entry<String, BigDecimal> sale : salesMap.entrySet()) {
                            reportHolder += sale.getKey() + "|" + sale.getValue() + "\n";
                        }
                        reportHolder = reportHolder.substring(0, reportHolder.length());
                        salesReportWriter.print(reportHolder);
                    } catch (FileNotFoundException e) {
                        System.out.println("Sales report not found!");
                    }
                    shouldLoop = false;
                    break;
                }
                case MAIN_MENU_OPTION_SALES_REPORT: {
                    for (Map.Entry<String, BigDecimal> sale : salesMap.entrySet()) {
                        if (!sale.getKey().equals("Total Sales")) {
                            System.out.println(sale.getKey() + "|" + sale.getValue());
                        }
                    }
                    System.out.println("Total sales: $" + salesMap.get("Total Sales"));
                }
            }//end of switch for main menu display
        }//end of while loop for main menu
    }//end of run method

    public static void loadFile() {
        String sourceForProducts = "java/vendingmachine.csv"; //Need to remove hard code
        File productSourceFile = new File(sourceForProducts);

        try (Scanner streamToReadSalesReport = new Scanner(salesReportSourceFile)) {
            while (streamToReadSalesReport.hasNext()) {
                String line = streamToReadSalesReport.nextLine();
                String[] salesItem = line.split("\\|");
                BigDecimal number = new BigDecimal(salesItem[1]);
                salesMap.put(salesItem[0], number);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sales report not found!");
        }

        try (Scanner streamToReadFile = new Scanner(productSourceFile)) {
            while (streamToReadFile.hasNext()) {
                String line = streamToReadFile.nextLine();
                if (line.toLowerCase().contains(PRODUCT_1.toLowerCase())) {
                    String[] productDetails = line.split("\\|");
                    BigDecimal bigPrice1 = new BigDecimal(productDetails[2]);
                    Chip chip = new Chip(productDetails[0], productDetails[1], bigPrice1, productDetails[3]);
                    productList.add(chip);
                } else if (line.toLowerCase().contains(PRODUCT_2.toLowerCase())) {
                    String[] productDetails = line.split("\\|");
                    BigDecimal bigPrice1 = new BigDecimal(productDetails[2]);
                    Candy candy = new Candy(productDetails[0], productDetails[1], bigPrice1, productDetails[3]);
                    productList.add(candy);
                } else if (line.toLowerCase().contains(PRODUCT_3.toLowerCase())) {
                    String[] productDetails = line.split("\\|");
                    BigDecimal bigPrice1 = new BigDecimal(productDetails[2]);
                    Drink drink = new Drink(productDetails[0], productDetails[1], bigPrice1, productDetails[3]);
                    productList.add(drink);
                } else if (line.toLowerCase().contains(PRODUCT_4.toLowerCase())) {
                    String[] productDetails = line.split("\\|");
                    BigDecimal bigPrice1 = new BigDecimal(productDetails[2]);
                    Gum gum = new Gum(productDetails[0], productDetails[1], bigPrice1, productDetails[3]);
                    productList.add(gum);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Product source not found");
        }
    }

    public static String[] productToString(Product product) {
        String[] itemsForPurchase = new String[product.getProductList().size()];
        for (int i = 0; i < product.getProductList().size(); i++) {
            itemsForPurchase[i] = product.getProductList().get(i).toString();
        }
        return itemsForPurchase;
    }

    public boolean isUserAuthentic() {
        boolean authenticUser = false;
        String username, password;
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to UmbrellaCorp Vending System!");
        System.out.print("Enter username:");//username:user
        username = s.nextLine();
        System.out.print("Enter password:");//password:user
        password = s.nextLine();
        if (username.equals("user") && password.equals("user")) {
            authenticUser = true;
            System.out.println("Authentication Successful");
        } else {
            System.out.println("Authentication Failed");
        }
        return authenticUser;
    }
}
