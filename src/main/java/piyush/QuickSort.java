package piyush;

public class QuickSort {

	public static void sort(int[] arr, int low, int high) {
		if(low < high) {
			int pIndex = partition(arr, low, high);
			sort(arr, 0, pIndex-1);
			sort(arr, pIndex+1, high);
		}
	}
	
	private static int partition(int[] nums, int left, int right) {
		
		/*
		 * Idea is to randomly select a number (pivot) from array, and put all elements of array that are smaller than
		 * than pivot to left of it and put all elements that are greater than pivot to the right of it.
		 * 
		 * to do this, find first-smaller-element from right and first-larger-element from left. (violation of requirement)
		 * swap the two until left and right pointer crosses. (not enough!!)
		 * 
		 * when left and right pointer have crossed, that means right pointer is at a position which left earlier was
		 * that also means, we have already checked if that element is less or greater than pivot, and we must have 
		 * swapped it if it was greater. Since we will stop iteration as soon as left and right crosses hence
		 * after crossing, right is sitting at a position where there is first lesser value than pivot, so at last
		 * just swap pivot (initialized with leftmost element) and element at right once left and right have crossed.
		 * 
		 * 
		 * return  right : pivot index.
		 * 
		 * */
		
		int pivot = nums[left];
        int i = left;
        int j = right;
        while(i < j){
                while(i<=right && nums[i] <= pivot)
                    i++;
                while(left<=j && nums[j] > pivot)
                    j--;
                if(i < j)
                    swap(nums, i, j);
        }
        swap(nums,left,j);
        return j;
	}
	
	private static void swap(int[] arr, int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
	
	public static void printArray(int[] arr) {
		for(int item = 0; item < arr.length; item++)
			System.out.print(arr[item] + ", ");
	}
}
