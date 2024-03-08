package be.technobel.backfermedubeaulieu.dal.models;

import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
    @OneToMany(mappedBy = "father")
    private List<Bull> bullChildren;
    @OneToMany(mappedBy = "mother")
    private List<Cow> cowChildren;
}
