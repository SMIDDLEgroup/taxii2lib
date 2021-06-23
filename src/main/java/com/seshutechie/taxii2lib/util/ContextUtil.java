package com.seshutechie.taxii2lib.util;

import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.tool.TaxiiContext;
import com.seshutechie.taxii2lib.tool.commands.ParsedCommand;

public class ContextUtil {
    private static final TaxiiContext context = TaxiiContext.getContext();

    public static String getParamValue(ParsedCommand parsedCommand, String name) throws TaxiiAppException {
        String value = parsedCommand.getValue(name);
        if(value == null && context.hasValue(name)) {
            value = context.getValue(name).toString();
        }
        if(value == null || value.trim().isEmpty()) {
            throw new TaxiiAppException(name + " is not specified");
        }
        return value;
    }

    public static int getIntValue(ParsedCommand parsedCommand, String name, int defaultValue) {
        int value = defaultValue;
        String strValue = parsedCommand.getValue(name);
        if(strValue != null) {
            try {
                value = Integer.parseInt(strValue);
            } catch (NumberFormatException e) {
                value = defaultValue;
            }
        } else if(context.hasValue(name)) {
            value = (int) context.getValue(name);
        }
        return value;
    }
}
