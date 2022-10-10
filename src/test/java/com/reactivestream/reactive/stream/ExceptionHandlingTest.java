package com.reactivestream.reactive.stream;


import com.reactivestream.reactive.stream.exception.ServiceException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import javax.sql.rowset.serial.SerialException;

public class ExceptionHandlingTest {
    ExceptionHandling exceptionHandling = new ExceptionHandling();
    @Test
    void exceptionFlux() {
        var value = exceptionHandling.exception_Flux();
        StepVerifier.create(value)
                .expectNext("A","B","C")
                //.expectError(RuntimeException.class)
                //.expectError()
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    void exploreOnErrorReturn() {
        var value = exceptionHandling.exploreOnErrorReturn();
        StepVerifier.create(value)
                .expectNext("A","B","C","D")

                .verifyComplete();
    }

    @Test
    void exploreOnErrorResume() {

        var e = new IllegalStateException("Not a valid state");
        var value = exceptionHandling.exploreOnErrorResume(e);
        StepVerifier.create(value)
                .expectNext("A","B","C","D", "E","F")

                .verifyComplete();
    }

    @Test
    void exploreOnErrorResume_1() {

        var e = new IllegalStateException("Not a valid state");
        var value = exceptionHandling.exploreOnErrorResume(e);
        StepVerifier.create(value)
                .expectNext("A","B","C","D", "E","F")

                .verifyComplete();
    }

    @Test
    void exploreOnErrorContinue() {


        var value = exceptionHandling.exploreOnErrorContinue();
        StepVerifier.create(value)
                .expectNext("A","C")

                .verifyComplete();
    }

    @Test
    void exploreOnErrorMap() {


        var value = exceptionHandling.exploreOnErrorMap();
        StepVerifier.create(value)
                .expectNext("A")

                .expectError(ServiceException.class)
                .verify();
    }

    @Test
    void exceptionDebug() {
       // Hooks.onOperatorDebug();
        var value = exceptionHandling.exception_Flux();
        StepVerifier.create(value)
                .expectNext("A","B","C")
                //.expectError(RuntimeException.class)
                //.expectError()
                .expectErrorMessage("Exception Occurred")
                .verify();
    }



}
