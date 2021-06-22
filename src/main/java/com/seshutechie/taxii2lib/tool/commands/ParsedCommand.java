package com.seshutechie.taxii2lib.tool.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsedCommand {
    private static final int INVALID_INT = Integer.MIN_VALUE;

    private String line;
    private String name;
    private List<String> arguments;
    private Map<String, String> argMap;

    public ParsedCommand(String line) {
        this.line = line;
        arguments = new ArrayList<>();
        argMap = new HashMap<>();
    }

    public ParsedCommand(String line, String name) {
        this(line);
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public String getName() {
        return name;
    }

    public void addArgument(String arg) {
        arguments.add(arg);
    }

    public void putKeyValue(String name, String value) {
        argMap.put(name, value);
    }

    public int getIntValue(String name) {
        int value = INVALID_INT;
        String str = argMap.get(name);
        if(str != null) {
            try {
                value = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                value = INVALID_INT;
            }
        }
        return value;
    }

    public boolean isFlagExists(String flag) {
        return argMap.keySet().contains(flag);
    }

    public void setFlag(String flag) {
        argMap.put(flag, null);
    }

    public String getValue(String name) {
        return argMap.get(name);
    }

    public int getArgumentCount() {
        return arguments.size();
    }

    public int getAdditionalArgumentCount() {
        return argMap.size();
    }

    public String getArgument(int index) {
        String value = null;

        if (index < arguments.size()) {
            value = arguments.get(index);
        }

        return value;
    }
}
