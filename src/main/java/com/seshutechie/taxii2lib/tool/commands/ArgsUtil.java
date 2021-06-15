package com.seshutechie.taxii2lib.tool.commands;

import com.seshutechie.taxii2lib.tool.TaxiiContext;

public class ArgsUtil {
    private static TaxiiContext context = TaxiiContext.getContext();

    public static String getArgument(ParsedCommand parsedCommand, String name) {
        String value = parsedCommand.getValue(name);
        if(value == null) {
            Object obj = context.getValue(name);
            if(obj != null) {
                value = obj.toString();
            }
        }
        return value;
    }
}
