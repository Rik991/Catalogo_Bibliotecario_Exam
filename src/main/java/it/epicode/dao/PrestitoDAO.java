package it.epicode.dao;

import it.epicode.entity.Catalogo;
import it.epicode.entity.Prestito;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PrestitoDAO {
    private EntityManager em;

    public void save(Prestito prestito) {
        em.getTransaction().begin();
        em.persist(prestito);
        em.getTransaction().commit();
    }

    public Prestito findById(Long id) {
        return em.find(Prestito.class, id);
    }

    public List<Prestito> findAll() {
        return em.createNamedQuery("Trova_tutto_Prestito", Prestito.class).getResultList();
    }

    public void update(Prestito prestito) {
        em.getTransaction().begin();
        em.merge(prestito);
        em.getTransaction().commit();
    }

    public void delete(Prestito prestito) {
        em.getTransaction().begin();
        em.remove(prestito);
        em.getTransaction().commit();
    }

    public List<Prestito> findPrestitiByNumeroTessera(int numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera", Prestito.class);
        query.setParameter("numeroTessera", numeroTessera);
        return query.getResultList();
    }

    public List<Prestito> findPrestitiScadutiNonRestituiti() {
        TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < CURRENT_DATE AND p.dataRestituzioneEffettiva IS NULL", Prestito.class);
        return query.getResultList();
    }






}