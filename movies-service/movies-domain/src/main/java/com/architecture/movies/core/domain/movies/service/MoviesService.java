package com.architecture.movies.core.domain.movies.service;

import com.architecture.movies.core.domain.movies.model.Movie;

import java.util.List;

public interface MoviesService {
    List<Movie> getPopulars();

    List<Movie> getUpcoming();

    Movie getById(final String id);
}
