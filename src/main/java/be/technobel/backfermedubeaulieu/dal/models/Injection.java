package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Entity
@NoArgsConstructor
public class Injection {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Temporal(TemporalType.DATE)
    private LocalDate injectionDate;
    @ManyToOne
    private Bull bovin;
    @Getter
    @ManyToOne
    private Substance substance;

}
