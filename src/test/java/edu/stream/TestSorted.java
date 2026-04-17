package edu.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.lang.System.out;

public class TestSorted {

   @Test
   void test1() {
      Stream.of("Tim", "Jim", "Peter", "Ann", "Mary")
             .peek(name -> out.print(" 0." + name))
             .filter(name -> name.length() == 3)
             .peek(name -> out.print(" 1." + name))
             .sorted()
             .peek(name -> out.print(" 2." + name))
             .limit(2)
             .forEach(name -> out.print(" 3." + name));
   }
}
