package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.tool.EnvVariable;
import com.seshutechie.taxii2lib.tool.TaxiiContext;
import com.seshutechie.taxii2lib.util.CommonUtil;
import com.seshutechie.taxii2lib.util.ContextUtil;
import com.seshutechie.taxii2lib.util.JsonUtil;

public class NextManifestCommand extends Command {
    public static final String NAME = "next-manifest";
    private final TaxiiContext context = TaxiiContext.getContext();

    public NextManifestCommand() {
        super(null);
    }

    public NextManifestCommand(ParsedCommand parsedCommand) {
        super(parsedCommand);
    }

    @Override
    public void execute() throws TaxiiAppException {
        if(parsedCommand.getArgumentCount() == 0) {
            int pageSize = ContextUtil.getIntValue(parsedCommand, EnvVariable.PAGE_SIZE.name, -1);
            String manifest = context.getTaxiiLib().getNextManifest(pageSize);
            System.out.println(JsonUtil.prettyJson(manifest));
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
