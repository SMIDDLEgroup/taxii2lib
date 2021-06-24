package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;

public class UnknownCommand extends Command {

    public UnknownCommand() {
        super(null);
    }

    @Override
    public void execute() {
        System.out.printf("Unknown command: %s\n", parsedCommand.getLine());
    }

    @Override
    public String getUsage() {
        return null;
    }
}
