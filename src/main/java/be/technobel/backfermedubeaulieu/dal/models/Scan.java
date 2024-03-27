package be.technobel.backfermedubeaulieu.dal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Scan {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private LocalDate scan_date;
    @Getter
    private boolean result;
    @ManyToOne
    private Cow cow;

    public Scan(LocalDate localDate, boolean result, Cow cow) {
        this.scan_date = localDate;
        this.result = result;
        this.cow = cow;
    }

}
