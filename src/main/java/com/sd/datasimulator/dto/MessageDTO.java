package com.sd.datasimulator.dto;

import java.time.LocalDateTime;

public record MessageDTO(
        String username,
        String device,
        LocalDateTime time,
        double consumption
) {
}
