package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cow extends Bull{
    @OneToMany(mappedBy = "cow")
    private List<Scan> scans;
}

