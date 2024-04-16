package edu.remove_element_from_array;

public class RemoveElementsFromArray {

    public int removeElement(final int[] nums, final int remove) {
        int writePosition = 0;
        int readPosition = 0;
        while (readPosition < nums.length) {
            int x = nums[readPosition];
            if (x != remove) {
                if (readPosition > writePosition) {
                    nums[writePosition] = x;
                }
                writePosition++;
            }
            readPosition++;
        }
        return writePosition;
    }

}
