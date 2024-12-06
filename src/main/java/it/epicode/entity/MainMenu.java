package it.epicode.entity;


import it.epicode.dao.CatalogoDAO;
import it.epicode.dao.PrestitoDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
    private static EntityManager em = emf.createEntityManager();
    private static CatalogoDAO catalogoDAO = new CatalogoDAO(em);
    private static PrestitoDAO prestitoDAO = new PrestitoDAO(em);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //ora che ho settato il persistence in update lascio la parola all'utente finale che si troverà
        // il db già popolato dal precedente MainCreate (con persistence in create).

        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("Menu:");
                System.out.println("1. Aggiungi elemento del catalogo");
                System.out.println("2. Rimuovi elemento dal catalogo (ISBN)");
                System.out.println("3. Ricerca per ISBN");
                System.out.println("4. Ricerca per anno di pubblicazione");
                System.out.println("5. Ricerca per autore");
                System.out.println("6. Ricerca per titolo o parte di esso");
                System.out.println("7. Ricerca elementi in prestito (numero tessera)");
                System.out.println("8. Ricerca prestiti scaduti e non restituiti");
                System.out.println("0. Esci");
                System.out.print("Scegli un'opzione: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addCatalogo();
                        break;
                    case 2:
                        removeCatalogoByISBN();
                        break;
                    case 3:
                        searchByISBN();
                        break;
                    case 4:
                        searchByAnnoDiPubblicazione();
                        break;
                    case 5:
                        searchByAutore();
                        break;
                    case 6:
                        searchByTitolo();
                        break;
                    case 7:
                        searchPrestitiByNumeroTessera();
                        break;
                    case 8:
                        searchPrestitiScadutiNonRestituiti();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("Opzione non valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: Inserisci un numero valido.");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        em.close();
        emf.close();
    }

    private static void addCatalogo() {
        System.out.print("Inserisci il tipo di elemento (1 per libro, 2 per rivista): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Codice ISBN: ");
        String isbn = scanner.nextLine();
        if (catalogoDAO.findByISBN(isbn) != null) {
            System.out.println("Elemento con questo ISBN esiste già.");
            return;
        }

        if (tipo == 1) {
            Libro libro = new Libro();
            libro.setCodiceISBN(isbn);
            System.out.print("Titolo: ");
            libro.setTitolo(scanner.nextLine());
            System.out.print("Autore: ");
            libro.setAutore(scanner.nextLine());
            System.out.print("Genere: ");
            libro.setGenere(scanner.nextLine());
            System.out.print("Anno di pubblicazione: ");
            libro.setAnnoDiPubblicazione(scanner.nextInt());
            System.out.print("Numero di pagine: ");
            libro.setNumeroPagine(scanner.nextInt());
            scanner.nextLine();
            catalogoDAO.addCatalogo(libro);
        } else if (tipo == 2) {
            Rivista rivista = new Rivista();
            rivista.setCodiceISBN(isbn);
            System.out.print("Titolo: ");
            rivista.setTitolo(scanner.nextLine());
            System.out.print("Anno di pubblicazione: ");
            rivista.setAnnoDiPubblicazione(scanner.nextInt());
            System.out.print("Numero di pagine: ");
            rivista.setNumeroPagine(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Periodicità (1 per SETTIMANALE, 2 per MENSILE, 3 per ANNUALE): ");
            int periodicita = scanner.nextInt();
            scanner.nextLine();
            switch (periodicita) {
                case 1:
                    rivista.setPeriodicita(Periodicita.SETTIMANALE);
                    break;
                case 2:
                    rivista.setPeriodicita(Periodicita.MENSILE);
                    break;
                case 3:
                    rivista.setPeriodicita(Periodicita.SEMESTRALE);
                    break;
                default:
                    System.out.println("Periodicità non valida.");
                    return;
            }
            catalogoDAO.addCatalogo(rivista);
        } else {
            System.out.println("Tipo di elemento non valido.");
        }
    }

    private static void removeCatalogoByISBN() {
        System.out.print("Inserisci il codice ISBN: ");
        String isbn = scanner.nextLine();
        catalogoDAO.removeCatalogoByISBN(isbn);
    }

    private static void searchByISBN() {
        System.out.print("Inserisci il codice ISBN: ");
        String isbn = scanner.nextLine();
        Catalogo catalogo = catalogoDAO.findByISBN(isbn);
        if (catalogo != null) {
            System.out.println(catalogo);
        } else {
            System.out.println("ISBN non trovato, riprova.");
        }
    }

    private static void searchByAnnoDiPubblicazione() {
        System.out.print("Inserisci l'anno di pubblicazione: ");
        int anno = scanner.nextInt();
        scanner.nextLine();
        List<Catalogo> cataloghi = catalogoDAO.findByAnnoDiPubblicazione(anno);
        cataloghi.forEach(System.out::println);
    }

    private static void searchByAutore() {
        System.out.print("Inserisci l'autore: ");
        String autore = scanner.nextLine();
        List<Catalogo> cataloghi = catalogoDAO.findByAutore(autore);
        if (!cataloghi.isEmpty()) {
            cataloghi.forEach(System.out::println);
        } else {
            System.out.println("Autore non trovato, riprova.");
        }
    }

    private static void searchByTitolo() {
        System.out.print("Inserisci il titolo o parte di esso: ");
        String titolo = scanner.nextLine();
        List<Catalogo> cataloghi = catalogoDAO.findByTitolo(titolo);
        for (Catalogo catalogo : cataloghi) {
            System.out.println("Tipo: " + catalogo.getClass().getSimpleName() + ", Titolo: " + catalogo.getTitolo());
        }
    }

    private static void searchPrestitiByNumeroTessera() {
        System.out.print("Inserisci il numero di tessera: ");
        int numeroTessera = scanner.nextInt();
        scanner.nextLine();
        List<Prestito> prestiti = prestitoDAO.findPrestitiByNumeroTessera(numeroTessera);
        if (!prestiti.isEmpty()) {
            prestiti.forEach(System.out::println);
        } else {
            System.out.println("Numero di tessera non trovato, riprova.");
        }
    }

    private static void searchPrestitiScadutiNonRestituiti() {
        List<Prestito> prestiti = prestitoDAO.findPrestitiScadutiNonRestituiti();
        prestiti.forEach(System.out::println);
    }
}