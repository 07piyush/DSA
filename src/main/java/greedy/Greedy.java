package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Greedy {

	public static int findContentChildren(int[] g, int[] s) {

		/*
		 * Assume you are an awesome parent and want to give your children some cookies. But, you should give each 
		 * child at most one cookie. Each child i has a greed factor g[i], which is the minimum size of a cookie that 
		 * the child will be content with; and each cookie j has a size s[j]. If s[j] >= g[i], we can assign the cookie 
		 * j to the child i, and the child i will be content. Your goal is to maximize the number of your content 
		 * children and output the maximum number.
		 * 
		 * Solution : to maximize number of fulfilled children, we have to feed the child with least greed.
		 * by sorting the greed array and size array, one by one for each child, find the cookie that can fulfill him.
		 * until we are out of cookies.
		 * 
		 * Time = O(nlong) Space = O(1)
		 * */

		if(s.length < 1){
			return 0;
		}
		Arrays.sort(g);
		Arrays.sort(s);
		int currChild = 0;
		int currCookie = 0;
		int res = 0;
		while(currChild < g.length && currCookie < s.length){
			if(g[currChild] <= s[currCookie]){
				res++;
				currChild++;
				currCookie++;
			}
			else{
				currCookie++;
			}
		}
		return res;
	}

	public static int matchPlayersAndTrainers(int[] players, int[] trainers) {
		/*
		 * problem statement is different as above, but problem is same.
		 * the difference in solution is we do not need to use result at all,
		 * just return then current index of player/child because it is the
		 * number of player/children satisfied after completion of loop.
		 * 
		 * 70398833330
		 *  
		 * */
		Arrays.sort(players);
		Arrays.sort(trainers);
		int currPlayer = 0;
		int currTrainer = 0;
		while(currPlayer < players.length && currTrainer < trainers.length){
			if(players[currPlayer] <= trainers[currTrainer]){
				currPlayer++;
				currTrainer++;
			}
			else{
				currTrainer++;
			}
		}
		return currPlayer;
	}

	public class Item{
		Integer value;
		Integer weight;
		double perunitVal;

		public Item(Integer v, Integer w, double puv){
			value = v;
			weight = w;
			perunitVal = puv;
		}
	}
	public double fractionalKnapsack(List<Integer> val, List<Integer> wt, int capacity) {
		// code here
		List<Item> items = new ArrayList<>();

		for(int item = 0; item < val.size(); item++){
			double perunitVal = (double) val.get(item)/wt.get(item);
			Item newItem = new Item(val.get(item), wt.get(item), perunitVal);
			items.add(newItem);
		}

		items.sort(
				(a,b) -> Double.compare(b.perunitVal, a.perunitVal)
				);

		double res = 0.0;

		for(Item item : items){
			if(capacity == 0)
				break;

			if(item.weight <= capacity){
				res+=item.value;
				capacity-=item.weight;
			}
			else{
				double fractionalValue = item.perunitVal * capacity;
				res += fractionalValue;
				capacity = 0;
			}
		}
		return res;
	}

	public int coinChangeBruteForce(int[] coins, int amount) {
		/*You are given an integer array coins representing coins of different denominations and an integer amount 
		 * representing a total amount of money.Return the fewest number of coins that you need to make up that amount. 
		 * If that amount of money cannot be made up by any combination of the coins, return -1. You may assume 
		 * that you have an infinite number of each kind of coin.
		 * 
		 * brute force : try all valid combinations.
		 * for each coin we can either select it or reject it, based on amount. 
		 * 1. if amount is zero then we don't need to check more coins. 
		 * 		: minimum number of coins = 0;
		 * 2. if amount is less than 0 then its not possible to use any coin to make a total of amount.
		 * 		: minimum number of coins = -1;
		 * 3. if amount is greater than 0 then for each coin, find the minimum number of coin when current coin is
		 * 		eligible to pick. that means, amount get reduced by current coin value.
		 * 		: minimum number of coins = 1 + minCoin(amount-coin)
		 * 4. in the end, if there is no coin that can give a sum of amount, then minCoins remain intmax.
		 * 
		 * Time = O(k^n) : k = number of coin denominations.
		 * 
		 * */
		if(amount == 0)
			return 0;
		if(amount < 0)
			return -1;

		int minCoins = Integer.MAX_VALUE;
		for(int coin : coins) {
			int minCoinsWithCurrentSelection = coinChangeBruteForce(coins, amount-coin);
			if(minCoinsWithCurrentSelection != -1) {
				//with current coin, it is possible to sum amount. so min coins is this + whatever returned 
				//when amount is reduced due to current coin selection.
				minCoins = Math.min(minCoinsWithCurrentSelection + 1, minCoins);
			}
		}
		return minCoins == Integer.MAX_VALUE ? -1 : minCoins;
	}

	public int coinChangeGreedy(int[] coins, int amount) {
		/*
		 * Idea is to minimize number of coins required by greedily selecting the coin with highest denomination.
		 * this looks intuitive because common sense states that to have minimum coins for an amount, we should have
		 * coins of maximum possible value.
		 * 
		 * this approach fails because we are not considering send best options. 
		 * E.g coins = [1, 5, 6, 9] and amount = 11
		 * 
		 * greedy answer : [9 ,1, 1] = 3 coins
		 * correct answer: [5,6] = 2 coins.
		 * 
		 * */
		return 0;
	}

	public boolean checkValidString(String s) {
		/*
		 * Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
		 * The following rules define a valid string:
		 * 
		 * Any left parenthesis '(' must have a corresponding right parenthesis ')'. 
		 * Any right parenthesis ')' must have a corresponding left parenthesis '('.
		 * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
		 * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
		 * 
		 * if there were no * then problem would have been simple, to check for each character, if it is ( string is
		 * imbalance by extra open if it is ) then string is imbalanced by a missing (.
		 * 
		 * 
		 * */
		int min = 0;
		int max = 0;
		for(int i=0; i<s.length(); i++){
			if(s.charAt(i) == '('){
				min++;
				max++;
			}
			else if(s.charAt(i) == ')'){   
				min--;
				max--;   
			}
			else{
				min--;
				max++;
			}

			if(min < 0){
				min = 0;
			}
			if(max < 0 )
				return false;
		}
		return min ==0?true:false;
	}

	public int maxMeetings(int start[], int end[]) {
		/*
		 * You are given timings of n meetings in the form of (start[i], end[i]) where start[i] is the start time of 
		 * meeting i and end[i] is the finish time of meeting i. Return the maximum number of meetings that can be 
		 * accommodated in a single meeting room, when only one meeting can be held in the meeting room at a particular 
		 * time. 
		 * Note: The start time of one chosen meeting can't be equal to the end time of the other chosen meeting.
		 *  Input: start[] = [10, 12, 20], end[] = [20, 25, 30]
		 *  Output: 1
		 *  
		 *  Greedy : by choosing those meetings that ends at the earliest. 
		 *  
		 * */

		List<Meeting> meetings = new ArrayList<>();
		for(int meeting=0; meeting<start.length; meeting++) {
			Meeting m = new Meeting();
			m.start = start[meeting];
			m.end = end[meeting];
			meetings.add(m);
		}
		meetings.sort(
				(a, b) -> Integer.compare(a.end, b.end)
				);

		int meetingCount = 0;
		int nextAvail = -1;
		for(Meeting m : meetings) {
			if(m.start > nextAvail) {
				meetingCount++;
				nextAvail = m.end + 1;
			}
		}
		return meetingCount;
	}

	private class Meeting{
		int start;
		int end;
	}
	
	public boolean canJump(int[] nums) {
		/*
		 * You are given an integer array nums. You are initially positioned at the array's first index, 
		 * and each element in the array represents your maximum jump length at that position.
		 * 
		 * Return true if you can reach the last index, or false otherwise.
		 * 
		 * Input: nums = [2,3,1,1,4] 
		 * Output: true
		 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
		 * 
		 * Brute force : at each step, try every possible jump till maximum.
		 * this is possible by recursively calling canJump for each jump size in [max, 1]
		 * Time = O(n!)
		 * 
		 * Optimal : to not able to reach end, the jump size must be 0.
		 * otherwise in all other cases, its not possible to not reach till end.
		 * 
		 * idea is to avoid reaching a position where jump size is 0.
		 * 
		 * while traversing array, we know that at what position we can at max reach, in this way for each possible 
		 * landing zones we can know maximum to maximum where we can reach if we jump from that landing zone.
		 * 
		 * but also if we know this maximum possible value of reachable landing zone, and no previous landing zone
		 * promise to reach current landing zone then we must return false.
		 * else keep traversing till either end or maximum possible reachable landing zone is equal or more than
		 * size of array.
		 * 
		 * */
        int maxDestination = 0; 
        for(int i=0; i<nums.length; i++){
            if(maxDestination >= nums.length){
                return true;
            }
            if(i > maxDestination){
                return false;
            }
            else{
                maxDestination = maxDestination>nums[i]+i?maxDestination:nums[i]+i;
            }
        }
        return true;
    }
}
