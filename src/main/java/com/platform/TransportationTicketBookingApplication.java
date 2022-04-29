package com.platform;

import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;
import ui.cli.CommandHandler;
import ui.cli.CommandHandlerFactory;

import static java.lang.System.*;

public class TransportationTicketBookingApplication {
    public static void main(String[] args) {
        out.println(TransportationBookingSystemImMemoryDataSource.getTickets().count());
        if (args.length == 0) {
            err.println("No Commands Provided!");
            exit(-1);
        }
        CommandHandlerFactory.commandHandler(args[0])
                             .ifPresentOrElse(CommandHandler::handle, () -> err.println("Command Not Supported"));

        out.println(TransportationBookingSystemImMemoryDataSource.getTickets().count());
        TransportationBookingSystemImMemoryDataSource.save();
    }
}

