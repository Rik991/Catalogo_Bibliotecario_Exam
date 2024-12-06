package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "riviste")

public class Rivista  extends Catalogo {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

}