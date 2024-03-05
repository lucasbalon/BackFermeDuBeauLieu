package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Substance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int doses;
    private int timeBeforeKilling;
    @OneToMany(mappedBy = "substance")
    private List<Injection> injection;
}
