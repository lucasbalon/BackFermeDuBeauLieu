package be.technobel.backfermedubeaulieu.dal.models;

import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
//todo: orm relation hibernate
//todo: diminuer les annotations
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("BULL")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bull{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String loopNumber;
    private String coat;
    private boolean gender;
    @Temporal(value = TemporalType.DATE)
    private LocalDate birthDate;
    private boolean cesarean;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(nullable = true)
    private Bull father;
    @ManyToOne
    @JoinColumn(nullable = true)
    private Cow mother;
    @ManyToOne
    private Pasture pasture;
    @OneToMany(mappedBy = "bovin")
    private List<Injection> injection;
    @OneToOne(mappedBy = "bovin")
    private Sale sale;
}
