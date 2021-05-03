package com.techelevator.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class VendingMachineLog {

    private static PrintWriter writer = null;

    public VendingMachineLog() {

    }

    public static void log(String message) {
        String logsFolderAtRoot = "java/src/main/java/com/techelevator/utilities/log.txt"; //We need to remove this hard code from here!
        File logFile = new File(logsFolderAtRoot);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (writer == null) {
                writer = new PrintWriter(new FileWriter(logFile, true));
            }

            writer.println(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
