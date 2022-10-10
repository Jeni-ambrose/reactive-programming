package com.reactivestream.reactive.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class BackPressureTest {
    @Test
    void testBackPressure() {
        var numberRange= Flux.range(1,100).log();
       /* numberRange
                .subscribe(num->{
                    log.info("number is {}", num);
                });*/

        numberRange.subscribe((new BaseSubscriber<Integer>() {



            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(2);
            }

            @Override
            protected void hookOnNext(Integer value) {
               // super.hookOnNext(value);
                log.info("number is {}", value);
                if (value==2)
                    cancel();
            }

            @Override
            protected void hookOnComplete() {
                //super.hookOnComplete();
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                //super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                log.info("cancelled");
            }

        }));


    }

    @Test
    public void testBackPressure_drop() throws Exception {

        Flux<Integer> numberRange = Flux.range(1, 100).log();

        CountDownLatch latch = new CountDownLatch(1);
        numberRange
                .onBackpressureDrop(x -> {
                    log.info("Dropped items are : {} ", x);
                }) // This operator does two things
                // 1. Makes the request as unbounded and stores the items in a queue
                // 2. drops the remaining elements that are not requested by the subscriber
                .subscribe(new BaseSubscriber<>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(2);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        log.info("Next Value is : {}", value);

                        if (value % 2 == 0 || value < 50) {
                            request(2);
                        } else {
                            cancel();
                        }

                    }
                    @Override
                    protected void hookOnError(Throwable throwable) {
                        log.error("Exception is : ", throwable);
                       latch.countDown();
                    }

                    @Override
                    protected void hookOnCancel() {
                        latch.countDown();
                    }

                    @Override
                    protected void hookOnComplete() {
                        latch.countDown();
                    }
                });
       // assertTrue(latch.await(5L, TimeUnit.SECONDS));
    }


}
