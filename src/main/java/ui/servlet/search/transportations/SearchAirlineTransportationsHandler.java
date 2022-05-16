package ui.servlet.search.transportations;

import com.platform.ResponseEntity;
import com.platform.business.service.search.transportations.AirlineTransportationsResource;
import com.platform.business.service.search.transportations.dto.AirlineTransportationDto;
import ui.servlet.frontcontroller.Handler;
import ui.servlet.frontcontroller.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Set;

public class SearchAirlineTransportationsHandler extends Handler {
    private final AirlineTransportationsResource transportationsResource;

    public SearchAirlineTransportationsHandler(HttpServletRequest httpRequest, HttpServletResponse httpResponse, RequestMapping requestMapping) {
        super(httpRequest, httpResponse, requestMapping);
        transportationsResource = new AirlineTransportationsResource();
    }

    @Override
    public String process() {
        String departureDateTime = httpRequest.getParameter("departureTime");
        Optional<OffsetDateTime> dateTime = parseDateTime(departureDateTime);
        String from = httpRequest.getParameter("offset");
        String to = httpRequest.getParameter("destination");
        ResponseEntity<?> transportations;
        if (dateTime.isEmpty() || from == null || to == null) {
            httpRequest.setAttribute("searchError", "Invalid Search Parameter");
            return "forward:/resources/home";
        } else {
            transportations = transportationsResource.findTransportations(from, to, dateTime.get());
            System.out.println(transportations.getData().toString());
        }
        if (!transportations.isError()) {
            httpRequest.setAttribute("flights", transportations.getData());
        }
        return "home";
    }

    private Optional<OffsetDateTime> parseDateTime(String dateTime) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            OffsetDateTime departureDateTime = OffsetDateTime.of(localDateTime, ZoneId.of("Asia/Tehran").getRules().getOffset(localDateTime));
            return Optional.of(departureDateTime);
        } catch (DateTimeParseException | NullPointerException parseException) {
            return Optional.empty();
        }

    }
}
