package com.architecture.movies.core.configuration;

import com.architecture.movies.core.domain.movies.outbound.MoviesProvider;
import com.architecture.movies.core.domain.movies.service.MoviesService;
import com.architecture.movies.core.domain.movies.service.MoviesServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public MoviesService moviesService(final MoviesProvider moviesProvider) {
        return new MoviesServiceImpl(moviesProvider);
    }
}
