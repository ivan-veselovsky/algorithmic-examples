package edu.streams;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Stream.of(List.of(1,2,3,4), List.of(5,6,7,8))
                .flatMap(Collection::stream)
                .forEach(System.out::println);
    }
}
