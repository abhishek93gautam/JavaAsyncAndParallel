package com.learnjava.parallelStreams;

import com.learnjava.util.DataSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamsExample {

    public List<String> stringTransform(List<String> namesList) {
        return namesList
                .parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public List<String> stringTransform_1(List<String> namesList, boolean isParallel) {

        Stream<String> nameStream = namesList.stream();
        if (isParallel) {
            nameStream.parallel();
        }
        return nameStream
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public List<String> string_toLowerCase(List<String> namesList) {
        return namesList
                .parallelStream()
                .map(name -> name.toLowerCase())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        List<String> namesList = DataSet.namesList();
        ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();
        startTimer();
        List<String> resultList = parallelStreamsExample.string_toLowerCase(namesList);
        log("ResultList " + resultList);
        timeTaken();
    }

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }
}
