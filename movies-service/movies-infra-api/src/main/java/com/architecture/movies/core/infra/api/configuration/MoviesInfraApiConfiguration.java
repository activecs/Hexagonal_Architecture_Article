package com.architecture.movies.core.infra.api.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        AppProperties.class
})
public class MoviesInfraApiConfiguration {
}
