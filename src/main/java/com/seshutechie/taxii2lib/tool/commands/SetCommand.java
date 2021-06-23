package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.TaxiiLib;
import com.seshutechie.taxii2lib.tool.ContextConstants;
import com.seshutechie.taxii2lib.tool.EnvVariable;
import com.seshutechie.taxii2lib.tool.TaxiiContext;

public class SetCommand extends Command {
    public static final String NAME = "set";

    private final TaxiiContext context = TaxiiContext.getContext();

    public SetCommand(ParsedCommand parsedCommand) {
        super(parsedCommand);
    }

    @Override
    public void execute() throws TaxiiAppException {
        if(parsedCommand.getArgumentCount() == 2) {
            String name = parsedCommand.getArgument(0);
            String value = parsedCommand.getArgument(1);

            EnvVariable envVariable = EnvVariable.getInstance(name);
            if(envVariable != null) {
                context.addValue(name, value);

                switch (envVariable) {
                    case PASSWORD:
                        Object user = context.getValue(EnvVariable.USER.name);
                        TaxiiLib taxiiLib = context.getTaxiiLib();
                        if (taxiiLib != null && user != null) {
                            taxiiLib.setBasicAuthorization(user.toString(), value);
                        }
                        break;
                    case PAGE_SIZE:
                        try {
                            context.addValue(name, Integer.parseInt(value));
                        } catch (NumberFormatException e) {
                            throw new TaxiiAppException("Invalid page size: " + value);
                        }
                }
            }

        } else {
            System.out.printf("Invalid arguments: %s.\nUsage: %s\n",
                    this.parsedCommand.getLine(), getUsage());
        }
    }

    @Override
    public String getUsage(){
        return "set <name> <value>";
    }
}
