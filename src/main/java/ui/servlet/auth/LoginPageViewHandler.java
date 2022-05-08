package ui.servlet.auth;

import ui.servlet.frontcontroller.Handler;
import ui.servlet.frontcontroller.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginPageViewHandler extends Handler {
    public LoginPageViewHandler(HttpServletRequest httpRequest, HttpServletResponse httpResponse, RequestMapping requestMapping) {
        super(httpRequest, httpResponse, requestMapping);
    }

    @Override
    public String process() throws ServletException, IOException {
        HttpSession session = httpRequest.getSession();
        String token = (String) session.getAttribute("auth-token");

        if (token != null) {
            return "redirect:/resources/home";
        }
        return "login";
    }
}
