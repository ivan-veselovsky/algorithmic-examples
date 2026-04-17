package edu;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

public class TestDemoSpliterator {

   @Test
   void demo_tryAdvance() {
      // given
      List<String> list = List.of("a", "b", "c");
      Spliterator<String> sp = list.spliterator();
      List<String> actualResult = new LinkedList<>();

      // when
      while (sp.tryAdvance(s -> {
         System.out.println("Processing: " + s);
         actualResult.add(s);
      })) {
         // noop
      }

      // then
      then(actualResult).contains("a", "b", "c");
   }

   @Test
   void demo_forEachRemaining() {
      List<String> list = List.of("a", "b", "c");
      Spliterator<String> sp = list.spliterator();
      List<String> actualResult = new LinkedList<>();

      // when
      sp.forEachRemaining(s -> {
         System.out.println("Processing: " + s);
         actualResult.add(s);
      });

      // then
      then(actualResult).contains("a", "b", "c");
   }

   @Test
   void demo_trySplit() {
      // given
      val originalList = IntStream.range(0, 9).boxed().toList();
      List<Spliterator<Integer>> spliterators = new LinkedList<>();

      // when
      Spliterator<Integer> spliterator = originalList.spliterator();
      then(spliterator.estimateSize()).isEqualTo(9);
      then(spliterator.getExactSizeIfKnown()).isEqualTo(9);
      while (spliterator != null) {
         spliterators.addFirst(spliterator);
         spliterator = spliterator.trySplit();
      }

      // then
      List<Integer> actualListAfterSplit = new LinkedList<>();
      spliterators.forEach(sp -> {
         System.out.println("====");
         sp.forEachRemaining(x -> {
            actualListAfterSplit.add(x);
            System.out.println(x);
         });
      });
      then(actualListAfterSplit).containsExactly(originalList.toArray(Integer[]::new));
   }

   @Test
   void demo_split_infinite_stream() {
      Stream<Double> stream = Stream.generate(Math::random);
      Spliterator<Double> sp = stream.spliterator();
      Spliterator<Double> split = sp.trySplit();

      then(split).isNotNull();
      //then(split.estimateSize()).isEqualTo(Long.MAX_VALUE); // ? has strange value
      then(split.getExactSizeIfKnown()).isEqualTo(-1L);
   }
}
