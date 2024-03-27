package be.technobel.backfermedubeaulieu.pl.models.forms;

import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record ScanForm(
        @PastOrPresent LocalDate scan_date,
        boolean result,
        String loopNumber
) {
}