package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return movie by id"
    request{
        method GET()
        url(regex("/movies/404"))
    }
    response {
        status 204
    }
}