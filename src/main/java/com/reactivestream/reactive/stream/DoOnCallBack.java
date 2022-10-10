package com.reactivestream.reactive.stream;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class DoOnCallBack {
    /*These operators allow you to peek into the events that are emitted by the Publisher( Flux or Mono)

    These are  also called side effect operator --> does not change the original sequence of the publishers

    used for debugging
    send a notification when a reactive stream complete or errors out


     */

    public Flux<String> namesFlux(int stringLength)
    {

            return  Flux
                    .fromIterable(List.of("alex","ben","chloe"))
                    .map(String::toUpperCase)
                    .filter(s->s.length()>stringLength)
                    .doOnNext(name-> {
                        System.out.println("name is "+ name);
                        name.toLowerCase();
                    })
                    .doOnSubscribe(subscription -> System.out.println("Subscription is "+subscription))
                    .doOnComplete(()-> System.out.println("Inside complete Call"))
                    .doFinally(signalType -> {
                        System.out.println("signal type "+ signalType);
                    }) //invoked on completion or on error

                    .log();


    }

    private Flux<String> splitStringFlux_withDelay(String name) {
        var charArray =  name.split("");
        return Flux.fromArray(charArray).delayElements(Duration.ofMillis(1000));
    }




}
