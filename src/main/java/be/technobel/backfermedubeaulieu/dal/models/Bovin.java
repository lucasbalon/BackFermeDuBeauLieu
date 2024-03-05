package be.technobel.backfermedubeaulieu.dal.models;

import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Bovin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int loopNumber;
    private String coat;
    private boolean gender;
    @Temporal(value = TemporalType.DATE)
    private LocalDate birthDate;
    private boolean cesarean;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Bull father;
    @ManyToOne
    private Cow mother;
    @ManyToOne
    private Pasture pasture;
    @OneToMany(mappedBy = "bovin")
    private List<Injection> injection;
    @OneToOne(mappedBy = "bovin")
    private Sale sale;
}
