package com.reactivestream.reactive.stream;

import reactor.core.publisher.Flux;

import java.util.List;

public class IfEmpty {


    public Flux<String> namesFlux_defaultIfEmpty(int stringLength)
    {
        return  Flux
                .fromIterable(List.of("alex","ben","chloe"))

                .filter(s->s.length()>stringLength)
                .defaultIfEmpty("default")
                .log();

    }

    public Flux<String> namesFlux_switchIfEmpty(int stringLength)
    {
        return  Flux
                .fromIterable(List.of("alex","ben","chloe"))

                .filter(s->s.length()>stringLength)
                .switchIfEmpty(Flux.just("default"))
                .log();

    }

}
