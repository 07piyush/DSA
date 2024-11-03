package piyush;

import java.util.HashMap;

public class TwoPointerSlidingWindow {
	
	public int lengthOfLongestSubstring(String s) {
		/*
		 * Given a string s, find the length of the longest substring without repeating characters.
		 * Input: s = "abcabcbb"
		 * Output: 3
		 * Explanation: The answer is "abc", with the length of 3.
		 * 
		 * Brute force : generate all substring, and for each substring, check uniqueness of each character.
		 * for each char as start of substr : for each char as end of substr. : for each char from start to end.
		 * Time = O(n3)
		 * Space = O(1)
		 * 
		 * Optimal 1 : there are limited characters that exist, keep memo of character seen in a substr, and decide
		 * from memo if character was seen previously. reset memo for each substr.
		 * Time = O(n2)
		 * Space = O(256)
		 * 
		 * Optimal : Using two pointer, create a window of substr, keep expanding the window untill a character repeats
		 * from the window. use memo to track which characters have been seen.
		 * 
		 * Time = O(n)
		 * Space = O(256)
		 * 
		 * */
		int left = 0;
		int right = 0;
		HashMap<Character, Integer> memo = new HashMap<>();
		int maxLen = 0;
		while(right < s.length()) {
			if(!memo.containsKey(s.charAt(right))) {
				memo.put(s.charAt(right), right);
                int currLen = right-left+1;
				maxLen = currLen>maxLen?currLen:maxLen;
                right++;
			}
			else {
				int foundAt = memo.get(s.charAt(right));
                if(foundAt >= left){
                    left = foundAt + 1;
                }
				int currentLen = right-left+1;
				maxLen = currentLen>maxLen?currentLen:maxLen;
				memo.put(s.charAt(right), right);
                right++;
			}
		}
		return maxLen;
    }
	
	public int longestOnes(int[] nums, int k) {
		/*
		 * Given a binary array nums and an integer k, 
		 * return the maximum number of consecutive 1's in the array if you can flip at most k 0's.
		 * 
		 * Brute Force : for each substring find if it meets the condition of consecutive 1 k 0s. 
		 * For each size of subtr from 0 to n :
		 * 	For each item as start till size : 
		 * 		For each item if 0 and countZ < k. assign length to max if greater than max.
		 *
		 * Time = O(n3)
		 * Space = O(1)
		 * 
		 * Optimal : create a window of size 0, based on conditions change window size while also sliding towards left.
		 * condition for size of window is left will move when right is at 0 and have already seen k zeros.
		 * right will move always.
		 * calculate maximum length if number of zeros in window is <= k.
		 * 
		 * Time = O(n)
		 * Space =O(1)
		 * 
		 * */
        int zeros = 0;
        int right = 0;
        int left = 0;
        int longest = 0;
        while(right < nums.length){
            if(nums[right] == 0) zeros++;
            
            if(zeros > k){
                if(nums[left] == 0)
                    {
                        zeros--;
                    }
                     left++;
            }
            if(zeros <= k){
                int curL = right-left+1;
                longest = curL>longest?curL:longest;
            }
            right++;
        }
        return longest;
    }

}
