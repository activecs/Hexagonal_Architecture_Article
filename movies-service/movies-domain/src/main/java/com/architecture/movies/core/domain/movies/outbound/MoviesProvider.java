package com.architecture.movies.core.domain.movies.outbound;

import com.architecture.movies.core.domain.movies.model.Movie;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface MoviesProvider {
    @NotNull
    List<Movie> getPopulars();

    @NotNull
    List<Movie> getUpcoming();

    @NotNull
    Optional<Movie> getById(final String id);
}
