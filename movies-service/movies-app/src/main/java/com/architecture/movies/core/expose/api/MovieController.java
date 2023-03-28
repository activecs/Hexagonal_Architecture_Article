package com.architecture.movies.core.expose.api;

import com.architecture.movies.core.domain.movies.error.MoviesNotFoundException;
import com.architecture.movies.core.domain.movies.model.Movie;
import com.architecture.movies.core.domain.movies.service.MoviesService;
import com.architecture.movies.core.error.ParametersException;
import io.micrometer.tracing.annotation.NewSpan;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    private final MoviesService moviesService;

    @GetMapping("/populars")
    public ResponseEntity<List<Movie>> getPopulars() {
        final List<Movie> movies = moviesService.getPopulars();
        log.info("Getting popular movies from backend service-> [{}]", movies.size());
        return ResponseEntity
                .ok(movies);
    }

    @NewSpan("movies.upcoming")
    @GetMapping("/upcoming")
    public ResponseEntity<List<Movie>> getUpcoming() {
        final List<Movie> movies = moviesService.getUpcoming();
        log.info("Getting upcoming movies from backend service-> [{}]", movies.size());
        return ResponseEntity
                .ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable("id") final String id) {
        if (!StringUtils.hasText(id)) {
            throw new ParametersException("[id] parameter is mandatory");
        }
        final Movie movie = moviesService.getById(id);
        return ResponseEntity
                .ok(movie);
    }

    @ExceptionHandler(MoviesNotFoundException.class)
    public ResponseEntity<String> handleMoviesNotFoundException(MoviesNotFoundException ex) {
        return ResponseEntity
                .status(204)
                .build();
    }
}
