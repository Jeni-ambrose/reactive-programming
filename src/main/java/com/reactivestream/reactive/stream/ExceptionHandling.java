package com.reactivestream.reactive.stream;

import com.reactivestream.reactive.stream.exception.ServiceException;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.sql.SQLOutput;
import java.time.Duration;

import static reactor.core.publisher.Mono.delay;


public class ExceptionHandling {

    public Flux<String> exception_Flux()
    {
       return Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
               .checkpoint("Exception at check point")
                .concatWith(Flux.just("D"))
                .log();
    }
    public Flux<String> exploreOnErrorReturn()
    {
        return Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .onErrorReturn("D")
                .log();
    }

    public Flux<String> exploreOnErrorResume(Exception ex)
    {
        var recoveryFlux = Flux.just("D","E","F");
        return Flux.just("A","B","C")
                .concatWith(Flux.error(ex))
                .onErrorResume(e->{
                    System.out.println("Excception is "+ e);
                    if(e instanceof IllegalStateException ) {
                        return recoveryFlux;
                    }
                    else
                        return Flux.error(e);
                })

                .log();
    }


    public Flux<String> exploreOnErrorContinue()
    {

        return Flux.just("A","B","C")

                .map(val ->{
                    if(val.equals("B"))
                        throw new RuntimeException("Exception Occurred");
                    return val;
                })
                .onErrorContinue((e, val)->{
                    System.out.println("Exception is "+ e);
                    System.out.println("Value is "+ val);
                }
                )

                .log();
    }

    public Flux<String> exploreOnErrorMap()
    {
        return Flux.just("A", "B","C")
                .map(val ->{
                    if(val.equals("B"))
                        throw new RuntimeException("Exception Occurred");
                    return val;
                })
                .onErrorMap(ex -> {
                    System.out.println("Exception is "+ex);
                    return new ServiceException("Exception Occurred");
                });
    }


}
