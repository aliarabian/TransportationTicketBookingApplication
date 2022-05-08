package ui.servlet.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/"})
public class FrontController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Handler handler = HandlerFactory.createHandler(req, resp);
        String page = handler.process();
        dispatch(req, resp, page);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp, String page) throws ServletException, IOException {
        if (page.startsWith("forward:")) {
            page = page.replace("forward:", "");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(page);
            requestDispatcher.forward(req, resp);
        } else if (page.startsWith("redirect:")) {
            resp.sendRedirect(req.getContextPath() + page.replace("redirect:", ""));
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(ViewResolver.resolve(page));
            requestDispatcher.forward(req, resp);
        }
    }
}

