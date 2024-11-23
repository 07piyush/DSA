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
	
}
