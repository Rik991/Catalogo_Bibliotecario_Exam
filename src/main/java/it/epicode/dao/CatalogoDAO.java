package it.epicode.dao;

import it.epicode.entity.Catalogo;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CatalogoDAO {
    private EntityManager em;

    //userò il catalogoDAO per gestire sia i libri che le riviste, quindi i metodi di LibroDAO e RivistaDAO sono superflui.

    public void addCatalogo(Catalogo catalogo) {
        em.getTransaction().begin();
        em.persist(catalogo);
        em.getTransaction().commit();
    }

    public Catalogo findById(Long id) {
        return em.find(Catalogo.class, id);
    }

    public List<Catalogo> findAll() {
        return em.createNamedQuery("Trova_tutto_Catalogo", Catalogo.class).getResultList();
    }

    public void update(Catalogo catalogo) {
        em.getTransaction().begin();
        em.merge(catalogo);
        em.getTransaction().commit();
    }

    public void delete(Catalogo catalogo) {
        em.getTransaction().begin();
        em.remove(catalogo);
        em.getTransaction().commit();
    }

    //dopo la classica CRUD aggiungiamo i metodi per le query personalizzate in base alla richiesta.

    public void removeCatalogoByISBN(String isbn) {
        em.getTransaction().begin();
        TypedQuery<Catalogo> query = em.createQuery("SELECT c FROM Catalogo c WHERE c.codiceISBN = :isbn", Catalogo.class);//crea una query che seleziona entità Catalogo dove il campo codiceISBN corrisponde al parametro specificato.
        query.setParameter("isbn", isbn);
        Catalogo catalogo = query.getSingleResult();
        if (catalogo != null) {
            em.remove(catalogo);
        }
        em.getTransaction().commit();
    }

    public Catalogo findByISBN(String isbn) {
        TypedQuery<Catalogo> query = em.createQuery("SELECT c FROM Catalogo c WHERE c.codiceISBN = :isbn", Catalogo.class);
        query.setParameter("isbn", isbn);
        return query.getSingleResult();
    }

    public List<Catalogo> findByAnnoDiPubblicazione(int anno) {
        TypedQuery<Catalogo> query = em.createQuery("SELECT c FROM Catalogo c WHERE c.annoDiPubblicazione = :anno", Catalogo.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    public List<Catalogo> findByAutore(String autore) {
        TypedQuery<Catalogo> query = em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Catalogo.class);
        query.setParameter("autore", autore);
        return query.getResultList();
    }

    public List<Catalogo> findByTitolo(String titolo) {
        TypedQuery<Catalogo> query = em.createQuery("SELECT c FROM Catalogo c WHERE c.titolo LIKE :titolo", Catalogo.class);
        query.setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }


}