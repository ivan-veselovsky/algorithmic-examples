package edu.find_file;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

class FindFileTest {

   @Test
   void find() {
      FindFile findFile = new FindFile("info.txt~");

      String result = findFile.find("/media/ivan/ext4-data");
      System.out.println(result);

      then(result).isEqualTo("/media/ivan/ext4-data/java/info.txt~");
   }

   @Test
   void find_with_jdk_method() throws Exception {
      final String target = "info.txt~";
      try (Stream<Path> resultPathsStream = Files.find(
             Paths.get("/media/ivan/ext4-data"), 5, (p, a) ->
                target.equals(p.getFileName().toString())
             )) {
         resultPathsStream.forEachOrdered(System.out::println);
      }
   }
}