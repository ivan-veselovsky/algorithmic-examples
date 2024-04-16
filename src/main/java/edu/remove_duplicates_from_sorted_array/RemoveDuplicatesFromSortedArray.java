package edu.remove_duplicates_from_sorted_array;

public class RemoveDuplicatesFromSortedArray {

    public int removeDuplicates(int[] nums) {
        int writePosition = 0;
        int readPosition = 0;
        int previousValue = 0;
        int x;
        while (readPosition < nums.length) {
            x = nums[readPosition];
            if (readPosition == 0) {
                writePosition++;
            } else if (previousValue < x) {
                nums[writePosition] = x;
                writePosition++;
            }
            previousValue = x;
            readPosition++;
        }
        return writePosition;
    }

}
