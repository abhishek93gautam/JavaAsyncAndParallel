package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

@AllArgsConstructor
public class CompletableFutureHelloWorld {

    private final HelloWorldService helloWorldService;

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorld_withSize() {
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(result -> result + result.length());
    }

    public String helloworld_multiple_async_calls() {
        startTimer();
        CompletableFuture<String> hello =  CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world =  CompletableFuture.supplyAsync(() -> helloWorldService.world());

        String rs = hello
                .thenCombine(world, (h, w) -> h+w)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return rs;
    }

    public String helloworld_three_async_calls() {
        startTimer();
        CompletableFuture<String> hello =  CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world =  CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture =  CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String rs = hello
                .thenCombine(world, (h, w) -> h+w)
                .thenCombine(hiCompletableFuture, (prev, curr) -> prev + curr)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return rs;
    }

    public String helloworld_three_async_calls_log() {
        startTimer();
        CompletableFuture<String> hello =  CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world =  CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture =  CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String rs = hello
                .thenCombine(world, (h, w) -> {
                    log("thenCombine hello/world");
                    return h+w;
                })
                .thenCombine(hiCompletableFuture, (prev, curr) -> {
                    log("thenCombine previous/current");
                    return prev + curr;
                })
                .thenApply(s -> {
                    log("thenApply toUpperCase");
                    return s.toUpperCase();
                })
                .join();

        timeTaken();

        return rs;
    }

    public String helloworld_three_async_calls_log_async() {
        startTimer();
        CompletableFuture<String> hello =  CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world =  CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture =  CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String rs = hello
                .thenCombineAsync(world, (h, w) -> {
                    log("thenCombine hello/world");
                    return h+w;
                })
                .thenCombineAsync(hiCompletableFuture, (prev, curr) -> {
                    log("thenCombine previous/current");
                    return prev + curr;
                })
                .thenApplyAsync(s -> {
                    log("thenApply toUpperCase");
                    return s.toUpperCase();
                })
                .join();

        timeTaken();

        return rs;
    }

    public String helloworld_three_async_calls_custom_threadpool() {
        startTimer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<String> hello =  CompletableFuture.supplyAsync(() -> helloWorldService.hello(), executorService);
        CompletableFuture<String> world =  CompletableFuture.supplyAsync(() -> helloWorldService.world(), executorService);
        CompletableFuture<String> hiCompletableFuture =  CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        }, executorService);

        String rs = hello
                .thenCombine(world, (h, w) -> {
                    log("thenCombine hello/world");
                    return h+w;
                })
                .thenCombine(hiCompletableFuture, (prev, curr) -> {
                    log("thenCombine previous/current");
                    return prev + curr;
                })
                .thenApply(s -> {
                    log("thenApply toUpperCase");
                    return s.toUpperCase();
                })
                .join();

        timeTaken();

        return rs;
    }

    public String helloworld_three_async_calls_custom_threadpool_async() {
        startTimer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<String> hello =  CompletableFuture.supplyAsync(() -> helloWorldService.hello(), executorService);
        CompletableFuture<String> world =  CompletableFuture.supplyAsync(() -> helloWorldService.world(), executorService);
        CompletableFuture<String> hiCompletableFuture =  CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        }, executorService);

        String rs = hello
                .thenCombineAsync(world, (h, w) -> {
                    log("thenCombine hello/world");
                    return h+w;
                }, executorService)
                .thenCombineAsync(hiCompletableFuture, (prev, curr) -> {
                    log("thenCombine previous/current");
                    return prev + curr;
                }, executorService)
                .thenApplyAsync(s -> {
                    log("thenApply toUpperCase");
                    return s.toUpperCase();
                }, executorService)
                .join();

        timeTaken();

        return rs;
    }

    public String helloWorld_4_async_calls() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });
        // Add the 4th CompletableFuture that returns a String "  Bye!"
        CompletableFuture<String> byeCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
           return " Bye!";
        });

        String hw = hello
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenCombine(byeCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }

    public CompletableFuture<String> helloWorld_thenCompose() {
        return CompletableFuture.supplyAsync(helloWorldService::hello)
                .thenCompose((prev) -> helloWorldService.worldFuture(prev));
                //.thenApply(String::toUpperCase);
    }

    public static void main(String[] args) {

        HelloWorldService helloWorldService = new HelloWorldService();

        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept((result) -> {
                    log("Result is " + result);
                }).join();

        log("Done");
    }
}
