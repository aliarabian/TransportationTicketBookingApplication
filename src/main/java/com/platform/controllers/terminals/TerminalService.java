package com.platform.controllers.terminals;

import com.platform.business.model.transportation.Terminal;

import java.util.Set;

public interface TerminalService {
    Set<Terminal> terminals();
}
