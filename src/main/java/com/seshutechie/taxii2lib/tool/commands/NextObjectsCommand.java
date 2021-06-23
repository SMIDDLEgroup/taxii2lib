package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.tool.EnvVariable;
import com.seshutechie.taxii2lib.tool.TaxiiContext;
import com.seshutechie.taxii2lib.util.CommonUtil;
import com.seshutechie.taxii2lib.util.ContextUtil;
import com.seshutechie.taxii2lib.util.JsonUtil;

public class NextObjectsCommand extends Command {
    public static final String NAME = "next-objects";
    private final TaxiiContext context = TaxiiContext.getContext();

    public NextObjectsCommand() {
        super(null);
    }

    public NextObjectsCommand(ParsedCommand parsedCommand) {
        super(parsedCommand);
    }

    @Override
    public void execute() throws TaxiiAppException {
        if(parsedCommand.getArgumentCount() == 0) {
            int pageSize = ContextUtil.getIntValue(parsedCommand, EnvVariable.PAGE_SIZE.name, -1);
            String collectionDetails = context.getTaxiiLib().getNextObjects(pageSize);
            System.out.println(JsonUtil.prettyJson(collectionDetails));
            String status = CommonUtil.getLastPageStatus(context.getTaxiiLib().getLastPage());
            if(status != null) {
                System.out.println(status);
            }
        } else {
            System.out.printf("Invalid arguments: %s.\nUsage: %s\n",
                    this.parsedCommand.getLine(), getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "next-objects [--page-size <size>]";
    }
}
