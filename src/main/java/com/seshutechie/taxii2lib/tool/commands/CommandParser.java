package com.seshutechie.taxii2lib.tool.commands;

import java.util.StringTokenizer;

public class CommandParser {

    private static final String FLAG_PREFIX = "-";
    private static final String KEY_PREFIX = "--";

    public static ParsedCommand parse(String line) {
        ParsedCommand parsedCommand = null;
        if(line != null) {
            line = line.trim();
            StringTokenizer stringTokenizer = new StringTokenizer(line);
            if (stringTokenizer.hasMoreTokens()) {
                parsedCommand = new ParsedCommand(line, stringTokenizer.nextToken());
                while (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken();
                    if(token.startsWith(KEY_PREFIX)) {
                        String value = null;
                        if(stringTokenizer.hasMoreTokens()) {
                            value = stringTokenizer.nextToken();
                        }
                        parsedCommand.putKeyValue(token.substring(KEY_PREFIX.length()), value);
                    } else if (token.startsWith(FLAG_PREFIX)){
                        parsedCommand.setFlag(token.substring(FLAG_PREFIX.length()));
                    } else {
                        parsedCommand.addArgument(token);
                    }
                }
            } else {
                parsedCommand = new ParsedCommand(null);
            }
        } else {
            parsedCommand = new ParsedCommand(null);
        }
        return parsedCommand;
    }
}
