package com.architecture.movies.frontend.controller;

import com.architecture.movies.frontend.representation.Movies;
import com.architecture.movies.frontend.service.proxy.MoviesServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class FrontendController {

    private final MoviesServiceProxy client;

    public FrontendController(MoviesServiceProxy client) {
        this.client = client;
    }

    @GetMapping("/popular")
    public List<Movies> getPopularMovies(Model model) {
        log.info("Getting popular movies from backend service");
        return client.getPopular();
    }

    @GetMapping("/upcoming")
    public List<Movies> getUpcomingMovies(Model model) {
        log.info("Getting upcoming movies from backend service");
        return client.getUpcoming();
    }

    // ".+" is necessary to capture URI with filename extension
    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> file(@PathVariable String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }

}

