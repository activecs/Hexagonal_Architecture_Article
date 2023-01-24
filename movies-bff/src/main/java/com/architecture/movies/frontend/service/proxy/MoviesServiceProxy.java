package com.architecture.movies.frontend.service.proxy;

import com.architecture.movies.frontend.representation.Movies;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "movies", url = "${services.movies.url}:${services.movies.port}/api/movies")
public interface MoviesServiceProxy {

    @RequestMapping(method = RequestMethod.GET, path = "/populars")
    List<Movies> getPopular();

    @RequestMapping(method = RequestMethod.GET, path = "/upcoming")
    List<Movies> getUpcoming();

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    Movies getMovie(@PathVariable("id") long movieId);

}

