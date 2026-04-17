package edu.find_file;

import lombok.SneakyThrows;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public record FindFile(String shortName) {

   /**
    * @return 1st found abs path, or null if not found.
    */
   public @Nullable String find(String startDirectory) {
      Path directoryPath = Paths.get(startDirectory);
      if (Files.notExists(directoryPath) || !Files.isDirectory(directoryPath)) {
         return null;
      }
      return findRecursive(directoryPath).orElse(null);
   }

   @SneakyThrows
   private Optional<String> findRecursive(final Path directoryPath) {
      try (Stream<Path> directoryContents = Files.list(directoryPath)) {
         return directoryContents.parallel()
                .map(this::targetFileAbsPathOrElseNull)
                .filter(Objects::nonNull)
                .findAny();
      }
   }

   private @Nullable String targetFileAbsPathOrElseNull(Path p) {
      if (Files.isDirectory(p)) {
         return findRecursive(p).orElse(null);
      } else if (Files.isRegularFile(p)) {
         String shortNameOfTheFile = p.getFileName().toString();
         if (shortName.equals(shortNameOfTheFile)) {
            return p.normalize().toAbsolutePath().toString();
         }
      }
      return null;
   };
}
