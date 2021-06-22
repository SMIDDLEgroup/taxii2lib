package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiLib;
import com.seshutechie.taxii2lib.tool.ContextConstants;
import com.seshutechie.taxii2lib.tool.EnvVariable;
import com.seshutechie.taxii2lib.tool.TaxiiContext;

public class ShowCommand extends Command {
    public static final String NAME = "show";

    private final TaxiiContext context = TaxiiContext.getContext();

    public ShowCommand() {
        super(null);
    }

    @Override
    public void execute() {
        if(parsedCommand.getArgumentCount() == 1) {
            String name = parsedCommand.getArgument(0);
            if("all".equals(name)) {
                for(String key: context.getKeys()) {
                    displayValue(key);
                }
            } else {
                EnvVariable envVariable = EnvVariable.getInstance(name);
                if(envVariable != null) {
                    displayValue(envVariable.name);
                } else {
                    System.out.println("Invalid variable: " + name);
                }
            }
        } else {
            System.out.printf("Invalid arguments: %s\nUsage: %s\n",
                    this.parsedCommand.getLine(), getUsage());
        }
    }

    private void displayValue(String key) {
        System.out.printf("%s: %s\n", key, context.getValue(key));
    }

    @Override
    public String getUsage(){
        return "show [all|<name>]";
    }
}
