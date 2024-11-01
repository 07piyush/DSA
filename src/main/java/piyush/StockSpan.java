package piyush;

import java.util.Stack;

public class StockSpan {
	
	private Stack<int[]> stk = new Stack<>();
    private int currentIndex;

    public StockSpan() {
       currentIndex = -1;
    }
    
    public int next(int price) {
        currentIndex++;
        while(!stk.isEmpty() && stk.peek()[1] <= price){
            stk.pop();
        }
        int pge = !stk.isEmpty()?stk.peek()[0]:-1;
        int span = currentIndex-pge;
        stk.push(new int[]{currentIndex,price});
        return span;
    }
}
