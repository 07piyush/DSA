package searchingAlgos;

public class Search {

	public int iterativeBinarysearch(int nums[], int target) {
		//return index of target in nums, -1 if not found
		int left = 0;
		int right = nums.length-1;

		while(left<=right) {

			int mid = (left+right)/2;

			if(nums[mid] == target)
				return mid;
			else if(target > nums[mid])
				left = mid +1;
			else
				right = mid-1;
		}
		return -1;
	}

	public int recursiveBinarySearch(int[] nums, int left, int right, int target){
		if(left > right)
			return -1;
		int mid = (left+right)/2;
		if(nums[mid] == target)
			return mid;
		if(target > nums[mid])
			return recursiveBinarySearch(nums, mid+1, right, target);
		else
			return recursiveBinarySearch(nums, left, mid-1, target);
	}

	public int lowestIndexOfTargetIterative(int[] nums, int target) {
		/*
		 * aka lower bound is the smallest index such that nums[index] >= target.
		 * aka first occurace of a number in a sorted array.
		 * 
		 * aka Find first position of an element in a sorted array.
		 * 
		 * */
		int firstOcc = -1;
		int left = 0;
		int right = nums.length-1;

		while(left<=right){
			int mid = (left+right)/2;
			if(nums[mid] == target){
				firstOcc = mid;
				right = mid-1;
			}
			else if(nums[mid] > target)
				right = mid-1;
			else
				left = mid+1;
		}

		return firstOcc;
	}

	public int highestIndexOfTargetIterative(int [] nums, int target) {

		/*
		 * aka last occurrence of an element in a sorted array.
		 * aka upper bound of a target in a sorted array.
		 * 
		 * aka Find last position of an element in a sorted array.
		 * 
		 * */

		int lastOcc = -1;
		int left = 0;
		int right = nums.length-1;
		while(left<=right){
			int mid = (left+right)/2;
			if(nums[mid] == target){
				lastOcc = mid;
				left = mid+1;
			}
			else if(nums[mid] > target)
				right = mid-1;
			else
				left = mid+1;
		}
		
		return lastOcc;
	}
	
	public int[] searchRange(int[] nums, int target) {
		/*
		 * Find first and last position of an element in a sorted array.
		 * 
		 * */
		
		int[] result = new int[2];
		
		result[0] = lowestIndexOfTargetIterative(nums, target);
		result[1] = highestIndexOfTargetIterative(nums, target);
		
		return result;
	}
	
	public int search(int[] nums, int target) {
		/*
		 * Search in Rotated Sorted Array.
		 * There is an integer array nums sorted in ascending order (with distinct values).
		 * array is rotated by random number.
		 * 
		 * Given the array nums after the possible rotation and an integer target, 
		 * return the index of target if it is in nums, or -1 if it is not in nums.
		 * 
		 * Example : nums = [4,5,6,7,0,1,2], target = 0 
		 * Output: 4
		 * 
		 * 
		 * */
		
        int left = 0;
        int right = nums.length-1;

        while(left <= right){
            int mid = (left+right)/2;

            if(nums[mid] == target){
                return mid;
            }
            //if left part is sorted
            if(nums[left] <= nums[mid]){
                if(nums[left] <= target && target < nums[mid])
                    right = mid-1;
                else
                    left = mid+1;
            }
            else{
                if(nums[mid] < target && target <= nums[right])
                    left = mid +1;
                else
                    right = mid-1;
            }
        }

        return -1;
    }
}
