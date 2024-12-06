package it.epicode.entity;

import it.epicode.entity.Catalogo;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "libri")

public class Libro extends Catalogo {

    @Column(nullable = false)
    private String autore;

    @Column(nullable = false)
    private String genere;


}