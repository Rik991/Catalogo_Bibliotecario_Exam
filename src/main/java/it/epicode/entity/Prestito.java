package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "prestiti")
@NamedQuery(name = "Trova_tutto_Prestito", query = "SELECT a FROM Prestito a")
public class Prestito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ToString.Exclude //per evitare il loop infinito
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @ManyToOne
    @JoinColumn(name = "elemento_prestato_id", nullable = false)
    private Catalogo elementoPrestato;
    @Column(name = "data_inizio_prestito", nullable = false)
    private LocalDate dataInizioPrestito;
    @Column(name = "data_restituzione_prevista", nullable = false)
    private LocalDate dataRestituzionePrevista;
    @Column(name = "data_restituzione_effettiva")
    private LocalDate dataRestituzioneEffettiva;



}