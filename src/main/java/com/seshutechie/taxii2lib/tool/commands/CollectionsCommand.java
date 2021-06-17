package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.tool.TaxiiContext;
import com.seshutechie.taxii2lib.util.JsonUtil;

public class CollectionsCommand extends Command {
    public static final String NAME = "collections";
    private final TaxiiContext context = TaxiiContext.getContext();

    public CollectionsCommand() {
        super(null);
    }

    public CollectionsCommand(ParsedCommand parsedCommand) {
        super(parsedCommand);
    }

    @Override
    public void execute() throws TaxiiAppException {
        if(parsedCommand.getArgumentCount() == 1) {
            String discovery = context.getTaxiiLib().getCollections(parsedCommand.getArgument(0));
            System.out.println(JsonUtil.prettyJson(discovery));
        } else {
            System.out.printf("Invalid arguments: %s.\nUsage: %s\n",
                    this.parsedCommand.getLine(), getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "collections <api-root-url>";
    }
}
