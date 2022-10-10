package com.reactivestream.reactive.stream;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class DoOnCallbackTest {
    DoOnCallBack doOnCallBack = new DoOnCallBack();

    @Test
    void namesFlux() {
        int stringLength = 3;
        var namesFlux = doOnCallBack.namesFlux(stringLength);
        StepVerifier
                .create(namesFlux)
                .expectNext("ALEX", "CHLOE")
                //.expectNextCount(3)

                .verifyComplete();

    }

}
