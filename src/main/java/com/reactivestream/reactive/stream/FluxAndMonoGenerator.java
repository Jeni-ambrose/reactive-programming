package com.reactivestream.reactive.stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class FluxAndMonoGenerator {
    public Flux<String> namesFlux(){
        return Flux
                //.just("alex","ben","chloe")
                .fromIterable(List.of("alex","ben","chloe"))
                //.map(String::toUpperCase)
                .delayElements(Duration.ofSeconds(20))
                .log()
        ;
    }

    public Mono<String> namesMono(){
        return Mono.just("alex")
                //.log()
        ;
    }


    public Flux<String> namesFlux_flatMap(int stringLength)
    {
       return  Flux
                .fromIterable(List.of("alex","ben","chloe"))
                .map(String::toUpperCase)
                .filter(s->s.length()>stringLength)
               .flatMap(this::splitStringFlux)
                .log();

    }

    public Flux<String> namesFlux_flatMapAsync(int stringLength)
    {
        return  Flux
                .fromIterable(List.of("alex","ben","chloe"))
                .map(String::toUpperCase)
                .filter(s->s.length()>stringLength)
                .flatMap(this::splitStringFlux_withDelay)
                .log();

    }
    public Flux<String> namesFlux_concatMap(int stringLength)
    {
        return  Flux
                .fromIterable(List.of("alex","ben","chloe"))
                .map(String::toUpperCase)
                .filter(s->s.length()>stringLength)
                .concatMap(this::splitStringFlux_withDelay)
                .log();

    }




    private Flux<String> splitStringFlux(String name) {
       var charArray =  name.split("");
       return Flux.fromArray(charArray);
    }

    private Flux<String> splitStringFlux_withDelay(String name) {
        var charArray =  name.split("");
        return Flux.fromArray(charArray).delayElements(Duration.ofMillis(1000));
    }

    public Mono<List<String>> namesMono_flatMap(){
        return Mono.just("alex")
                .map(String::toUpperCase)
                .flatMap(this::splitStringMono);
    }

    private  Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    public static void main(String[] args) {

        FluxAndMonoGenerator fluxAndMonoGenerator = new FluxAndMonoGenerator();
        fluxAndMonoGenerator.namesFlux().subscribe(name->{
            System.out.println("Name is " + name);
        });
        fluxAndMonoGenerator
                .namesMono()
                .subscribe(name-> System.out.println("Name from mono "+ name));
        /*fluxAndMonoGenerator*/
        /*        .namesMono_flatMap()*/
        /*        .subscribe(name-> System.out.println("list of string from namesMono_flapMap "+name));*/
        
    }
}
