package be.technobel.backfermedubeaulieu.pl.models.forms;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

/**
 * DTO for {@link be.technobel.backfermedubeaulieu.dal.models.Sale}
 */
public record SaleForm(
        @PastOrPresent(message = "Pas de date dans le futur") LocalDate saleDate,
        @PositiveOrZero(message = "Pas de données négatives") double amount,
        int carrierNumber,
        int customerNumber,
        String bovinLoopNumber
) {
}