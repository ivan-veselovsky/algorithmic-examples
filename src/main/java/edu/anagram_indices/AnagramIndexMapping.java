package edu.anagram_indices;

import java.util.*;

/**
 Write a Java 8 method named anagramIndices
 that takes two lists of strings, words1 and words2,
 and returns an array of integers representing an index mapping from words1 to words2.
 The mapping should indicate the index in words2-list
 where the i-th element in words1-list appears.
 If there are multiple answers, return any of them.

 Conditions:

 - Implement the anagramIndices method using Java 8 features.
 - Use Stream API.
 - Return an array containing the indices indicating the mapping from words1-list to words2-list.
 - If there are multiple valid answers, return any of them.

 Output:
 The result array should be [3, 2, 0, 1]:

 String[] words1 = {"apple", "banana", "orange", "grape", "peach"};
 String[] words2 = {"orange", "grape", "banana", "apple"};

 The word "apple" at index 0 in words1 appears at index 3 in words2.
 The word "banana" at index 1 in words1 appears at index 2 in words2.
 The word "orange" at index 2 in words1 appears at index 0 in words2.
 The word "grape" at index 3 in words1 appears at index 1 in words2.
 */
// 1) bruteforce: O(n^2)
// 2) sort: O(n * log(n)) runtime, + O(n) space
// 3) HashMap: O(n) , + O(n) space .
// 4) use trie : O(n) , + O(n) space.
public class AnagramIndexMapping {
    public static void main(String[] args) {
        String[] words1 = {"apple", "banana", "orange", "grape", "peach"};
        String[] words2 = {"orange", "grape", "banana", "apple"};

        int[] result = anagramIndices(words1, words2);

        System.out.print("Index mapping from words1 to words2: " + Arrays.toString(result));
    }

    public static int[] anagramIndices(String[] words1, String[] words2) {
        final Map<String, Integer> map = new HashMap<>(words2.length);

        for (int i = 0; i < words2.length; i++) {
            map.putIfAbsent(words2[i], i);
        }

        return Arrays.stream(words1)
                .map(map::get)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .toArray();
    }
}