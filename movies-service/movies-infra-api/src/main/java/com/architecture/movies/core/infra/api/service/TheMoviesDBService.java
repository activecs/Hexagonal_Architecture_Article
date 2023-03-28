package com.architecture.movies.core.infra.api.service;

import com.architecture.movies.core.domain.movies.model.Movie;
import com.architecture.movies.core.domain.movies.outbound.MoviesProvider;
import com.architecture.movies.core.infra.api.mapper.MovieMapper;
import info.movito.themoviedbapi.TmdbApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheMoviesDBService implements MoviesProvider {

    private final TmdbApi tmdbApi;

    @Override
    public List<Movie> getPopulars() {
        return tmdbApi.getMovies().getPopularMovies("en", 1)
                .getResults()
                .stream()
                .map(MovieMapper::toDomain)
                .toList();
    }

    @Override
    public List<Movie> getUpcoming() {
        return tmdbApi.getMovies().getUpcoming("en", 1, "UA")
                .getResults()
                .stream()
                .map(MovieMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Movie> getById(String id) {
        return Optional.ofNullable(MovieMapper
                .toDomain(tmdbApi.getMovies().getMovie(Integer.parseInt(id), "en")));
    }
}
