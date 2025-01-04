package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Undirected {

	public List<List<Integer>> printGraph(int V, int edges[][]) {

		/*
		 * Given an undirected graph with V nodes and E edges, 
		 * create and return an adjacency list of the graph. 0-based indexing is followed everywhere.
		 * 
		 * There are two ways to represent a graph. 1. Adjacency matrix 2. Adjacency List.
		 * 
		 * Adjacency matrix : is [V, E] matrix V=no of vertices and E=no of edges. very inefficient.
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

	public ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
		/*
		 * Given a connected undirected graph represented by an adjacency list adj, which is a vector of vectors where 
		 * each adj[i] represents the list of vertices connected to vertex i. Perform a Breadth First Traversal (BFS) 
		 * starting from vertex 0, visiting vertices from left to right according to the adjacency list, and return a 
		 * list containing the BFS traversal of the graph.
		 * 
		 * 
		 * */
		Set<Integer> visited = new HashSet<>();
		Queue<Integer> queue = new LinkedList<>();
		ArrayList<Integer> bfs = new ArrayList<>();

		if(adj.size() == 0) return bfs;

		queue.add(0);
		visited.add(0);
		while(!queue.isEmpty())
		{
			int frontValue = queue.poll();
			bfs.add(frontValue);
			for(int node : adj.get(frontValue)){
				if(!visited.contains(node))
					queue.add(node);
				visited.add(node);
			}
		}
		return bfs;
	}

	public ArrayList<Integer> dfsOfGraph(ArrayList<ArrayList<Integer>> adj) {
		/*
		 * Given a connected undirected graph represented by an adjacency list adj, which is a vector of vectors where 
		 * each adj[i] represents the list of vertices connected to vertex i. Perform a Depth First Traversal (DFS) 
		 * starting from vertex 0, visiting vertices from left to right as per the adjacency list, and return a list 
		 * containing the DFS traversal of the graph.
		 * 
		 * */
		ArrayList<Integer> dfs = new ArrayList<>();
		Set<Integer> visited = new HashSet<>();
		if(adj.isEmpty()) return dfs;

		dfs(adj, dfs, visited, 0);

		return dfs;
	}

	private void dfs(ArrayList<ArrayList<Integer>> adj, ArrayList<Integer> dfs, Set<Integer> visited, int node){

		if(!visited.contains(node)){
			dfs.add(node);
			visited.add(node);
		}
		for(int n : adj.get(node)){
			if(!visited.contains(n))
				dfs(adj, dfs, visited, n);
		}
	}
	
	public int findCircleNum(int[][] isConnected) {
		/*
		 * Return the number of provinces.
		 * A province is a group of directly or indirectly connected cities and no other cities outside of the group.
		 * 
		 * */
        Set<Integer> visited = new HashSet<>();
        int provinces = 0;
        for(int i=0; i<isConnected.length; i++){
            if(!visited.contains(i)){
                provinces++;
                dfs(isConnected, visited, i);
            }
        }
        return provinces;
    }
    private void dfs(int[][] isConnected, Set<Integer> visited, int node){
        visited.add(node);
        for(int n=0; n<isConnected.length; n++){
            if(isConnected[node][n]==1 && !visited.contains(n))
                dfs(isConnected, visited, n);
        }
    }
    
    class Triplet{
        public Integer i;
        public Integer j;
        public Integer level;
        public Triplet(Integer i, Integer j, Integer level){
            this.i = i;
            this.j = j;
            this.level = level;
        }
    }

    public int orangesRotting(int[][] grid) {
        Queue<Triplet> queue = new LinkedList<>();
        int[][] visited = new int[grid.length][];
        int maxTime = 0;
        int countFresh = 0;
        //1. put all (i,j) = 2 nodes in queue.
        for(int i=0; i<grid.length; i++){
            visited[i] = new int[grid[i].length];
            for(int j=0; j<grid[i].length; j++){
                if(grid[i][j] == 2){
                    Triplet node = new Triplet(i,j,0);
                    visited[i][j] = 2;
                    queue.add(node);
                } 
                else if(grid[i][j] == 1) countFresh++;
            }
        }
        //2. perform bfs on queue.
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};
        int count = 0;
        while(!queue.isEmpty()){
            Triplet node = queue.poll();
            maxTime = maxTime>node.level?maxTime:node.level;
            for(int neighbour=0; neighbour<4; neighbour++){
                int row = node.i + delRow[neighbour];
                int col = node.j + delCol[neighbour];
                if(row > -1 && row < grid.length && col < grid[row].length && col > -1 && 
                visited[row][col] != 2 && grid[row][col] == 1){
                    queue.add(new Triplet(row, col, node.level+1));
                    visited[row][col] = 2;
                    count++;
                }
            }
            
        }

        return count<countFresh?-1:maxTime;
    }
}
