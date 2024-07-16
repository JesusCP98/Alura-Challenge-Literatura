package com.alura.challenge_literatula.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorsData
        (@JsonAlias("birth_year") Long birthYear,
        @JsonAlias("death_year") Long deathYear,
        @JsonAlias("name") String name ){
}
