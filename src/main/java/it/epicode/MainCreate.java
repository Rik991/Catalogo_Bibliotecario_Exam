package it.epicode;

import com.github.javafaker.Faker;
import it.epicode.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.Locale;

public class MainCreate {
    public static void main(String[] args) {

        //questo è il main che popola il db, usato soltanto per la creazione delle tabelle e per il popolamento iniziale.
        //adesso passerò il persistence a update così da poter fare le operazioni CRUD nel MainMenu.

        Faker faker = new Faker(new Locale("it"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();

        //popoliamo il db di libri
        em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            Libro l = new Libro();
            l.setTitolo(faker.book().title());
            l.setAutore(faker.book().author());
            l.setCodiceISBN(faker.code().isbn13());
            l.setGenere(faker.book().genre());
            l.setAnnoDiPubblicazione(faker.number().numberBetween(1900, 2021));
            l.setNumeroPagine(faker.number().numberBetween(50, 1000));
            em.persist(l);
        }
        //popoliamo il db di riviste
        for (int i = 0; i < 10; i++) {
            Rivista r = new Rivista();
            r.setTitolo(faker.book().title());
            r.setCodiceISBN(faker.code().isbn13());
            r.setAnnoDiPubblicazione(faker.number().numberBetween(1900, 2021));
            r.setNumeroPagine(faker.number().numberBetween(50, 1000));
            r.setPeriodicita(faker.options().option(Periodicita.class));
            em.persist(r);
        }

        //inseriamo anche qualche utente
        Utente u = new Utente();
        u.setNome("Riccardo");
        u.setCognome("Santilli");
        u.setDataDiNascita(LocalDate.of(1991, 07, 03));
        u.setNumeroTessera(1);
        em.persist(u);

        Utente u2 = new Utente();
        u2.setNome("Mauro");
        u2.setCognome("Larese");
        u2.setDataDiNascita(LocalDate.of(1990, 01, 01));
        u2.setNumeroTessera(2);
        em.persist(u2);

        Utente u3 = new Utente();
        u3.setNome("Gianmarco");
        u3.setCognome("Arzanese");
        u3.setDataDiNascita(LocalDate.of(1996, 05, 05));
        u3.setNumeroTessera(3);
        em.persist(u3);

        Utente u4 = new Utente();
        u4.setNome("Danilo");
        u4.setCognome("Fumuso");
        u4.setDataDiNascita(LocalDate.of(1993, 03, 03));
        u4.setNumeroTessera(4);
        em.persist(u4);

        //e un prestito già fatto ma restituito dall'utente 1
        Prestito p = new Prestito();
        p.setUtente(u);
        p.setElementoPrestato(em.find(Libro.class, 1L));
        p.setDataInizioPrestito(LocalDate.of(2021, 10, 1));
        p.setDataRestituzionePrevista(p.getDataInizioPrestito().plusDays(30));
        p.setDataRestituzioneEffettiva(LocalDate.of(2021, 10, 15));
        em.persist(p);



        //popolo il db di tanti prestiti random per poter fare test
        for (int i = 0; i < 50; i++) {
            Prestito p2 = new Prestito();
            p2.setUtente(em.find(Utente.class, faker.number().numberBetween(1, 5))); // Decido random quale dei 4 utenti ha fatto il prestito

            // Decidi casualmente se il prestito è di un libro o di una rivista
            Catalogo elementoPrestato;
            if (faker.bool().bool()) {
                elementoPrestato = em.find(Libro.class, faker.number().numberBetween(1, 10));
            } else {
                elementoPrestato = em.find(Rivista.class, faker.number().numberBetween(1, 10));
            }

            // Verifica che l'elemento prestato non sia nullo dato che mi causava errori la data di restituzione a null
            if (elementoPrestato != null) {
                p2.setElementoPrestato(elementoPrestato);
                p2.setDataInizioPrestito(LocalDate.now().minusDays(faker.number().numberBetween(1, 365)));
                p2.setDataRestituzionePrevista(p2.getDataInizioPrestito().plusDays(30));

                // Decidi casualmente se impostare dataRestituzioneEffettiva a null o a una data futura cos' avremo prestiti scaduti e prestiti restituiti
                if (faker.bool().bool()) {
                    p2.setDataRestituzioneEffettiva(null);
                } else {
                    p2.setDataRestituzioneEffettiva(p2.getDataInizioPrestito().plusDays(faker.number().numberBetween(5, 60)));
                }

                em.persist(p2);
            }
        }


        em.getTransaction().commit();
        em.close();
        emf.close();


    }
}