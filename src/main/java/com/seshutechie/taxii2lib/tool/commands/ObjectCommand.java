package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.tool.EnvVariable;
import com.seshutechie.taxii2lib.tool.TaxiiContext;
import com.seshutechie.taxii2lib.util.CommonUtil;
import com.seshutechie.taxii2lib.util.ContextUtil;
import com.seshutechie.taxii2lib.util.JsonUtil;

public class ObjectCommand extends Command {
    public static final String NAME = "object";
    private final TaxiiContext context = TaxiiContext.getContext();

    public ObjectCommand() {
        super(null);
    }

    public ObjectCommand(ParsedCommand parsedCommand) {
        super(parsedCommand);
    }

    @Override
    public void execute() throws TaxiiAppException {
        if(parsedCommand.getArgumentCount() == 1) {
            String apiRoot = ContextUtil.getParamValue(parsedCommand, EnvVariable.API_ROOT.name);
            String connectionId = ContextUtil.getParamValue(parsedCommand, EnvVariable.COLLECTION_ID.name);
            String object = context.getTaxiiLib().getObjectById(apiRoot, connectionId, parsedCommand.getArgument(0));
            System.out.println(JsonUtil.prettyJson(object));
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
        return "object [--api-root <api-root-url>] [--collection-id <collection-id>] <object-id>";
    }
}
