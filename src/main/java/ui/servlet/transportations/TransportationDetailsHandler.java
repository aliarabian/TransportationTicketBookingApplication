package ui.servlet.transportations;

import com.platform.ResponseEntity;
import com.platform.business.service.search.transportations.AirlineTransportationsResource;
import ui.servlet.frontcontroller.Handler;
import ui.servlet.frontcontroller.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TransportationDetailsHandler extends Handler {
    private final AirlineTransportationsResource transportationsResource;

    public TransportationDetailsHandler(HttpServletRequest httpRequest, HttpServletResponse httpResponse, RequestMapping requestMapping) {
        super(httpRequest, httpResponse, requestMapping);
        transportationsResource = new AirlineTransportationsResource();
    }

    @Override
    public String process() {
        Map<String, String> pathVariablesMap = extractPathVariables();
        String transportationIdString = pathVariablesMap.get("transportationId");
        long transportationId = Long.parseLong(transportationIdString);
        ResponseEntity<?> transportation = transportationsResource.getTransportationById(transportationId);
        httpRequest.setAttribute("flight", transportation.getData());
        return "flight-details";
    }
}
