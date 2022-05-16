package ui.servlet.filter.security;

import com.platform.security.CsrfTokenGenerator;
import com.platform.security.SecureCsrfTokenGenerator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = "/*", filterName = "CsrfTokenFilter")
public class CsrfFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        CsrfTokenGenerator csrfTokenGenerator = new SecureCsrfTokenGenerator();
        HttpSession session = req.getSession();
        String csrfToken = (String) session.getAttribute("_csrfToken");
        if (csrfToken == null) {
            session.setAttribute("_csrfToken", csrfTokenGenerator.generate());
        }
        chain.doFilter(req, res);
    }
}
