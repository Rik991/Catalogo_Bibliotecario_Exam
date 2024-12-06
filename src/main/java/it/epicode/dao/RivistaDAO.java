package it.epicode.dao;

import it.epicode.entity.Rivista;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RivistaDAO {
    private EntityManager em;
    //LIBRODAO E RIVISTADAO sono superflui in quanto gestisco tutto da catalogo DAO. Li lascio per completezza e scalabilitta' del progetto.

    public void save(Rivista oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Rivista findById(Long id) {
        return em.find(Rivista.class, id);
    }

    public List<Rivista> findAll() {
        return em.createNamedQuery("Trova_tutto_Rivista", Rivista.class).getResultList();
    }

    public void update(Rivista oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void delete(Rivista oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }


}