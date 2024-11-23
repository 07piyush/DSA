package greedy;

import java.util.Arrays;

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
	
}
