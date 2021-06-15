package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;

public abstract class Command {
    protected ParsedCommand parsedCommand;

    public Command(ParsedCommand parsedCommand) {
        if(parsedCommand != null) {
            this.parsedCommand = parsedCommand;
        } else {
            this.parsedCommand = new ParsedCommand(null);
        }
    }

    public void setParsedCommand(ParsedCommand parsedCommand) {
        this.parsedCommand = parsedCommand;
    }

    public abstract void execute() throws TaxiiAppException;
    public abstract String getUsage();
}
