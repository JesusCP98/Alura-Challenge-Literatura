package com.alura.challenge_literatula.principal;

import com.alura.challenge_literatula.model.*;
import com.alura.challenge_literatula.repository.AuthorRepository;
import com.alura.challenge_literatula.service.ConsumoAPI;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final Scanner text = new Scanner(System.in);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String URL_BASE = "https://gutendex.com/books/";
    private Boolean terminate = false;
    private final AuthorRepository repo;
    private ConsumoAPI apiConsumer = new ConsumoAPI();
    public Main(AuthorRepository repo) { this.repo=repo; }

    private void showMenu(){
        var menu = """
                    1: Buscar libros por titulo
                    2: Lista libros registrados
                    3: Lista autores registrados
                    4: Lista autores vivos en un determinado año
                    5: Lista libros por idioma
                    6: Mostrar el top 10
                    0: Salir
                    """;
        System.out.println(menu);
    }

    interface Options { void call() throws IOException, InterruptedException; }

    private void exitMenu(){ terminate = true; }

    private void searchBooks() throws IOException, InterruptedException {
        System.out.println("Ingresa el titulo del libro o autor que deseas buscar:");
        var keyPhrase = text.nextLine().toLowerCase().replaceAll(" ","%20");
        String json = apiConsumer.obtenerDatos( URL_BASE+"?search="+keyPhrase );
        var results = objectMapper.readValue(json, ResultsData.class).results();
        if ( results.isEmpty() ){
            System.out.println("No se encontró\n");
            return;
        }
        BookData bookData = results.get(0);
        AuthorsData authorData = bookData.authors().get(0);
        Optional<Author> searchedAuthor = repo.findByNameContainsIgnoreCase( authorData.name() );
        //el autor existe en DB
        if ( searchedAuthor.isPresent() ){
            searchedAuthor.get().getBooks().stream()
                    .filter( book -> book.getTitle().equalsIgnoreCase(bookData.title()) &&
                            book.getLanguage().equalsIgnoreCase(bookData.languages().get(0)) )
                    .findFirst()
                    //el libro ya esta en DB
                    .ifPresentOrElse( book -> {
                        book.setDownloadCount( bookData.downloadCount() );
                        System.out.println( book+"\n" );
                        repo.save(searchedAuthor.get());
                        //el libro no esta en DB
                    }, () -> {
                        var book = new Book( bookData );
                        searchedAuthor.get().addBook( book );
                        System.out.println( book+"\n" );
                        repo.save( searchedAuthor.get() );
                    } );
            return;
        }
        Author author = new Author( authorData );
        Book book = new Book( bookData );
        author.addBook( book );
        repo.save( author );
        System.out.println(book+"\n" );
    }



    private void showBooks(){
        repo.allBooks().forEach(System.out::println);
    }

    private void searchBookByLanguage(){
        System.out.println("Ingresa el idioma para poder consultarr:");
        var bookLanguage = text.nextLine().toLowerCase();
        var books = repo.findByLanguage(bookLanguage);
        if ( books.isEmpty() ){ System.out.println("\nNo existeó\n"); return; }
        System.out.println( books.get(0) );
    }

    private void showAuthors() {
        var authors = repo.findAll();
        System.out.println("\n");
        authors.forEach(System.out::println);
        System.out.println("\n");
    }

    private void  authorsAlive(){
        System.out.println("Ingresa el año en el que deseas buscar autores vivos:");
        Long year = Long.parseLong( text.nextLine() );
        var authors = repo.aliveAtYear(year);
        System.out.println("\n");
        authors.forEach(System.out::println);
        System.out.println("\n");
    }



    private void top10Books(){
        var books = repo.top10Books();
        System.out.println("\n");
        books.forEach( System.out::println );
        System.out.println("\n");
    }



    public void menu() throws IOException, InterruptedException {
        var option = -1;
        while ( !terminate ){
            showMenu();
            try{
                option = Integer.parseInt( text.nextLine() );
                switch (option)
                {
                    case 1:
                        searchBooks();
                        break;
                    case 2:
                        showBooks();
                        break;
                    case 3:
                        showAuthors();
                        break;
                    case 4:
                        authorsAlive();
                        break;
                    case 5:
                        searchBookByLanguage();
                        break;
                    case 6:
                        break;
                    case 0:
                        top10Books();
                        exitMenu();
                        break;


                }
            }catch (NumberFormatException | IndexOutOfBoundsException e){
                System.out.println("\nIntroduce un valor válido\n");
            }
        }
        System.out.println("Vuelve pronto");
    }
}
