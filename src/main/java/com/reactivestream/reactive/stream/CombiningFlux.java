package com.reactivestream.reactive.stream;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class CombiningFlux {
    public Flux<String> fluxConcat()
    {
        var abcFlux = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E","F")
                .delayElements(Duration.ofMillis(125));
        //return abcFlux.concatWith(defFlux).log();
        return Flux.concat(abcFlux,defFlux).log();
    }
    public Flux<String> fluxMerge()
    {
        var abcFlux = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100)). log();
        var defFlux = Flux.just("D", "E","F")
                .delayElements(Duration.ofMillis(125)).log();
        //return abcFlux.mergeWith(defFlux).log();
        return Flux.merge(abcFlux,defFlux);
    }
}
