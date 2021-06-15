package com.seshutechie.taxii2lib.tool;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.TaxiiLib;
import com.seshutechie.taxii2lib.tool.commands.Command;
import com.seshutechie.taxii2lib.tool.commands.CommandFactory;

import java.util.Scanner;

public class TaxiiTool {
    private static final Object TAXII_PROMPT = "TAXII-2>";
    private static final TaxiiLib taxiiLib = new TaxiiLib();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            TaxiiContext context = TaxiiContext.getContext();
            context.setTaxiiLib(taxiiLib);

            while (true) {
                showPrompt();
                String line = scanner.nextLine();
                processCommandLine(line);
            }
        } finally {
            scanner.close();
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
