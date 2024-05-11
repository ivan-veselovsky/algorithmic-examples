package edu.test8;

/**
 Given a sorted array a[] with possibly duplicate elements, find indexes of first and last occurrences of an element x in the given array.
 Examples:
 Input : a[] = {2, 3, 3, 5, 5, 5, 5, 67, 123, 125}, x = 5
 Output :
 First Occurrence = 3
 Last Occurrence = 6
 */
public class FindFirstAndLast {
    int[] getFirstAndLastIndexOfValue(int[] a, final int value) {
        int x = 0;
        int y = a.length;
        if (value < a[x] || value > a[y - 1]) {
            throw new IllegalArgumentException("value out of the array");
        }

        int middle;
        while (true) {
            middle = (x + y)/2;
            if (middle == x) {
                break;
            }
            int middleValue = a[middle];
            if (middleValue == value) {
                break;
            } else if (middleValue < value) {
                x = middle;
            } else {
                y = middle;
            }
        }

        if (a[middle] == value) {
            x = middle;
            while (x > 0 && a[x - 1] == value) {
                x--;
            }
            y = middle;
            while (y < a.length - 1 && a[y + 1] == value) {
                y++;
            }
            return new int[] { x, y };
        } else {
            return new int[] { -x, -y };
        }
    }
}
