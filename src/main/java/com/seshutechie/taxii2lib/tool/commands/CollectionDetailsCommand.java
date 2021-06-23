package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.tool.EnvVariable;
import com.seshutechie.taxii2lib.tool.TaxiiContext;
import com.seshutechie.taxii2lib.util.ContextUtil;
import com.seshutechie.taxii2lib.util.JsonUtil;

public class CollectionDetailsCommand extends Command {
    public static final String NAME = "collection-details";
    private final TaxiiContext context = TaxiiContext.getContext();

    public CollectionDetailsCommand() {
        super(null);
    }

    public CollectionDetailsCommand(ParsedCommand parsedCommand) {
        super(parsedCommand);
    }

    @Override
    public void execute() throws TaxiiAppException {
        if(parsedCommand.getArgumentCount() >= 1) {
            String apiRoot = ContextUtil.getParamValue(parsedCommand, EnvVariable.API_ROOT.name);
            for(int i = 0; i < parsedCommand.getArgumentCount(); i++) {
                String collectionDetails = context.getTaxiiLib().getCollectionDetails(apiRoot, parsedCommand.getArgument(i));
                System.out.println(JsonUtil.prettyJson(collectionDetails));
            }
        } else {
            System.out.printf("Invalid arguments: %s.\nUsage: %s\n",
                    this.parsedCommand.getLine(), getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "collection-details [--api-root <api-root-url>] <collection-id> [<collection-id-2>...]";
    }
}
