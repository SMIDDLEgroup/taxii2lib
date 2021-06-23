package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.tool.EnvVariable;
import com.seshutechie.taxii2lib.tool.TaxiiContext;
import com.seshutechie.taxii2lib.util.CommonUtil;
import com.seshutechie.taxii2lib.util.ContextUtil;
import com.seshutechie.taxii2lib.util.JsonUtil;

public class ObjectsCommand extends Command {
    public static final String NAME = "objects";
    private final TaxiiContext context = TaxiiContext.getContext();

    public ObjectsCommand() {
        super(null);
    }

    public ObjectsCommand(ParsedCommand parsedCommand) {
        super(parsedCommand);
    }

    @Override
    public void execute() throws TaxiiAppException {
        if(parsedCommand.getArgumentCount() == 0) {
            String apiRoot = ContextUtil.getParamValue(parsedCommand, EnvVariable.API_ROOT.name);
            String connectionId = ContextUtil.getParamValue(parsedCommand, EnvVariable.COLLECTION_ID.name);
            int from = ContextUtil.getIntValue(parsedCommand, EnvVariable.FROM.name, -1);
            int pageSize = ContextUtil.getIntValue(parsedCommand, EnvVariable.PAGE_SIZE.name, -1);
            String collectionDetails = context.getTaxiiLib().getObjects(apiRoot, connectionId, from, pageSize);
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
        return "objects [--api-root <api-root-url>] [--collection-id <collection-id>] [--from <start-from>] [--page-size <size>]";
    }
}
