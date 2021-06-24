package com.seshutechie.taxii2lib.tool.commands;

import java.util.Map;

public class HelpCommand extends Command {
    public static final String NAME = "help";
    private final Map<String, Command> commandMap;

    public HelpCommand(Map<String, Command> commandMap) {
        super(null);
        this.commandMap = commandMap;
    }

    @Override
    public void execute() {
        if (parsedCommand.getArgumentCount() == 0) {
            for(String name: commandMap.keySet()) {
                System.out.printf("%s\n", commandMap.get(name).getUsage());
            }
        } else if (parsedCommand.getArgumentCount() == 1) {
            Command command = commandMap.get(parsedCommand.getArgument(0));
            if(command != null) {
                System.out.println(command.getUsage());
                // TODO: add more help
                System.out.println("Details: TBD");
            } else {
                System.out.printf("Unknown command: %s\n", parsedCommand.getArgument(0));
            }
        } else {
            System.out.println("Unsupported arguments");
        }
    }

    @Override
    public String getUsage() {
        return "help [command]";
    }
}
