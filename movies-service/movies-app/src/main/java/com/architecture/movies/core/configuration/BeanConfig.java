package com.architecture.movies.core.configuration;

import com.architecture.movies.core.domain.movies.outbound.MoviesProvider;
import com.architecture.movies.core.domain.movies.service.MoviesService;
import com.architecture.movies.core.domain.movies.service.MoviesServiceImpl;
import io.micrometer.tracing.Tracer;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporterBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Configuration
public class BeanConfig {


    @Bean
    public MoviesService moviesService(final MoviesProvider moviesProvider, final Tracer tracer) {
        return new MoviesServiceImpl(moviesProvider, tracer);
    }

    @Bean
    @ConditionalOnProperty(value = "management.tracing.exporter.jaeger.enabled", havingValue = "true")
    public JaegerGrpcSpanExporter otelJaegerGrpcSpanExporter(Environment environment) {
        JaegerGrpcSpanExporterBuilder builder = JaegerGrpcSpanExporter.builder();
        String endpoint = environment.getProperty("management.tracing.exporter.jaeger.endpoint");
        if (StringUtils.hasText(endpoint)) {
            builder.setEndpoint(endpoint);
        }
        Long timeout = environment.getProperty("management.tracing.exporter.jaeger.timeout", Long.class);
        if (timeout != null) {
            builder.setTimeout(timeout, TimeUnit.MILLISECONDS);
        }
        return builder.build();
    }
}
