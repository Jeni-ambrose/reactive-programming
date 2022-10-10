package com.reactivestream.reactive.stream;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class CombiningFluxTest {
    private CombiningFlux combiningFlux  = new CombiningFlux();
    @Test
    void concatWith() {
        var fluxConcat = combiningFlux.fluxConcat();
        StepVerifier.create(fluxConcat)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void mergeWith() {
        var fluxMerge = combiningFlux.fluxMerge();
        StepVerifier.create(fluxMerge)
                .expectNext("A", "D", "B", "E", "C", "F")
                //  .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }

}
