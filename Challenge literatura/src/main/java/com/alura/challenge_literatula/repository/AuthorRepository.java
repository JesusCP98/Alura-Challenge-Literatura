package com.alura.challenge_literatula.repository;

import com.alura.challenge_literatula.model.Author;
import com.alura.challenge_literatula.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByNameContainsIgnoreCase(String name);

    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND :year <= a.deathYear")
    List<Author> aliveAtYear( Long year );

    @Query("SELECT b FROM Author a JOIN a.books b ORDER BY b.downloadCount DESC LIMIT 10")
    List<Book> top10Books();


    @Query("SELECT b FROM Author a JOIN a.books b")
    List<Book> allBooks();

    @Query("SELECT b FROM Author a JOIN a.books b WHERE b.language ILIKE %:language%")
    List<Book> findByLanguage(String language);



}