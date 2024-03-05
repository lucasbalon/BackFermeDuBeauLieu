package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Injection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private LocalDate injectionDate;
    @ManyToOne
    private Bovin bovin;
    @ManyToOne
    private Substance substance;
}
