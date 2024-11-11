package piyush;

import searchingAlgos.Search;

public class MyPractice {

	public static void main(String[] args) {
		
		int[] arr = {5,4,1,7,8,9,1,0,9};
		int[] zeroOnesAndTwos = {0,1,1,0,1,2,1,2,0,0,0};
		int[] longestConsicutiveSequence = {100,4,200,1,3,2};
		int[][] intervals = new int[4][2]; 
		//intervals[[1,3],[2,6],[8,10],[15,18]];
		int [] piles = {805306368,805306368,805306368};
		Search.minEatingSpeed(piles, 1000000000);
		int res = ArrayProblems.longestConsecutive(longestConsicutiveSequence);
		System.out.println(res);
//		QuickSort.sort(arr, 0, arr.length-1);
//		QuickSort.printArray(arr);
		 
}
	
	

}
