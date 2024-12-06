package it.epicode.dao;

import it.epicode.entity.Libro;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LibroDAO {
    private EntityManager em;

    //LIBRODAO E RIVISTADAO sono superflui in quanto gestisco tutto da catalogo DAO. Li lascio per completezza e scalabilitta' del progetto, in fase di creazione uso
    //l'entitymanager per "persistere" i dati nel database.

    public void save(Libro oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Libro findById(Long id) {
        return em.find(Libro.class, id);
    }

    public List<Libro> findAll() {
        return em.createNamedQuery("Trova_tutto_Libro", Libro.class).getResultList();
    }

    public void update(Libro oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Libro oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }


}