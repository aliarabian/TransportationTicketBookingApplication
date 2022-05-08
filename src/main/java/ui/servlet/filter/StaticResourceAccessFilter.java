package ui.servlet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, filterName = "staticResourceAccess")
public class StaticResourceAccessFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getRequestURI().substring(req.getContextPath().length()).startsWith("/static/")) {
            getServletContext().getNamedDispatcher("default").forward(req, res);
            return;
        }
        chain.doFilter(req,res);
    }
}
