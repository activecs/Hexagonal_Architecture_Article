package com.architecture.movies.frontend.representation;

import java.time.LocalDate;
import java.util.List;

public record Movies(String id,
                     String title,
                     String synopsys,
                     Rate rate,
                     LocalDate releaseDate,
                     Person director,
                     List<Person> actors) {

}

