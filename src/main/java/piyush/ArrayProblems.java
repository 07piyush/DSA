package piyush;

public class ArrayProblems {

	public static void sortColors(int[] nums) {
		
		/* PROBELM :
		Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

		We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.

		You must solve this problem without using the library's sort function.

		Example 1:

		Input: nums = [2,0,2,1,1,0]
		Output: [0,0,1,1,2,2]
		Example 2:

		Input: nums = [2,0,1]
		Output: [0,1,2]
		
		 * Optimal Solution : 
		 * using three pointers, assume array have all zeros before a pointer low, have all twos after a pointer high.
		 * and mid pointer must assume that values from mid to high are unsorted.
		 * now for each value of mid till high, put 0 at low and increment low and mid. put 2 at high, decrement high.
		 * if encountered 1, then by logic, everything before mid is already sorted, then mid was expeted to have 1. just increment mid.
		 * Time = O(n) Space = O(1).
		 * 
		 * */
        int startOfOnes = 0;
        int startOfUnsorted = 0; //mid
        int endOfUnsorted = nums.length-1;

        while(startOfUnsorted <= endOfUnsorted){
            if(nums[startOfUnsorted] == 0){
                swap(nums, startOfUnsorted, startOfOnes);
                startOfOnes++;
                startOfUnsorted++;
            }
            else if(nums[startOfUnsorted] == 1){
                startOfUnsorted++;
            }
            else{
                swap(nums, startOfUnsorted, endOfUnsorted);
                endOfUnsorted--;
            }
        }
    }

    public static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
