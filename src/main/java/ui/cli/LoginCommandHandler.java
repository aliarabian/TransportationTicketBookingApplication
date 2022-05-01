package ui.cli;

import com.platform.ResponseEntity;
import com.platform.business.service.auth.AuthenticationRequest;
import com.platform.business.service.auth.CustomerAuthenticationService;
import com.platform.business.service.auth.LoginController;
import com.platform.repository.customer.InMemoryCustomerDao;

import java.io.Console;
import java.io.DataInputStream;
import java.util.Scanner;

public class LoginCommandHandler implements CommandHandler {
    private final LoginController loginController;
    private final AppMenu appMenu;

    public LoginCommandHandler() {
        loginController = new LoginController(new CustomerAuthenticationService(new InMemoryCustomerDao()));
        appMenu = new AppMenu();
    }

    @Override
    public void handle() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            Console console = System.console();
            String password = new String(console.readPassword());
            ResponseEntity<?> loginResponse = loginController.login(new AuthenticationRequest(username, password));
            if (loginResponse.isError()) {
                System.err.println(loginResponse.getData().toString());
                return;
            }

            System.out.println("Logged In");
            appMenu.start();
        }
    }
}
