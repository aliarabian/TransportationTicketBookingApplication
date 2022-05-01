package com.platform;

import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;
import ui.cli.CommandHandler;
import ui.cli.CommandHandlerFactory;

import static java.lang.System.err;
import static java.lang.System.exit;

public class TransportationTicketBookingApplication {
    public static void main(String[] args) {
        CommandHandlerFactory.commandHandler("login")
                             .ifPresentOrElse(CommandHandler::handle, () -> err.println("Command Not Supported"));

        TransportationBookingSystemImMemoryDataSource.save();
    }
}

