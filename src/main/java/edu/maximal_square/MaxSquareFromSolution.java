package edu.maximal_square;

public class MaxSquareFromSolution {
    char[][] matrix;
    int[][] data;
    int maxSquareSize;

    public int maximalSquare(final char[][] matrix) {
        this.matrix  = matrix;

        final int horizontalSize = matrix[0].length;
        final int verticalSize = matrix.length;
        final int squareLimit = Math.min(horizontalSize, verticalSize);

        data = new int[verticalSize][];
        for (int y=0; y<verticalSize; y++ ) {
            data[y] = new int[horizontalSize];
        }

        for (int y=0; y<verticalSize; y++) {
            for (int x=0; x<horizontalSize; x++) {
                data[y][x] = getMatrixValueAsInt(x, y);
                data[y][x] = reComputeDataCell(x, y);
                if (maxSquareSize == squareLimit) {
                    break;
                }
            }
        }

        return maxSquareSize * maxSquareSize;
    }

    int reComputeDataCell(int x, int y) {
        if (getData(x, y) == 0) {
            return 0;
        }
        int v = 1 + min3(
                getData(x -1, y - 1),
                getData(x, y - 1),
                getData(x - 1, y));

        if (v > maxSquareSize) {
            maxSquareSize = v;
        }
        return v;
    }

    int getData(int x, int y) {
        if (x < 0 || y < 0) {
            return 0;
        }
        return data[y][x];
    }

    static int min3(int x, int y, int z) {
        return Math.min(Math.min(x, y), z);
    }

    int getMatrixValueAsInt(int x, int y) {
        if (x < 0 || y < 0) {
            return 0;
        }
        return matrix[y][x] == '1' ? 1 : 0;
    }
}
