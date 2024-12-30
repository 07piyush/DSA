package graph;

import java.util.ArrayList;
import java.util.List;

public class Undirected {

	 public List<List<Integer>> printGraph(int V, int edges[][]) {
		 
		 /*
		  * Given an undirected graph with V nodes and E edges, 
		  * create and return an adjacency list of the graph. 0-based indexing is followed everywhere.
		  * 
		  * */
	        List<List<Integer>> adjList = new ArrayList<>(V);
	        
	        for(int i=0; i<V; i++){
	            adjList.add(new ArrayList<>());
	        }
	        
	        for(int i= 0; i<edges.length; i++){
	            int fromNode = edges[i][0];
	            int toNode = edges[i][1];
	            
	            adjList.get(fromNode).add(toNode);
	            adjList.get(toNode).add(fromNode);
	        }
	        
	        return adjList;
	    }
}
