package edu.balancedbrackets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static edu.balancedbrackets.Result.isBalanced;
import static org.assertj.core.api.BDDAssertions.then;

class CountGroupsTest {
    @ParameterizedTest
    @MethodSource("cases")
    void testCases(String input, String expectedOutput) {
        String actual = isBalanced(input);
           then(actual).isEqualTo(expectedOutput);
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(  "{([])}",  "YES"),
                Arguments.of(  "{{[[(())]]}}",  "YES"),
                Arguments.of( "(()" ,  "NO"),
                Arguments.of( "]" ,  "NO"),
                Arguments.of( "{}}" ,  "NO"),
                Arguments.of( "{[(])}" ,  "NO"),
                Arguments.of( "({[]}))" ,  "NO")
        );
    }

    @Test
    void bigString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<1024; i++) {
            sb.append("({[");
        }
        for (int i=0; i<1024; i++) {
            sb.append("]})");
        }
        String input = sb.toString();

        String actual = isBalanced(input);
        then(actual).isEqualTo("YES");
    }

    @Test
    void bigStringIncorrect() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<1024; i++) {
            sb.append("({[");
        }
        for (int i=0; i<1024; i++) {
            if (i == 513) {
                sb.append("]})");
            } else {
                sb.append("])}");
            }
        }
        String input = sb.toString();

        String actual = isBalanced(input);
        then(actual).isEqualTo("NO");
    }

}