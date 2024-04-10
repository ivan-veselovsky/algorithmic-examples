package edu.sudoku;

import java.util.function.Supplier;

public class ValidateSudoku {

    public boolean isValidSudoku(final char[][] board) {
        Checker horisontal = new Checker();

        Checker[] vertical = new Checker[9];
        fill(vertical, Checker::new);

        Checker[] x33 = new Checker[3];
        fill(x33, Checker::new);

        assert board.length == 9;
        boolean result;
        for (int y = 0; y < board.length; y++) {
            char[] line = board[y];
            horisontal.reset();
            for (int x = 0; x < board.length; x++) {
                final char c = line[x]; // line
                result = horisontal.test(c);

                result = result && vertical[x].test(c);
                if (!result) {
                    return false;
                }

                int checker3x3index = x / 3;
                Checker x33checker = x33[checker3x3index];
                if ((y % 3) == 0 && (x % 3) == 0) {
                    x33checker.reset();
                }
                if (!x33checker.test(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    void fill(Object[] array, Supplier<?> sup) {
        for (int i = 0; i < array.length; i++) {
            array[i] = sup.get();
        }
    }

    static class Checker {
        private int x = 0; // maintains state as a bit set.
        //private int count;
        private boolean valid = true;

        public boolean test(char c) {
//            count++;
//            assert count <= 9;

            if (!valid) {
                return false;
            }
            if ('.' == c) {
                return true;    // empty cell
            }

            assert c >= '1';
            assert c <= '9';

            int copyOfX = x;
            x |= (1 << toBitNumber(c));
            valid = (copyOfX != x);

            return valid;
        }
        int toBitNumber(char c) {
            return (c - '0' - 1); // 0..8
        }
        void reset() {
            valid = true;
            //count = 0;
            x = 0;
        }
    }

}
