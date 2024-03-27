package be.technobel.backfermedubeaulieu.dal.models;

import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("BULL")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Bull {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    private String loopNumber;
    private String coat;
    private boolean gender;
    @Temporal(value = TemporalType.DATE)
    private LocalDate birthDate;
    private boolean cesarean;
    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Bull father;
    @ManyToOne
    private Cow mother;
    @Setter
    @ManyToOne
    private Pasture pasture;
    @OneToMany(mappedBy = "bovin")
    private List<Injection> injection;
    @OneToOne(mappedBy = "bovin")
    private Sale sale;

}
