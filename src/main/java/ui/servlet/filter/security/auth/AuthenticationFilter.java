package ui.servlet.filter.auth;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/resources/*"}, filterName = "authentication")
public class AuthenticationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String token = (String) session.getAttribute("auth-token");
        if (token == null) {
            res.sendRedirect(getServletContext().getContextPath() + "/login");
            return;
        }
        chain.doFilter(req, res);
    }
}
