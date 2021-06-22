package com.seshutechie.taxii2lib.tool;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.TaxiiLib;
import com.seshutechie.taxii2lib.tool.commands.Command;
import com.seshutechie.taxii2lib.tool.commands.CommandFactory;

import java.util.Scanner;

public class TaxiiTool {
    private static final Object TAXII_PROMPT = "TAXII-2>";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            StartupHandler.setDefaultContext();

            while (true) {
                try {
                    showPrompt();
                    String line = scanner.nextLine();
                    processCommandLine(line);
                } catch (Exception ex) {
                    System.out.printf("Unable to handle. Got Error: %s", ex.getMessage());
                }
            }
        }
    }

    private static void showPrompt() {
        System.out.printf("\n%s", TAXII_PROMPT);
    }

    private static void processCommandLine(String line) {
        if(!line.trim().isEmpty()) {
            Command command = CommandFactory.createCommand(line);
            try {
                command.execute();
            } catch (TaxiiAppException e) {
                System.out.printf("Error in running command: %s\n", e.getMessage());
            }
        }
    }
}
