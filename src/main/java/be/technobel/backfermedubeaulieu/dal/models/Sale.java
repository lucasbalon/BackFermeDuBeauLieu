package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class Sale {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private LocalDate saleDate;
    @Getter
    private double amount;
    @Getter
    private int carrierNumber;
    @Getter
    private int customerNumber;
    @OneToOne
    private Bull bovin;

    public Sale(LocalDate localDate, double amount, int carrierNumber, int customerNumber, Bull bull) {
        this.saleDate = localDate;
        this.amount = amount;
        this.carrierNumber = carrierNumber;
        this.customerNumber = customerNumber;
        this.bovin = bull;
    }

}
