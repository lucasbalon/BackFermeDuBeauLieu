package be.technobel.backfermedubeaulieu.pl.models.forms;

import be.technobel.backfermedubeaulieu.dal.models.Scan;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

public record ScanForm(
        @PastOrPresent LocalDate scan_date,
        boolean result,
        String loopNumber
        ) {
}