package piyush;

import heaps.kthLargestSmallest;
import searchingAlgos.Search;

public class MyPractice {

	public static void main(String[] args) {
		
		int[] arr = {5,4,1,7,8,9,1,0,9};
		int[] zeroOnesAndTwos = {0,1,1,0,1,2,1,2,0,0,0};
		int[] longestConsicutiveSequence = {3,3,2,2,1,1};
		int[][] intervals = new int[4][2]; 
		//intervals[[1,3],[2,6],[8,10],[15,18]];
		int [] a = {44,22,33,11,1};
		
		kthLargestSmallest.isPossibleDivide(longestConsicutiveSequence, 3);
}
	
	

}
