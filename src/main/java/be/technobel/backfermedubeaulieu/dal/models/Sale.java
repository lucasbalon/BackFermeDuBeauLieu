package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate saleDate;
    private double amount;
    private int carrierNumber;
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

    public Sale() {

    }
}
