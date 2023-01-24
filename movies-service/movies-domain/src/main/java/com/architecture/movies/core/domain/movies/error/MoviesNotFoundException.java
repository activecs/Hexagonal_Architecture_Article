package com.architecture.movies.core.domain.movies.error;

public class MoviesNotFoundException extends RuntimeException {

    public MoviesNotFoundException(final String errorMessage) {
        super(errorMessage);
    }
}
