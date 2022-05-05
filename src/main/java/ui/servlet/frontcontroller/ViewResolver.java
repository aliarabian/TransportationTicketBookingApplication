package ui.servlet.frontcontroller;

public class ViewResolver {
    private static final String VIEW_ROOT_DIR = "/WEB-INF/jsp/";
    private static final String VIEW_SUFFIX = ".jsp";

    public static String resolve(String name) {
        return VIEW_ROOT_DIR + name + VIEW_SUFFIX;
    }
}
