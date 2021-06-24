package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.tool.EnvVariable;
import com.seshutechie.taxii2lib.tool.TaxiiContext;
import com.seshutechie.taxii2lib.util.CommonUtil;
import com.seshutechie.taxii2lib.util.ContextUtil;
import com.seshutechie.taxii2lib.util.JsonUtil;

public class ManifestCommand extends Command {
    public static final String NAME = "manifest";
    private final TaxiiContext context = TaxiiContext.getContext();

    public ManifestCommand() {
        super(null);
    }

    public ManifestCommand(ParsedCommand parsedCommand) {
        super(parsedCommand);
    }

    @Override
    public void execute() throws TaxiiAppException {
        if(parsedCommand.getArgumentCount() == 0) {
            String apiRoot = ContextUtil.getParamValue(parsedCommand, EnvVariable.API_ROOT.name);
            String connectionId = ContextUtil.getParamValue(parsedCommand, EnvVariable.COLLECTION_ID.name);
            int from = ContextUtil.getIntValue(parsedCommand, EnvVariable.FROM.name, -1);
            int pageSize = ContextUtil.getIntValue(parsedCommand, EnvVariable.PAGE_SIZE.name, -1);
            String manifest = context.getTaxiiLib().getManifest(apiRoot, connectionId, from, pageSize);
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
        return "manifest [--api-root <api-root-url>] [--collection-id <collection-id>] [--from <start-from>] [--page-size <size>]";
    }
}
