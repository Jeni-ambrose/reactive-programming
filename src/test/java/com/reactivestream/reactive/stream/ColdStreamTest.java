package com.reactivestream.reactive.stream;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static reactor.core.publisher.Mono.delay;


public class ColdStreamTest {
    @Test
    void coldStream() {
        //var flux = Flux.just("A","B","C","D");
        var flux = Flux.range(1, 10);

        flux.subscribe(val -> System.out.println("data from subscriber 1 : " + val));

        flux.subscribe(val -> System.out.println("data from subscriber 2 : " + val));

    }

    @Test
    void hotStream() throws InterruptedException {
        Flux<Integer> stringFlux = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<Integer> connectableFlux = stringFlux.publish();
        connectableFlux.connect();
        //Thread.sleep(3000);
        connectableFlux.subscribe(s -> System.out.println("Subscriber 1 : " + s));
        Thread.sleep(3000);
        connectableFlux.subscribe(s -> System.out.println("Subscriber 2 : " + s)); // does not get the values from beginning
        Thread.sleep(10000);

    }

    @Test
    public void hotPublisherTest_autoConnect() throws InterruptedException {

        Flux<Integer> stringFlux = Flux.range(1, 10)
                .doOnSubscribe(s -> {
                    System.out.println("Subscription started");
                })
                .delayElements(Duration.ofSeconds(1));

        // this "autoConnect" call needs to be connected to the publish method itself
        var hotSource = stringFlux.publish().autoConnect(2);

        var disposable = hotSource.subscribe(s -> System.out.println("Subscriber 1 : " + s));
        Thread.sleep(2000);
        var disposable1 = hotSource.subscribe(s -> System.out.println("Subscriber 2 : " + s)); // does not get the values from beginning
        System.out.println("Two subscribers connected");
        Thread.sleep(5000);
        // disposable.dispose();
        //disposable1.dispose();
        hotSource.subscribe(s -> System.out.println("Subscriber 3 : " + s)); // does not get the values from beginning
        Thread.sleep(10000);


    }

    @Test
    public void hotPublisherTest_refConnect() throws InterruptedException {

        Flux<Integer> stringFlux = Flux.range(1, 10)
                .doOnSubscribe(s -> {
                    System.out.println("Subscription reeived");
                })
                .doOnCancel(() -> {
                    System.out.println("Received Cancel Signal");
                })
                .delayElements(Duration.ofSeconds(1));

        // this "refCount" call needs to be connected to the publish method itself
        var hotSource = stringFlux.publish().refCount(2);

        var disposable = hotSource.subscribe(s -> System.out.println("Subscriber 1 : " + s));
        Thread.sleep(2000);
        var disposable1 = hotSource.subscribe(s -> System.out.println("Subscriber 2 : " + s)); // does not get the values from beginning
        System.out.println("Two subscribers connected");
        Thread.sleep(4000);
        disposable.dispose();
        disposable1.dispose(); // this cancels the whole subscription
        //  This does not start the subscriber to emit the values,because of minimum of 2 subscribers needed.
        hotSource.subscribe(s -> System.out.println("Subscriber 3 : " + s));

        // Run by showing the above code and then enable the below code and run it.
        Thread.sleep(2000);
        // By adding the fourth subscriber enables the minimum subscriber condition and it starts to emit the values
        hotSource.subscribe(s -> System.out.println("Subscriber 4: " + s));
        Thread.sleep(12000);
    }
}
