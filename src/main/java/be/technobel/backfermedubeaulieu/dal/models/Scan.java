package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Scan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate scan_date;
    private boolean result;
    @ManyToOne
    private Cow cow;

    public Scan(LocalDate localDate, boolean result, Cow cow) {
        this.scan_date = localDate;
        this.result = result;
        this.cow = cow;
    }
}
