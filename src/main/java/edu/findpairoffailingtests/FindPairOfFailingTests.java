package edu.findpairoffailingtests;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A set of somehow dependent tests is given: exactly two of them being present together in the test run
 * give 'false' result, while the result is positive when these 2 tests are not present in the same run.
 * Tests are identified by indices, 0..N-1, where N is the total number of tests.
 * The function should return a couple of indices of the 2 tests that cause the failures.
 */
public class FindPairOfFailingTests {

    /**
     * In fact, this function may accept an array of arbitrary indices, but in practice 2 ranges are enough,
     * and this avoids index array creation.
     */
    @FunctionalInterface
    public interface RunTestsFunction {
        boolean runTests(int start1Inclusive, int end1Exclusive, int start2Inclusive, int end2Exclusive);
    }

    public record ArrayRunTestsFunction(int totalTests, int failureIndex1, int failureIndex2) implements RunTestsFunction {

        public ArrayRunTestsFunction {
            if (totalTests < 1) {
                throw new IllegalArgumentException();
            }
            Objects.checkIndex(failureIndex1, totalTests);
            Objects.checkIndex(failureIndex2, totalTests);
        }

        @Override
        public boolean runTests(int start1Inclusive, int end1Exclusive, int start2Inclusive, int end2Exclusive) {
            // both tests must fail simultaneously in the run:
            return containsAnyOfRanges(failureIndex1, start1Inclusive, end1Exclusive, start2Inclusive, end2Exclusive)
                    && containsAnyOfRanges(failureIndex2, start1Inclusive, end1Exclusive, start2Inclusive, end2Exclusive);
        }

        private boolean contains(int index, int from, int to) {
            return index >= from && index < to;
        }

        private boolean containsAnyOfRanges(int index, int from, int to, int from2, int to2) {
            return contains(index, from, to) || contains(index, from2, to2);
        }
    }

    /**
     *
     * @param totalTests the total number of tests.
     * @param runTestsFunction the function that answers if the run passed or failed for specified sets of tests.
     * @return the *indices* of the 2 tests that fail together.
     */
    public static int[] solve(int totalTests, RunTestsFunction runTestsFunction) {
        if (totalTests < 2) {
            throw new IllegalArgumentException("At least 2 test ids are expected: " + totalTests);
        }
        // Bring the function to the convenient form:
        Predicate<PairOfRanges> pairOfRangePredicate = pair -> runTestsFunction.runTests(
                pair.left().startInclusive(), pair.left().endExclusive(),
                pair.right().startInclusive(), pair.right().endExclusive());
        Solver solver = new Solver(totalTests, pairOfRangePredicate);
        PairOfRanges solution = solver.solve();
        assert solution.left().isSingleton();
        assert solution.right().isSingleton();
        return new int[] {
                solution.left().startInclusive(),
                solution.right().startInclusive()
        };
    }

    record Solver(int totalTests, Predicate<PairOfRanges> runTestsFunction) {
                public PairOfRanges solve() {
                    IndexRange fullRange = IndexRange.of(0, totalTests);
                    PairOfRanges pair = bisect(fullRange); // initial pair
                    while (pair.summaryLength() > 2) {
                        pair = processPair(pair);
                    }

                    assert pair.left().isSingleton();
                    assert pair.right().isSingleton();
                    return pair;
                }

                PairOfRanges processPair(final PairOfRanges pair) {
                    //System.out.println("processing pair -> " + pair);

                    final PairOfRanges pairAB = bisect(pair.left());
                    if (runTestsFunction.test(pairAB)) {
                        return pairAB;
                    }
                    final PairOfRanges pairCD = bisect(pair.right());
                    if (runTestsFunction.test(pairCD)) {
                        return pairCD;
                    }

                    //System.out.println(" split into: " + leftPair + " and " + rightPair);

                    // We use Suppliers there because not all of them may actually be executed:
                    Supplier<PairOfRanges> supplierAC = () -> PairOfRanges.of(pairAB.left(), pairCD.left());
                    Supplier<PairOfRanges> supplierAD = () -> PairOfRanges.of(pairAB.left(), pairCD.right());
                    Supplier<PairOfRanges> supplierBC = () -> PairOfRanges.of(pairAB.right(), pairCD.left());
                    Supplier<PairOfRanges> supplierBD = () -> PairOfRanges.of(pairAB.right(), pairCD.right());

                    return Stream.of(supplierAC,
                                     supplierAD,
                                     supplierBC,
                                     supplierBD)
                            .map(Supplier::get)
                            .filter(runTestsFunction)
                            .findFirst().orElseThrow(() -> new IllegalStateException("At least one range should have succeeded."));
                }

                PairOfRanges bisect(IndexRange indexRange) {
                    if (indexRange.length() < 2) {
                        return PairOfRanges.of(indexRange, IndexRange.zeroLength(indexRange.endExclusive()));
                    }
                    int medianIndex = indexRange.medianIndex();
                    return PairOfRanges.of(
                            IndexRange.of(indexRange.startInclusive(), medianIndex),
                            IndexRange.of(medianIndex, indexRange.endExclusive())
                    );
                }
    }

    record IndexRange(int startInclusive, int endExclusive) {
        IndexRange {
            Objects.checkFromToIndex(startInclusive, endExclusive, Integer.MAX_VALUE);
        }
        static IndexRange of(int a, int b) {
            return new IndexRange(a, b);
        }
        static IndexRange singleton(int singleIndex) {
            return new IndexRange(singleIndex, singleIndex + 1);
        }
        static IndexRange zeroLength(int singleIndex) {
            return new IndexRange(singleIndex, singleIndex);
        }
        int length() {
            return endExclusive - startInclusive;
        }
        boolean isSingleton() {
            return length() == 1;
        }
        int medianIndex() {
            return (startInclusive + endExclusive) / 2;
        }

        @Override
        public String toString() {
            return "[" + startInclusive + ", " + endExclusive + ")";
        }
    }

    record PairOfRanges(IndexRange left, IndexRange right) {
        static PairOfRanges of(IndexRange left, IndexRange right) {
            assert left.endExclusive() <= right.startInclusive();
            return new PairOfRanges(left, right);
        }
        int summaryLength() {
            return left().length() + right().length();
        }

        @Override
        public String toString() {
            return left.toString() + " + " + right.toString();
        }
    }

}


