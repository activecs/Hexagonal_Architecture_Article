package com.architecture.movies.core.domain.movies.service;

import com.architecture.movies.core.domain.movies.error.MoviesNotFoundException;
import com.architecture.movies.core.domain.movies.model.Movie;
import com.architecture.movies.core.domain.movies.outbound.MoviesProvider;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.NewSpan;
import io.micrometer.tracing.annotation.SpanTag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MoviesServiceImpl implements MoviesService {

    private final MoviesProvider moviesProvider;
    private final Tracer tracer;

    @Override
    public List<Movie> getPopulars() {
        var newSpan = tracer.nextSpan().name("MoviesServiceImpl.getPopulars");
        try (var ignored = this.tracer.withSpan(newSpan.start())) {
            newSpan.event("Going to get popular movies");
            List<Movie> movies = moviesProvider.getPopulars();
            if (CollectionUtils.isEmpty(movies)) {
                throw new MoviesNotFoundException("Populard movies not found");
            }
            return movies;
        } finally {
            newSpan.event("Popular movies retrieved");
            newSpan.end();
        }
    }

    @Override
    public List<Movie> getUpcoming() {
        List<Movie> movies = moviesProvider.getUpcoming();
        if (CollectionUtils.isEmpty(movies)) {
            throw new MoviesNotFoundException("Lastet movies not found");
        }
        return movies;
    }

    @Override
    public Movie getById(String id) {
        Optional<Movie> movie = moviesProvider.getById(id);
        if (movie.isEmpty()) {
            throw new MoviesNotFoundException(String.format("Movie with id [%s] not found", id));
        }
        return movie.get();
    }
}
