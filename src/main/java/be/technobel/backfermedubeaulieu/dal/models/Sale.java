package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate saleDate;
    private double amount;
    private int carrierNumber;
    private int customerNumber;
    @OneToOne
    private Bovin bovin;
}
