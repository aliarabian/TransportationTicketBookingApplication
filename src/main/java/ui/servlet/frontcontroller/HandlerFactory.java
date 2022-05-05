package ui.servlet.frontcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class HandlerFactory {
    private static final Map<RequestMapping, Class<? extends Handler>> HANDLER_MAPPINGS;
    public static final String CONTEXT_ROOT = "/booking-app";
    private static final RequestMapping UNKNOWN_HANDLER_REQUEST_MAPPING = new RequestMapping("unknown", "unknown");

    private static void addRoute(String method, String uri, Class<? extends Handler> handler) {
        HANDLER_MAPPINGS.put(new RequestMapping(method, uri), handler);
    }

    static {
        HANDLER_MAPPINGS = new HashMap<>();
        HANDLER_MAPPINGS.put(UNKNOWN_HANDLER_REQUEST_MAPPING, UnknownHandler.class);
    }

    private HandlerFactory() {
    }

    public static Handler createHandler(HttpServletRequest request, HttpServletResponse response) {
        Handler handler;
        try {
            RequestMapping requestMapping = getMatchingRequestMapping(request.getMethod(), request.getRequestURI()).get();
            handler = HANDLER_MAPPINGS.get(requestMapping)
                                      .getDeclaredConstructor(
                                              HttpServletRequest.class,
                                              HttpServletResponse.class,
                                              RequestMapping.class
                                      )
                                      .newInstance(request, response, requestMapping);
            return handler;
        } catch (NoSuchElementException |
                InvocationTargetException |
                InstantiationException |
                IllegalAccessException |
                NoSuchMethodException exception) {
            exception.printStackTrace();
        }

        return new UnknownHandler(request, response, UNKNOWN_HANDLER_REQUEST_MAPPING);
    }

    private static Optional<RequestMapping> getMatchingRequestMapping(final String method, final String requestURI) {
        return HANDLER_MAPPINGS.keySet().stream()
                               .filter(requestMapping -> requestMapping.matches(method, requestURI))
                               .findFirst();
    }


}
