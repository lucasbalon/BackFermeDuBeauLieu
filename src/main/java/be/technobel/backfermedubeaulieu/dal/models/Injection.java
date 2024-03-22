package be.technobel.backfermedubeaulieu.dal.models;

import be.technobel.backfermedubeaulieu.pl.models.dtos.SubstanceDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Getter
@Setter
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


    public Injection() {
        
    }

    public Injection(SubstanceDto substanceByName, LocalDate localDate, Bull byLoopNumber) {
        this.substance = new Substance(substanceByName.name());
        this.injectionDate = localDate;
        this.bovin = byLoopNumber;
    }
}
