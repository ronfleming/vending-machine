package com.techelevator.view;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

    private PrintWriter out;
    private Scanner in;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = "";
        userInput = in.nextLine();

        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            for (int i = 0; i < options.length; i++) {
                String itemDetails = options[i].toString().substring(1, 3);
                if (itemDetails.equalsIgnoreCase(userInput)) {
                    choice = userInput;
                    break;
                }
            }
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    private void displayMenuOptions(Object[] options) {

        out.println();
        if (options.length < 5) {
            for (int i = 0; i < options.length; i++) {
                int optionNum = i + 1;
                out.println(optionNum + ") " + options[i]);
            }

        } else {
            for (int i = 0; i < options.length; i++) {
                out.println(options[i]);
            }
        }

        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }
}
