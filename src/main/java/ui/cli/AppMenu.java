package ui.cli;

import java.util.Scanner;

class AppMenu {

    public void start() {
        System.out.println("Enter Command(available commands [book-ticket]):  ");
        try (Scanner scanner = new Scanner(System.in)) {
            CommandHandlerFactory.commandHandler(scanner.nextLine())
                                 .ifPresentOrElse(CommandHandler::handle, () -> System.err.println("Invalid Command"));
        }
    }
}
