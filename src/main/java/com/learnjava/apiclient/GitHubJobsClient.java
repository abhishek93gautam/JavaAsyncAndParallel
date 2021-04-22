package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

public class GitHubJobsClient {

    private WebClient webClient;

    public GitHubJobsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<GitHubPosition> invokeGitHubJobApiWithPageNumber(int pageNumber,
                                                                 String description) {
        String uri = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("description", description)
                .queryParam("page", pageNumber)
                .buildAndExpand()
                .toUriString();

        log("Uri is : " + uri);

        List<GitHubPosition> gitHubPositions = webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(GitHubPosition.class)
                .collectList()
                .block();

        return gitHubPositions;
    }

    public List<GitHubPosition> invokeGitHubJobsApiUsingMultiplePageNum(List<Integer> pageNumbers,
                                                                       String desc) {

        startTimer();

        List<GitHubPosition> gitHubPositionList = pageNumbers.stream()
                .map(pageNumber -> invokeGitHubJobApiWithPageNumber(pageNumber, desc))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        timeTaken();
        return gitHubPositionList;
    }

    public List<GitHubPosition> invokeGitHubJobsApiUsingMultiplePageNum_cf(List<Integer> pageNumbers,
                                                                        String desc) {

        startTimer();

        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pageNumbers.stream()
                .map(pageNumber -> CompletableFuture.supplyAsync(() -> invokeGitHubJobApiWithPageNumber(pageNumber, desc)))
                .collect(Collectors.toList());

        List<GitHubPosition> gitHubPositionList = gitHubPositions.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        timeTaken();
        return gitHubPositionList;
    }

    public List<GitHubPosition> invokeGitHubJobsApiUsingMultiplePageNum_cf_approach2(List<Integer> pageNumbers,
                                                                           String desc) {

        startTimer();

        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pageNumbers.stream()
                .map(pageNumber -> CompletableFuture.supplyAsync(() -> invokeGitHubJobApiWithPageNumber(pageNumber, desc)))
                .collect(Collectors.toList());

        CompletableFuture<Void> cfAllOf =
                CompletableFuture.allOf(gitHubPositions.toArray(new CompletableFuture[gitHubPositions.size()]));

        List<GitHubPosition> gitHubPositionList = cfAllOf.thenApply(v -> gitHubPositions.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()))
                .join();

        timeTaken();
        return gitHubPositionList;
    }
}
