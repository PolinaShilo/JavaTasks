package com.phonebook.command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final Map<String, Command> commands = new HashMap<>();
    static {
        commands.put("register", new RegisterCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
    }
    public static Command get(String action) {
        Command cmd = commands.get(action);
        if (cmd == null) throw new IllegalArgumentException("Unknown command: " + action);
        return cmd;
    }
}