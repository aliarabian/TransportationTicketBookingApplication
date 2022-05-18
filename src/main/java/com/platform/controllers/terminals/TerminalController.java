package com.platform.controllers.terminals;

import com.platform.ApiResponseEntity;
import com.platform.business.booking.dto.FlightTicketDto;
import com.platform.model.Terminal;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "terminals", produces = MediaType.APPLICATION_JSON_VALUE)
public class TerminalController {

    @GetMapping
    public ResponseEntity<ApiResponseEntity<Set<Terminal>>> getTerminals() {
        return ResponseEntity.ok()
                             .build();
    }
}
