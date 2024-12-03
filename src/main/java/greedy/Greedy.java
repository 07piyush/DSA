package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
	
	public int jump(int[] nums) {
		
		/*
		 * You are given a 0-indexed array of integers nums of length n. You are initially positioned at nums[0].
		 * Each element nums[i] represents the maximum length of a forward jump from index i. In other words, 
		 * if you are at nums[i], you can jump to any nums[i + j] where:
		 * 
		 * 0 <= j <= nums[i] and
		 * i + j < n
		 * 
		 * Return the minimum number of jumps to reach nums[n - 1]. The test cases are generated such that you can 
		 * reach nums[n - 1].
		 * 
		 * Brute force : recursively try all possibilities. 
		 * 
		 * Optimal : for each jump point(element of array), we need to have track of minimum number of jumps
		 * that could have allowed to reach current jump point. like for 0th jump point minimum jumps = 0;
		 * each jump point will give a range of points, that can be reached.
		 * 
		 * Idea is to keep iterating till second last jump point and any jump point may help reach farther than 
		 * probably any next jump point.
		 * keeping track of maximumReachable (for all jump points visited yet the farthest point that can be reached)
		 * 				and	currReachable (reachable points from current jump point)
		 * 
		 * a new jump is required when current iteration is at point which is maximum currently reachable.
		 * once jumped.  
		 * 
		 * confusion : why currReach is required at all.??
		 * 			   why a jump is registered when i reaches currReach??
		 * */
		int currReach = 0;
		int maxReach = 0;
		int jumps = 0;
		for(int i=0; i<nums.length-1; i++) {
			int currentPossible = i+nums[i];
			maxReach = currentPossible>maxReach?currentPossible:maxReach;
			
			if(i == currReach) {
				currReach = maxReach;
				jumps++;
				if(currReach>=nums.length-1)
					break;
			}
		}
        return jumps;
    }
	
	static int findPlatform(int arr[], int dep[]) {
		/*
		 * You are given the arrival times arr[] and departure times dep[] of all trains that arrive at a railway 
		 * station on the same day. Your task is to determine the minimum number of platforms required at the station 
		 * to ensure that no train is kept waiting.
		 * 
		 * At any given time, the same platform cannot be used for both the arrival of one train and the departure of 
		 * another. Therefore, when two trains arrive at the same time, or when one arrives before another departs, 
		 * additional platforms are required to accommodate both trains.
		 * 
		 * Input: arr[] = [900, 940, 950, 1100, 1500, 1800], dep[] = [910, 1200, 1120, 1130, 1900, 2000]
		 * Output: 3
		 * Explanation: There are three trains during the time 9:40 to 12:00. So we need a minimum of 3 platforms.
		 * 
		 * Brute Force : for each incoming train find if there is anyoverlapping time, increase platforms if yes.
		 * 
		 * 
		 * */
		
        int arrivalTime = 0;
        int departureTime = 0;
        int maxPlatforms = 0;
        int platformCount = 0;
        Arrays.sort(arr);
        Arrays.sort(dep);
        while(arrivalTime<arr.length && departureTime<dep.length){
            
            if(arr[arrivalTime] <= dep[departureTime]){
            	arrivalTime++;
                platformCount++;
                maxPlatforms = maxPlatforms>platformCount?maxPlatforms:platformCount;
            }
            else{
                departureTime++;
                platformCount--;
            }
        }
        return maxPlatforms;
    }
	
	ArrayList<Integer> JobScheduling(Job jobs[], int n) {
		/*
		 * Given an array, jobs[] where each job[i] has a jobid, deadline and profit associated with it. 
		 * Each job takes 1 unit of time to complete and only one job can be scheduled at a time. We earn the profit 
		 * associated with a job if and only if the job is completed by its deadline.
		 * 
		 * Find the number of jobs done and the maximum profit.
		 * 
		 * Note: jobs will be given in the form (jobid, deadline, profit) associated with that job. 
		 * Deadline of the job is the time on or before which job needs to be completed to earn the profit.
		 * 
		 * Greedy : sort the jobs by profit, for each job we need to know when in the days from 1 to deadline of
		 * current job (sorted by profit) can we execute it or not execute it.
		 * 
		 * Time : O( NlogN + N*maxDeadline)
		 * Space : O(maxDeadline)
		 * 
		 * This is the most optimal greedy solution.
		 * 
		 * */
        
        Arrays.sort(jobs, (a, b) -> Integer.compare(b.profit, a.profit));
        
        int count = 0;
        int profit = 0;
        int maxDeadline = 0;
        for(int i=0; i<jobs.length; i++){
            Job job = jobs[i];
            maxDeadline = maxDeadline>job.deadline?maxDeadline:job.deadline;
        }
        
        int[] arr = new int[maxDeadline];
        for(int i=0; i<jobs.length; i++){
            Job job = jobs[i];
            int day = job.deadline-1;
            while(day>=0){
                if(arr[day] == 0){
                    arr[day] = job.profit;
                    count++;
                    day--;
                    profit+=job.profit;
                    break;
                }
            }
            
        }
        
        ArrayList<Integer> res = new ArrayList<>(2);
        res.add(count);
        res.add(profit);
        return res;
    }
	
	class Job {
	    int id, profit, deadline;
	    Job(int x, int y, int z){
	        this.id = x;
	        this.deadline = y;
	        this.profit = z;
	    }
	}
	
	public int[][] insert(int[][] intervals, int[] newInterval) {
		
		/*
		 * Leetcode : 57. Insert Interval
		 * 
		 * Example 1:
		 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
		 * Output: [[1,5],[6,9]]
		 * 
		 * Example 2:
		 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
		 * Output: [[1,2],[3,10],[12,16]]
		 * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
		 * 
		 * Solution : while traversing intervals, we need to find first interval that overlaps
		 * with newInterval. Untill overlapping interval is found, we can insert the interval in result
		 * when found the interval that contains start of newInterval we can keep iterating and choose 
		 * the range as minimum(newInterval.start, currInterval.start) and choose maximum(newInterval.end, curr.end)
		 * untill newInterval is in overlapping range of curr intervals. 
		 * 
		 * in the end, for remaining intervals we can directly push them in result.
		 * 
		 * Time = O(n)
		 * Space = O(1)
		 * 
		 * 
		 * */
        int currInterval = 0;
        List<int[]> result = new ArrayList<>();

        while(currInterval < intervals.length && intervals[currInterval][1] < newInterval[0]){
            int[] interval = {intervals[currInterval][0], intervals[currInterval][1]};
            result.add(interval);
            currInterval++;
        }
        
        while(currInterval < intervals.length && intervals[currInterval][0] <= newInterval[1]){
            newInterval[0] = intervals[currInterval][0] < newInterval[0]?intervals[currInterval][0]:newInterval[0];
            newInterval[1] = intervals[currInterval][1] > newInterval[1]?intervals[currInterval][1]:newInterval[1];
            currInterval++;
        }

        result.add(newInterval);

        while(currInterval < intervals.length ){
            int[] interval = {intervals[currInterval][0], intervals[currInterval][1]};
            result.add(interval);
            currInterval++;
        }

        return result.toArray(new int[0][]);
    }
}
