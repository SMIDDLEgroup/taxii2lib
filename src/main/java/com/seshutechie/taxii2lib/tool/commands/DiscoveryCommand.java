package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.TaxiiLib;
import com.seshutechie.taxii2lib.tool.ContextConstants;
import com.seshutechie.taxii2lib.tool.TaxiiContext;
import com.seshutechie.taxii2lib.util.JsonUtil;

public class DiscoveryCommand extends Command {
    public static final String NAME = "discovery";
    private final TaxiiContext context = TaxiiContext.getContext();

    public DiscoveryCommand() {
        super(null);
    }

    @Override
    public void execute() throws TaxiiAppException {
        if(parsedCommand.getArgumentCount() == 1) {
            String discovery = context.getTaxiiLib().getDiscovery(parsedCommand.getArgument(0));
            System.out.println(JsonUtil.prettyJson(discovery));
        } else {
            System.out.printf("Invalid arguments: %s.\nUsage: %s\n",
                    this.parsedCommand.getLine(), getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "discovery <discovery-root-url>";
    }
}
