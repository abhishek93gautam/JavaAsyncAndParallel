package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitHubJobsClientTest {

    WebClient webClient =  WebClient.create("https://jobs.github.com/");
    GitHubJobsClient gjc = new GitHubJobsClient(webClient);

    @Test
    void invokeGitHubJobApiWithPageNumber() {
        //given
        int pageNum = 1;
        String desc = "Java";

        //when
        List<GitHubPosition> gitHubPositionList = gjc.invokeGitHubJobApiWithPageNumber(pageNum, desc);

        //then
        assertTrue(gitHubPositionList.size() > 0);
        gitHubPositionList
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGitHubJobsApiUsingMultiplePageNum() {
        //given
        List<Integer> pageNumList = List.of(1,2,3);
        String desc = "Java";

        //when
        List<GitHubPosition> gitHubPositionList = gjc.invokeGitHubJobsApiUsingMultiplePageNum(pageNumList, desc);

        //then
        assertTrue(gitHubPositionList.size() > 0);
        gitHubPositionList
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGitHubJobsApiUsingMultiplePageNum_cf() {
        //given
        List<Integer> pageNumList = List.of(1,2,3);
        String desc = "Java";

        //when
        List<GitHubPosition> gitHubPositionList = gjc.invokeGitHubJobsApiUsingMultiplePageNum_cf(pageNumList, desc);

        //then
        assertTrue(gitHubPositionList.size() > 0);
        gitHubPositionList
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGitHubJobsApiUsingMultiplePageNum_cf_approach2() {
        //given
        List<Integer> pageNumList = List.of(1,2,3);
        String desc = "Java";

        //when
        List<GitHubPosition> gitHubPositionList = gjc.invokeGitHubJobsApiUsingMultiplePageNum_cf_approach2(pageNumList, desc);

        //then
        assertTrue(gitHubPositionList.size() > 0);
        gitHubPositionList
                .forEach(Assertions::assertNotNull);
    }
}