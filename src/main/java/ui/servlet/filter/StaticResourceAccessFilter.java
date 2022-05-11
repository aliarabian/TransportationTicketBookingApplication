package ui.servlet.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
            if (req.getRequestURI().endsWith(".mjs")) {
                res.setContentType("text/javascript");
            }
            getServletContext().getNamedDispatcher("default").forward(req, res);
            return;
        }
        chain.doFilter(req, res);
    }
}
