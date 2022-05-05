package ui.servlet.frontcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownHandler extends Handler {
    public UnknownHandler(HttpServletRequest request, HttpServletResponse response, RequestMapping requestMapping) {
        super(request, response, requestMapping);
    }

    @Override
    public String process() {
        return "unknown";
    }
}
