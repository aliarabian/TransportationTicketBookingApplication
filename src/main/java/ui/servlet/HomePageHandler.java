package ui.servlet;

import com.platform.ResponseEntity;
import com.platform.business.service.search.transportations.AirlineTransportationsResource;
import ui.servlet.frontcontroller.Handler;
import ui.servlet.frontcontroller.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomePageHandler extends Handler {
    private final AirlineTransportationsResource transportationsResource;
    public HomePageHandler(HttpServletRequest httpRequest, HttpServletResponse httpResponse, RequestMapping requestMapping) {
        super(httpRequest, httpResponse, requestMapping);
        transportationsResource = new AirlineTransportationsResource();
    }

    @Override
    public String process() throws ServletException, IOException {
        ResponseEntity<?> transportations = transportationsResource.getAllTransportations();
        httpRequest.setAttribute("flights", transportations.getData());
        return "home";
    }
}
