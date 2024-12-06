package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "utenti")
@NamedQuery(name = "Trova_tutto_Utente", query = "SELECT a FROM Utente a")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(name = "data_di_nascita", nullable = false)
    private LocalDate dataDiNascita;
    @Column(name = "numero_tessera", nullable = false)
    private int numeroTessera;

    @OneToMany(mappedBy = "utente")
    @ToString.Exclude //per evitare il loop infinito
    private List<Prestito> prestiti = new ArrayList<>();


}