package edu.listofdepths;

import edu.common.NodeP;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ListOfDepths {

    static <T> List<List<NodeP<T>>> makeListOfDepths(NodeP<T> root) {
        List<NodeP<T>> levelList = List.of(root);
        List<List<NodeP<T>>> resultCollector = new LinkedList<>(List.of(levelList));
        while (true) {
            levelList = makeNextLevel(levelList);
            if (levelList.isEmpty()) {
                break;
            } else {
                resultCollector.add(levelList);
            }
        }
        return resultCollector;
    }

    static <T> List<NodeP<T>> makeNextLevel(List<NodeP<T>> levelList) {
        return levelList.stream()
                .flatMap(node -> Stream.of(
                        node.left(),
                        node.right()))
                .filter(Objects::nonNull)
                .toList();
    }
}
