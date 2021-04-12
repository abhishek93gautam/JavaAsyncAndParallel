package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorldException {

    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public String helloworld_three_async_calls_handle() {
        startTimer();
        CompletableFuture<String> hello =  CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world =  CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture =  CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String rs = hello
                .handle((res, e) -> {
                    if (e!=null) {
                        log("Exception is : " + e.getMessage());
                        return "";
                    } else {
                        return res;
                    }

                })
                .thenCombine(world, (h, w) -> h+w)
                .handle((res, e) -> {
                    if (e!=null) {
                        log("Exception after world is : " + e.getMessage());
                        return "";
                    } else {
                        return res;
                    }

                })
                .thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return rs;
    }

    public String helloworld_three_async_calls_exceptionally() {
        startTimer();
        CompletableFuture<String> hello =  CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world =  CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture =  CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String rs = hello
                .exceptionally((e) -> {
                    log("Exception is : " + e.getMessage());
                    return "";
                })
                .thenCombine(world, (h, w) -> h+w)
                .exceptionally((e) -> {
                    log("Exception after world is : " + e.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return rs;
    }

    public String helloworld_three_async_calls_whenComplete() {
        startTimer();
        CompletableFuture<String> hello =  CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world =  CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture =  CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String rs = hello
                .whenComplete((res, e) -> {
                    if (e!=null) {
                        log("Exception is : " + e.getMessage());
                    }
                })
                .thenCombine(world, (h, w) -> h+w)
                .whenComplete((res, e) -> {
                    if (e!=null) {
                        log("Exception after world is : " + e.getMessage());
                    }
                })
                .exceptionally((e) -> {
                    log("Exception after thenCombine is : " + e.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return rs;
    }
}
