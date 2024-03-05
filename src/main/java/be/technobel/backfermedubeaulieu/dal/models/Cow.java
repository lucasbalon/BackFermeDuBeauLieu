package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cow extends Bovin{
    @OneToMany(mappedBy = "cow")
    private List<Scan> scans;
    @OneToMany(mappedBy = "mother")
    private List<Bovin> children;
}

