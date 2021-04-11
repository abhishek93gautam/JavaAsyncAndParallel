package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld =
            new CompletableFutureHelloWorld(helloWorldService);
    @Test
    void helloWorld() {
        //given
        //when
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld();
        //then
        completableFuture
                .thenAccept((s) -> {
                    assertEquals("HELLO WORLD", s);
                }).join();
    }

    @Test
    void helloWorld_withSize() {
        //given
        //when
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld_withSize();
        //then
        completableFuture
                .thenAccept((s) -> {
                    assertEquals("hello world11", s);
                }).join();
    }

    @Test
    void helloworld_multiple_async_calls() {
        //given
        //when
        String helloworld = completableFutureHelloWorld.helloworld_multiple_async_calls();
        //then
        assertEquals("HELLO WORLD!", helloworld);
    }

    @Test
    void helloworld_three_async_calls() {
        //given
        //when
        String helloworld = completableFutureHelloWorld.helloworld_three_async_calls();
        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", helloworld);
    }

    @Test
    void helloWorld_4_async_calls() {
        //given
        //when
        String helloworld = completableFutureHelloWorld.helloWorld_4_async_calls();
        //then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", helloworld);
    }

    @Test
    void helloWorld_thenCompose() {
        //given
        startTimer();
        //when
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld_thenCompose();
        //then
        completableFuture
                .thenAccept((s) -> {
                    assertEquals("hello world!", s);
                }).join();
        timeTaken();
    }
}