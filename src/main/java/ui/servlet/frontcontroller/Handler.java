package ui.servlet.frontcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public abstract class Handler {
    protected final HttpServletRequest httpRequest;
    protected final HttpServletResponse httpResponse;
    protected final RequestMapping requestMapping;

    protected Handler(HttpServletRequest httpRequest, HttpServletResponse httpResponse, RequestMapping requestMapping) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
        this.requestMapping = requestMapping;
    }

    public abstract String process();

    protected final Map<String, String> extractPathVariables() {

        Map<String, String> pathVariablesMap = new HashMap<>();

        String[] uriParts = httpRequest.getRequestURI().split("/");
        String[] templateParts = requestMapping.getUri().split("/");

        for (int i = 0; i < uriParts.length; i++) {
            if (templateParts[i].startsWith("{") && templateParts[i].endsWith("}")) {
                pathVariablesMap.put(templateParts[i].substring(1, templateParts[i].length() - 1), uriParts[i]);
            }
        }
        return pathVariablesMap;
    }
}
