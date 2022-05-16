package ui.servlet.auth;

import com.platform.ResponseEntity;
import com.platform.business.service.auth.AuthenticationResponse;
import com.platform.business.service.auth.AuthenticationController;
import com.platform.business.service.auth.AuthenticationRequest;
import com.platform.business.service.auth.CustomerAuthenticationService;
import com.platform.repository.customer.InMemoryCustomerDao;
import com.platform.security.CsrfTokenGenerator;
import com.platform.security.SecureCsrfTokenGenerator;
import ui.servlet.frontcontroller.Handler;
import ui.servlet.frontcontroller.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginHandler extends Handler {
    private final AuthenticationController authenticationController;
    private final CsrfTokenGenerator csrfTokenGenerator;
    private static final int SESSION_TIMEOUT = 60 * 30;

    public LoginHandler(HttpServletRequest httpRequest, HttpServletResponse httpResponse, RequestMapping requestMapping) {
        super(httpRequest, httpResponse, requestMapping);
        this.authenticationController = new AuthenticationController(new CustomerAuthenticationService(new InMemoryCustomerDao()));
        csrfTokenGenerator = new SecureCsrfTokenGenerator();
    }

    @Override
    public String process() throws ServletException, IOException {

        String username = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(username, password);
        ResponseEntity<?> authResponse = authenticationController.login(authenticationRequest);
        if (!authResponse.isError()) {
            HttpSession session = httpRequest.getSession();
            httpRequest.changeSessionId();
            session.setAttribute("auth-token", ((AuthenticationResponse) authResponse.getData()).getToken());
            session.setAttribute("userId", ((AuthenticationResponse) authResponse.getData()).getId());
            session.setAttribute("username", ((AuthenticationResponse) authResponse.getData()).getUsername());
            session.setMaxInactiveInterval(SESSION_TIMEOUT);
            session.setAttribute("_csrfToken", csrfTokenGenerator.generate());
            return "redirect:/resources/home";
        }
        httpRequest.getSession().invalidate();
        return "redirect:/login";
    }
}
