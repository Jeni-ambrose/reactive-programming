package com.reactivestream.reactive.stream;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class IfEmptyTest {
    private IfEmpty ifEmpty = new IfEmpty();
    @Test
    void defaultIfEmpty() {
        int stringLength = 7;
        var fluxMerge = ifEmpty.namesFlux_defaultIfEmpty(stringLength);
        StepVerifier.create(fluxMerge)
                .expectNext("default")
                //  .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }
    @Test
    void switchIfEmpty() {
        int stringLength = 7;
        var fluxMerge = ifEmpty.namesFlux_switchIfEmpty((stringLength));
        StepVerifier.create(fluxMerge)
                .expectNext("default")
                //  .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }
}
