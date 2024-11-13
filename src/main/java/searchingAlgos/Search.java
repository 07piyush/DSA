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
	
	public int searchInRotatedSortedArrayWithUniqueElements(int[] nums, int target) {
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
		 * Brute force : linear search traverse whole array, return index when found the target.
		 * 
		 * Optimal : since array was originally sorted, so even when rotated, some part of array will still be sorted.
		 * Idea : when array is rotated, then by each rotation start index of sorted array get shifted in the direction
		 * of rotation.
		 * 
		 * so while we find the middle, every time. we know that either left or right part of middle will
		 * always be sorted. we go search target in wrong direction only when target is not in range of 
		 * sorted part of array.
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
	
	public boolean searchInRotatedSortedArrayWithDuplicateElements(int[] nums, int target) {
		/*
		 * extending to above problem, when array has duplicate elements, then 
		 * for a particular case, it is possible that we can not tell which part might be sorted.
		 * 
		 * E.g: [3,4,5,6,3,3,3,3,3] 
		 * here nums[0] = 3 : low
		 * 		nums[4] = 3 : mid
		 * 		nums[7] = 3 : right
		 * 
		 * Solution : move left, right by one, and narrow down the search space.
		 * 
		 * */
        int left = 0;
        int right = nums.length-1;

        while(left<=right){
            int mid = (left+right)/2;
            if(nums[mid] == target)
                return true;

            if(nums[left] == nums[mid] && nums[mid] == nums[right]){
                left++;
                right--;
                continue;
            }
            
            if(nums[left] <= nums[mid]){
                //array is sorted on left side.
                if(nums[left] <= target && target < nums[mid])
                    right = mid-1;
                else
                    left = mid+1;
            }
            else{
                //array is sorted on right side.
                if(target > nums[mid] && target <= nums[right])
                    left = mid+1;
                else
                    right = mid-1;
            }
        }
        return false;
    }
	

	public int findMin(int[] nums) {
		/*
		 * find minimum element in a sorted array, which is rotated arbitrary times.
		 * 
		 * Solution : if an array is rotated, then the side which is in sorted order may or may not have
		 * the minimum element, even if sorted part have minimum element, we can always check that in constant time
		 * as minimum in sorted is always leftmost. i.e nums[left].
		 * 
		 * so check minimum from sorted, and continue the search on unsorted part.
		 * 
		 * Time = O(log n)
		 * */
		
        int left = 0;
        int right = nums.length-1;
        int min = Integer.MAX_VALUE;
        while(left <= right){
            int mid = (left+right)/2;
            if(nums[mid] < min)
                min = nums[mid];
            if(nums[left] <= nums[mid]){
                min = nums[left]<min?nums[left]:min;
                left = mid+1;
            }
            else{
                min = nums[mid+1]<min?nums[mid+1]:min;
                right = mid-1;
            }
        }
        return min;
    }
	
	public int singleNonDuplicate(int[] nums) {
		/*
		 * You are given a sorted array consisting of only integers where every element appears exactly twice, 
		 * except for one element which appears exactly once. Return the single element that appears only once.
		 * 
		 * E.g. : [1,1,2,3,3,4,4,8,8]
		 * Output : 2
		 * 
		 * brute force : linear search, return the element that is same as its next.
		 * 
		 * Optimal : since the array is sorted, some how we can eliminate one of the half.
		 * 
		 * observation. 1. pair will be in combination of (odd, even) index, unless a single is encountered.
		 * once a single is encountered, pair will be found in (even, odd) indices.
		 * 
		 * hence when mid is standing at a point, either it is single himself.
		 * or single reside in left half or in right half. 
		 * if middle and left are same while mid is at odd index, or middle and right are same while mid is
		 * at even index. then all values to left of middle are pairs.
		 *  		 L			 M				R	
		 * 		 [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]			
		 * E.g : [1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6]
		 * 		 [O, E, O, E, O, E, O, E, O, E, O, E
		 * */
        int left = 0;
        int right = nums.length-1;
        int result = -1;
        if(nums.length == 1)
            return nums[0];
        if(nums[0] != nums[1])
            return nums[0];
        if(nums[nums.length-1] != nums[nums.length-2])
            return nums[nums.length-1];
        left++;
        right--;
        while(left<=right){
            int mid = (left+right)/2;

            if(nums[mid] != nums[mid+1] && nums[mid-1] != nums[mid])
                return nums[mid];
            
            if(mid%2 == 0 && nums[mid+1] == nums[mid] || 
                mid%2==1 && nums[mid-1] == nums[mid])
                left = mid+1;
            else
                right = mid-1;
        }
        return result;
    }
	
	public int findPeakElement(int[] nums) {
		/*
		 * Find Peak Element 
		 * A peak element is an element that is strictly greater than its neighbors.
		 * 
		 * IMP NOTE : Constraints:
		 * 1 <= nums.length <= 1000 
		 * -231 <= nums[i] <= 231 - 1 
		 * nums[i] != nums[i + 1] for all valid i.
		 * 
		 * brute force : perform linear search, each element is either our answer or potential answer.
		 * let first element be our candidate. (since according to question, we can assume every thing before and after
		 * provided is -infinity)
		 * for each candidate check if next element is greater, if yes then candidate is our answer.
		 * if not then next element will become our candidate.
		 * 
		 * Time = O(n)
		 * 
		 * Optimal : if we find mid of array, it will satisfy any of three conditions :
		 * 1. mid is peak.
		 * 2. mid is part of increasing trend. : then peak lies in right part of mid.
		 * 3. mid is part of decreasing trend. : then peak lies in left part of mid.
		 * 
		 * 
		 * */
		
        if(nums.length == 1)
            return 0;
        if(nums[0] > nums[1])
            return 0;
        if(nums[nums.length-1] > nums[nums.length-2])
            return nums.length-1;
        int left = 1;
        int right = nums.length-2;

        while(left <= right){
            int mid = (left+right)/2;

            if(nums[mid-1] < nums[mid] && nums[mid] > nums[mid+1])
                return mid;
            
            if(nums[mid+1] < nums[mid])
                right = mid-1;
            else
                left = mid+1;
        }
        return -1;
    }
	
	public static int minEatingSpeed(int[] piles, int h) { 
		/*
		 * koko have piles of bananas, each index has a pile.
		 * she can eat k number of bananas in an hour.
		 * she has h hours in total, find minimum k, such that she eats k bananas each hour
		 * so that all bananas are finished in h hours.
		 * 
		 * Return the minimum integer k such that she can eat all the bananas within h hours.
		 * 
		 * E.g :Input: piles = [3,6,7,11], h = 8
		 * Output: 4
		 * 
		 * Explanation :when she can eat 1/2/3/4/5...10/11. bananas in an hour.
		 * to finish them in 4 hours if she eat 11 banana per hour. 
		 * she can not eat more than 11 (maximum possible) bananas in an hour.
		 * if she eats 2 bananas in an hour, she will require [2+3+4+6]=15 hours to finish but she has only 8.
		 * that means she must increase number of bananas per hour.
		 * 
		 *  so minimum can range in [1 : max(piles)].
		 *  
		 *  Brute force : for each hourlyCapacity[1:max(piles)] calculate time taken to finish, only return 
		 *  for first such capacity. else keep considering larger hourlyCapacity.
		 *  Time = O(max(piles)*n) == O(n2) if max is equal to size of array;
		 *  
		 * optimal : there is a hourlyCapacity, after which koko will be able to finish all bananas in h hours.
		 * but we are interested in minimum of that.
		 * 
		 * Eg. [1,2,3,4,5,6,7,8,9,10,11] : possible hourlyCapacity.
		 * 	   [x,x,x,Y,Y,Y,Y,Y,Y,Y ,Y ] : here that number is 4. so do a binary search for that.
		 * 
		 * 
		 * */
        int left = 1;
        int right = max(piles);

        while(left<=right){
            int mid = (left+right)/2;
            long hoursTaken = hoursTaken(piles, mid);
            if(hoursTaken <= h){
                right = mid-1;
            }
            else {
                left = mid+1;
            }
        }
        return left;
    }
    public static int hoursTaken(int[] piles, int capacity){
        int ans = 0;
        for(int i=0; i<piles.length; i++){
        	ans += Math.ceil((double)(piles[i]) / (double)(capacity));
        }
        return ans;
    }

    public static int max(int[] arr){
        int max = arr[0];
        for(int i=1; i<arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }
    
    public static int smallestDivisor(int[] nums, int threshold) {
    	/*
    	 * Given an array of integers nums and an integer threshold, we will choose a positive integer divisor, 
    	 * divide all the array by it, and sum the division's result. 
    	 * Find the smallest divisor such that the result mentioned above is less than or equal to threshold.
    	 * 
    	 * Input: nums = [44,22,33,11,1], threshold = 5
    	 * Output: 44
    	 * 
    	 * Brute force : for each number as divisor, find the sum after division. if sum is less than threshold,
    	 * then assign it to globalmin answer if less than globalmin.
    	 * 
    	 * Time = O(n2) | Space = O(1)
    	 * 
    	 * Optimal : answer can range from 1 to maximum element value in array. so via binary search we can find
    	 * answer optimally by rejecting other permutations.
    	 * 
    	 * Time = O(n * logn) | space = O(1)
    	 * */
        int[] minMax = getMinMax(nums);
        int low = 1;
        int high = minMax[1];
        int answer = high;
        while(low<=high){
            int mid = (low+high)/2;
            if(isEligibleNumber(nums, threshold, mid)){
                answer = mid<=answer?mid:answer;
                high = mid-1;
            }
            else
                low = mid+1;
        }
        return answer;
    }

    public static boolean isEligibleNumber(int[] arr, int th, int num){
        
        int sum = 0;
        for(int i=0; i<arr.length; i++){
            sum += Math.ceil((double)(arr[i])/(double)(num));            
        }
        if(sum<=th)
            return true;
        else 
            return false;
    }

    public static int[] getMinMax(int[] arr){
         int[] minMax = new int[2];
         minMax[0] = Integer.MAX_VALUE;
         minMax[1] = Integer.MIN_VALUE;
         for(int i=0; i<arr.length; i++){
             minMax[0] = arr[i]<minMax[0]?arr[i]:minMax[0];
             minMax[1] = arr[i]>minMax[1]?arr[i]:minMax[1];
         }
         return minMax;
     }
    
    public static int shipWithinDays(int[] weights, int days) {
    	/*
    	 * A conveyor belt has packages that must be shipped from one port to another within days days. 
    	 * The ith package on the conveyor belt has a weight of weights[i]. Each day, we load the ship with packages 
    	 * on the conveyor belt (in the order given by weights). We may not load more weight than the maximum weight 
    	 * capacity of the ship. Return the least weight capacity of the ship that will result in all the packages on 
    	 * the conveyor belt being shipped within days days.
    	 * 
    	 * Input: weights = [1,2,3,4,5,6,7,8,9,10], days = 5 Output: 15
    	 * Explanation: A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
    	 * 1st day: 1, 2, 3, 4, 5
    	 * 2nd day: 6, 7
    	 * 3rd day: 8
    	 * 4th day: 9
    	 * 5th day: 10
    	 * 
    	 * brute force : for each weight on conveyer belt, let that weight be capacity of ship.
    	 * thus for that selected capacity, check if we can deliver all items in given days.
    	 * 
    	 * if yes, use to compare with minimum capacity initialized before.
    	 * and return.
    	 * 
    	 * Time = O(n2) | Space = O(1)
    	 * 
    	 * Optimal : maximum possible capacity could be sum of all item weights. while least capacity has to be 
    	 * minimum weight on conveyer belt. now search for best weight in this range using binary search. and return
    	 * 
    	 * Time = O(n * log n) | Space = O(1)

    	 * 
    	 * */
        int[] minMax = getMinMax(weights);
        int low = minMax[1];
        int high = sum(weights);
        int minDays = high;
        while(low<=high){
            int mid = (low+high)/2;
            if(checkIfEligibleShipCapacity(weights, days, mid)){
                minDays = mid;
                high = mid-1;
            }
            else{
                low = mid+1;
            }
        }
        return minDays;
    }

    public static int sum(int[] weights){
        int sum = 0;
        for(int i=0; i<weights.length; i++){
            sum += weights[i];
        }
        return sum;
    }

    public static boolean checkIfEligibleShipCapacity(int[] weights, int days, int capacity){
        int consumedPerDay = 0;
        int requiredDays = 1;
        for(int i=0; i<weights.length; i++){
            if(consumedPerDay+weights[i] <= capacity){
                consumedPerDay += weights[i];
            }
            else{
                consumedPerDay = weights[i];
                requiredDays++;
            }
        }
        if(requiredDays <= days)
            return true;
        else
            return false;
    }
    
    public int findKthPositive(int[] arr, int k) {
    	/*
    	 * Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
    	 * Return the kth positive integer that is missing from this array.
    	 * 
    	 * Input: arr = [2,3,4,7,11], k = 5 
    	 * Output: 9 Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. 
    	 * The 5th missing positive integer is 9.
    	 * 
    	 * brute force : from 1 to maximum(arr) keep monitoring missing numbers, when count of missing reaches k, 
    	 * return i;
    	 * Time = O(n)
    	 * 
    	 * optimal : if no number is missing, then in the array the each element will represent its index+1.
    	 * hence if an element is not equal to index+1 then it is deviated from it original position.
    	 * 
    	 * deviation represents number of numbers that were skipped. deviation = arr[i]-i-1
    	 * if we start a binary search from 1 as low and last of array as high, we can reject part of array if required 
    	 * number to find (kth missing element) is less or more than deviation from middle of array.
    	 * 
    	 * when middle is deviated by just 3 digits, and we are looking for 5th missing element, that 
    	 * we no that only 3 elements were missing on left side of middle. hence go search in right part of array.
    	 * 
    	 * when loop end : high > low. this means missing kth element is between the range [high, low].
    	 * 
    	 * high represent that missing element is greater than arr[high] and low represent missing element in lesser.
    	 * REMEMBER high is sitting at low-1 after loop ends.
    	 * arr[high] + more = this mean just calculate more elements after a[high]
    	 * 
    	 * more = k - deviation at high.
    	 * 		= k - (arr[high] - h - 1)
    	 * 		= k - arr[high] + h + 1
    	 * 
    	 * hence return arr[high] + k - arr[high] + h + 1;
    	 * 		 return k + h + 1;
    	 *  OR   return k+low;		since low = h+1 after the loop.
    	 *  
    	 *  Time = O(logn)  | Space = O(1)
    	 * */
        int low = 0;
        int high = arr.length-1;

        while(low<=high){
            int mid = (low+high)/2;
            int deviation = arr[mid]-mid-1;
            if(deviation<k)
                low = mid+1;
            else
                high = mid-1;
        }
        return k+high+1;
    }
}
