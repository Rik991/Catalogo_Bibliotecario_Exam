package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "catologo")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "Trova_tutto_Catalogo", query = "SELECT a FROM Catalogo a")
public abstract class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "codice_isbn", nullable = false)
    private String codiceISBN;

    @Column(name = "titolo", nullable = false)
    private String titolo;

    @Column(name = "anno_di_pubblicazione", nullable = false)
    private int annoDiPubblicazione;

    @Column(name = "numero_pagine", nullable = false)
    private int numeroPagine;



}