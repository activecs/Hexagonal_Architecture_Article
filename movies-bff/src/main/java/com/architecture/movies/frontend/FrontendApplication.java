package com.architecture.movies.frontend;

import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporterBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableFeignClients
public class FrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
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
