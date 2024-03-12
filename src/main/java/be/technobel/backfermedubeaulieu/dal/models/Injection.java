package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Injection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private LocalDate injectionDate;
    @ManyToOne
    private Bull bovin;
    @ManyToOne
    private Substance substance;
}
