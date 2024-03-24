package edu.listofdepths;

import edu.common.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ListOfDepths {

    static <T> List<List<Node<T>>> makeListOfDepths(Node<T> root) {
        List<Node<T>> levelList = List.of(root);
        List<List<Node<T>>> resultCollector = new LinkedList<>(List.of(levelList));
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

    static <T> List<Node<T>> makeNextLevel(List<Node<T>> levelList) {
        return levelList.stream()
                .flatMap(node -> Stream.of(
                        node.left(),
                        node.right()))
                .filter(Objects::nonNull)
                .toList();
    }
}
