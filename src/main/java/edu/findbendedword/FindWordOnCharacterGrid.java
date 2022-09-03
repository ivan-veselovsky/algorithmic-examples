package edu.findbendedword;

import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class FindWordOnCharacterGrid {

    private final CharacterGrid characterGrid;

    FindWordOnCharacterGrid(char[][] charGrid) {
        characterGrid = new CharacterGrid(charGrid);
    }

    /**
     * The solution entry point.
     * @param word The word to find.
     * @return List of points, each point denotes the last character position in the word.
     * Points have references, so the given {@code word} must appear by traversing from each last character to the 1st.
     * The order of variants is arbitrary.
     *
     * TODO: revert the resultant lists, as viewing them in forward order is more convenient.
     */
    public List<Point> findWordPositions(String word) {
        List<Point> list = getMatchingPointsFirstLevel(word);
        for (int level = 1; level < word.length(); level++) {
            list = getMatchingPointsForLevel(list, word, level);
        }
        return list;
    }

    private List<Point> getMatchingPointsFirstLevel(final String word) {
        final char charToMatch = word.charAt(0);
        final List<Point> result = new LinkedList<>();
        characterGrid.traverse(p -> {
            if (characterGrid.isValidStartPosition(p, word)) {
                if (characterGrid.get(p) == charToMatch) {
                    result.add(p);
                }
                return 1; // ok, go further
            } else {
                return -1; // go to the next line
            }
        });
        return result;
    }

    private List<Point> getMatchingPointsForLevel(List<Point> previousLevel, String word, int level) {
        final char charToMatch = word.charAt(level);
        final List<Point> result = new LinkedList<>();

        for (Point point : previousLevel) {
            Point nextToTheRight = point.nextRight();
            if (characterGrid.get(nextToTheRight) == charToMatch) {
                result.add(nextToTheRight);
            }
            Point nextDown = point.nextDown();
            if (characterGrid.get(nextDown) == charToMatch) {
                result.add(nextDown);
            }
        }

        return result;
    }

    @RequiredArgsConstructor
    static class CharacterGrid {
        private final char[][] gridData;

        public char get(Point p) {
            if (p.x < width() && p.y < height()) {
                return gridData[p.y][p.x];
            } else {
                return 0;
            }
        }

        int width() {
            return gridData[0].length;
        }

        int height() {
            return gridData.length;
        }

        /**
         * Small optimization: we can skip points in the lower right corner
         * that have no chance to be a valid start point of the word,
         * because summary length to the right and down is not enough:
         */
        boolean isValidStartPosition(Point p, String word) {
            return width() - p.x + height() - p.y >= word.length();
        }

        void traverse(Function<Point, Integer> visitor) {
            for (int y = 0; y < height(); y++) {
                for (int x = 0; x < width(); x++) {
                    int response = visitor.apply(new Point(x, y, null));
                    if (response == 0) {
                        return; // finish the traverse
                    } else if (response < 0) {
                        break; // go to the next line
                    }
                }
            }
        }
    }

    /**
     * Represents a point with zero-based coordinates
     * and an optional reference to another point.
     */
    record Point(int x, int y, Point ref) {
        Point nextRight() {
            return new Point(x + 1, y, this);
        }

        Point nextDown() {
            return new Point(x, y + 1, this);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        public String deepToString() {
            if (ref == null) {
                return toString();
            } else {
                return this + " -> " + ref.deepToString();
            }
        }
    }
}
