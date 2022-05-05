package ui.servlet.frontcontroller;

import java.util.Objects;

public class RequestMapping {
    private final String method;
    private final String uri;

    public RequestMapping(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public boolean matches(String method, String uri) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(uri);
        method = method.toUpperCase();
        if (!this.method.equals(method)) {
            return false;
        }

        String[] uriParts = uri.split("/");
        String[] templateParts = this.getUri().split("/");
        if (uriParts.length != templateParts.length) {
            return false;
        }

        for (int i = 0; i < uriParts.length; i++) {
            if (!templateParts[i].startsWith("{") && !templateParts[i].endsWith("}") && !uriParts[i].equals(templateParts[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestMapping)) return false;
        RequestMapping that = (RequestMapping) o;
        return method.equals(that.method) && uri.equals(that.uri);
    }


    @Override
    public int hashCode() {
        return Objects.hash(method, uri);
    }

    @Override
    public String toString() {
        return "RequestMapping{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
