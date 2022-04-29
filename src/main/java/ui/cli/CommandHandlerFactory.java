package ui.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandHandlerFactory {
    private static final Map<String, CommandHandler> commandHandlerMap;

    static {
        commandHandlerMap = new HashMap<>();
        commandHandlerMap.put("--book-ticket", new BookingCommandHandler());
    }

    public static Optional<CommandHandler> commandHandler(String commandId) {
        return Optional.ofNullable(commandHandlerMap.get(commandId));
    }
}
