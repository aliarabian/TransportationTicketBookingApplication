package ui.servlet.filter.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter(value = "/*", filterName = "CsrfValidator")
public class CsrfValidatorFilter extends HttpFilter {
    private final List<String> unsafeMethods = List.of("post", "put", "delete", "patch");


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (unsafeMethods.contains(req.getMethod().toLowerCase())) {
            HttpSession session = req.getSession();
            String storedCsrfToken = (String) session.getAttribute("_csrfToken");
            String clientSentCsrfToken = req.getParameter("_csrfToken");
            if (storedCsrfToken == null || !storedCsrfToken.equals(clientSentCsrfToken)) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
