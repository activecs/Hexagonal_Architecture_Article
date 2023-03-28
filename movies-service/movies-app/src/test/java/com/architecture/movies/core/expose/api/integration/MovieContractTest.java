package com.architecture.movies.core.expose.api.integration;

import com.architecture.movies.core.expose.api.MovieController;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles(profiles = "test")
@AutoConfigureMessageVerifier
public abstract class MovieContractTest {

    @Autowired
    private MovieController movieController;
    @MockBean
    private TmdbApi tmdbApi;
    @Mock
    private TmdbMovies tmdbMovies;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(movieController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
        mockTmdbApi();
    }

    private void mockTmdbApi() {
        final MovieDb movieDb = createSampleMovie();
        final List<MovieDb> movieDbs = List.of(movieDb);
        final MovieResultsPage movieResultsPage = new MovieResultsPage();
        movieResultsPage.setResults(movieDbs);
        when(tmdbApi.getMovies()).thenReturn(tmdbMovies);
        when(tmdbMovies.getPopularMovies("en", 1)).thenReturn(movieResultsPage);
        when(tmdbMovies.getMovie(intThat(id-> id != 404), any(String.class))).thenReturn(movieDb);
        when(tmdbMovies.getUpcoming(eq("en"), eq(1), any(String.class))).thenReturn(movieResultsPage);
    }

    private static MovieDb createSampleMovie() {
        final MovieDb movieDb = new MovieDb();
        movieDb.setId(11);
        movieDb.setTitle("Interstellar");
        movieDb.setOverview("""
                In 2067, crop blights and dust storms threaten humanity's survival. Cooper, a widowed engineer and former NASA pilot turned farmer, lives with his father-in-law, Donald, his 15-year-old son, Tom, and 10-year-old daughter, Murphy "Murph". After a dust storm, patterns inexplicably appear in the dust covering Murphy's bedroom, which she thinks is the work of a ghost. Cooper deduces the patterns were caused by gravity variations and they represent geographic coordinates in binary code. Cooper follows the coordinates to a secret NASA facility headed by Professor John Brand.
                """);
        movieDb.setPopularity(4.7f);
        movieDb.setReleaseDate("2014-10-26");
        return movieDb;
    }
}
