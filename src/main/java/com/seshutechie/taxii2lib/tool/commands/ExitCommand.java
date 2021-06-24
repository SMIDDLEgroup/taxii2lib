package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;

public class ExitCommand extends Command {
    public static final String NAME = "exit";

    public ExitCommand() {
        super(null);
    }

    @Override
    public void execute() {
        if(parsedCommand.getArgumentCount() == 0) {
            System.exit(0);
        } else {
            System.out.println("Unsupported arguments");
        }
    }

    @Override
    public String getUsage() {
        return NAME;
    }
}
