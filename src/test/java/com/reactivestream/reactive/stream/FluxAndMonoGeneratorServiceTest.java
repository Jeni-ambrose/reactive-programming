package com.reactivestream.reactive.stream;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGenerator fluxAndMonoGenerator = new FluxAndMonoGenerator();

    @Test
    void namesFlux() {
        var namesFlux = fluxAndMonoGenerator.namesFlux();
        StepVerifier
                .create(namesFlux)
                .expectNext("alex", "ben", "chloe")
                //.expectNextCount(3)

                .verifyComplete();

    }





    @Test
    void namesFluxFlatMap() {
        int stringLength = 3;
        var namesFluxFlatMap = fluxAndMonoGenerator.namesFlux_flatMap(stringLength);
        StepVerifier.create(namesFluxFlatMap)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatMap_async() {
        int stringLength = 3;
        var namesFluxFlatMap = fluxAndMonoGenerator.namesFlux_flatMapAsync(stringLength);
        StepVerifier.create(namesFluxFlatMap)
                //.expectNext("A","L","E","X","C","H","L","O", "E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatMap() {
        int stringLength = 3;
        var namesFluxFlatMap = fluxAndMonoGenerator.namesFlux_concatMap(stringLength);
        StepVerifier.create(namesFluxFlatMap)
                .expectNext("A", "L", "E", "X", "C", "H", "L", "O", "E")
                //.expectNextCount(6)
                .verifyComplete();
    }



}

