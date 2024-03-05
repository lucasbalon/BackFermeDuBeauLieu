package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Scan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate scan_date;
    private boolean result;
    @ManyToOne
    private Cow cow;
}
