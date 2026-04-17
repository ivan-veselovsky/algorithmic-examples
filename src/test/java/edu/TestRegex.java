package edu;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class TestRegex {

    @Test
    void basic() {
        String base = "a[b]c";
        String replaced = base.replace("[", "\\[").replace("]", "\\]");
        boolean v = base.matches(replaced);
        then(v).isTrue();
    }

    @Test
    void nested() {
        String base = "aa[a[b]c]ee";
        String replaced = base.replace("[", "\\[").replace("]", "\\]");
        boolean v = base.matches(replaced);
        then(v).isTrue();
    }

    @Test
    void nested3() {
        String base = "[+]";
        String replaced = base.replace("[", "\\[").replace("]", "\\]");
        boolean v = base.matches(replaced);
        then(v).isTrue();
    }

}
