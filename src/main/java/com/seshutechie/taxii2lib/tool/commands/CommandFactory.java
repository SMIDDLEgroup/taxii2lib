package com.seshutechie.taxii2lib.tool.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final Map<String, Command> commandsMap = new HashMap<>();
    private static final Command unknownCommand = new UnknownCommand();

    static {
        commandsMap.put(ExitCommand.NAME, new ExitCommand());
        commandsMap.put(SetCommand.NAME, new SetCommand());
        commandsMap.put(DiscoveryCommand.NAME, new DiscoveryCommand());
        commandsMap.put(CollectionsCommand.NAME, new CollectionsCommand());
        commandsMap.put(HelpCommand.NAME, new HelpCommand(commandsMap));
        commandsMap.put(ShowCommand.NAME, new ShowCommand());
        commandsMap.put(CollectionDetailsCommand.NAME, new CollectionDetailsCommand());
        commandsMap.put(ObjectsCommand.NAME, new ObjectsCommand());
        commandsMap.put(NextObjectsCommand.NAME, new NextObjectsCommand());
        commandsMap.put(ObjectCommand.NAME, new ObjectCommand());
        commandsMap.put(ManifestCommand.NAME, new ManifestCommand());
        commandsMap.put(NextManifestCommand.NAME, new NextManifestCommand());
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
