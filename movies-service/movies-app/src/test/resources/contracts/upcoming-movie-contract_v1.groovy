package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return upcoming movies"
    request{
        method GET()
        url("/movies/upcoming")
    }
    response {
        bodyMatchers {
            xPath("movies/movie[1]/id", byRegex( "[0-9]+"))
            xPath("movies/movie[1]/title", byRegex(".*"))
        }
        status 200
    }
}