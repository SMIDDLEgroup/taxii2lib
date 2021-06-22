package com.seshutechie.taxii2lib.tool.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final Map<String, Command> commandsMap = new HashMap<>();
    private static final Command unknownCommand = new UnknownCommand(null);

    static {
        commandsMap.put(ExitCommand.NAME, new ExitCommand(null));
        commandsMap.put(SetCommand.NAME, new SetCommand(null));
        commandsMap.put(DiscoveryCommand.NAME, new DiscoveryCommand(null));
        commandsMap.put(CollectionsCommand.NAME, new CollectionsCommand());
        commandsMap.put(HelpCommand.NAME, new HelpCommand(null, commandsMap));
        commandsMap.put(ShowCommand.NAME, new ShowCommand());
        commandsMap.put(CollectionDetailsCommand.NAME, new CollectionDetailsCommand());
    }

    public static Command createCommand(String line) {
        ParsedCommand parsedCommand = parseCommand(line);
        Command command = commandsMap.get(parsedCommand.getName());
        if(command == null) {
            command = unknownCommand;
        }
        command.setParsedCommand(parsedCommand);
        return command;
    }

    private static ParsedCommand parseCommand(String line) {
        return CommandParser.parse(line);
    }
}
