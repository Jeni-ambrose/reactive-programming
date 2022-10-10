package com.reactivestream.reactive.stream;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class FluxImmutablityTest{
    private FluxImmutablity fluxImmutablity = new FluxImmutablity();
    @Test
    void nameMono() {
        var namesFlux = fluxImmutablity.namesFluxImmutablity();
        StepVerifier
                .create(namesFlux)
                .expectNext("alex","ben","chloe")
                //.expectNextCount(3)

                .verifyComplete();

    }

}
