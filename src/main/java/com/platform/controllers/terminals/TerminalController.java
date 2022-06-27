package com.platform.controllers.terminals;

import com.platform.ApiResponseEntity;
import com.platform.business.model.Terminal;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;

import java.util.Set;

@RestController
@RequestMapping(value = "terminals", produces = MediaType.APPLICATION_JSON_VALUE)
public class TerminalController {
    private final TerminalService terminalService;

    public TerminalController(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseEntity<Set<Terminal>>> getTerminals() {
        return ResponseEntity.ok(new ApiResponseEntity<>(terminalService.terminals()));

    }
}
