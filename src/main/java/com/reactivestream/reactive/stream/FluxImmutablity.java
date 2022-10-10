package com.reactivestream.reactive.stream;

import reactor.core.publisher.Flux;

import java.util.List;


public class FluxImmutablity {
    public Flux<String> namesFluxImmutablity(){
        var namesFlux =  Flux
                .fromIterable(List.of("alex","ben","chloe"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;

    }
}
