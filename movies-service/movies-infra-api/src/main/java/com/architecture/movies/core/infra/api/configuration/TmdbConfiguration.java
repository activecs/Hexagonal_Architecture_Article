package com.architecture.movies.core.infra.api.configuration;

import info.movito.themoviedbapi.TmdbApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Configuration
public class TmdbConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbConfiguration.class);

    @Async
    @Bean
    public TmdbApi tmdbApi(final AppProperties appProperties) {
        final String key = appProperties.getKey();
        LOG.info("TmdbApi key: " + key);
        return new TmdbApi(key);
    }
}
