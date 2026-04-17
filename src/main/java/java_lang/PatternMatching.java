package java_lang;

public class PatternMatching {

   private static final Object xxx = "f_ck";

   String foo(Object obj) {
         return switch (obj) {
            case Integer i -> "Integer value: " + i;
            case String s  -> "String length: " + s.length();
            case null      -> "Null value";
            default        -> "Unknown type";
         };
   }

   String describe(Object object) {
      return switch (object) {
         //case Point p -> "" +  p.x() + p.y();
         case Point(int x, int y) -> "" +  x + y;
         default -> "";
      };
   }
}
