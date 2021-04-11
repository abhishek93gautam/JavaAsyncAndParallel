package com.learnjava.parallelStreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamResultOrder {

    public static List<Integer> listOrder(List<Integer> inputList) {
        return inputList.parallelStream()
                .map(integer -> integer * 2)
                .collect(Collectors.toList());
    }

    public static Set<Integer> setOrder(Set<Integer> inputList) {
        return inputList.parallelStream()
                .map(integer -> integer * 2)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
//       List<Integer> inputList =  List.of(1,2,3,4,5,6,7,8);
//       log("input : " + inputList);
//       List<Integer> result = listOrder(inputList);
//       log("outputList : " + result);

        Set<Integer> inputList =  Set.of(1,2,3,4,5,6,7,8);
        log("inputSet : " + inputList);
        Set<Integer> result = setOrder(inputList);
        log("outputSet : " + result);
    }
}
