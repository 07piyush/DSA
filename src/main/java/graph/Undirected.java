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
    
    class Pair{
        int row;
        int col;
        
        public Pair(int r, int c){
            row=r;
            col=c;
        }
        
    }
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    	/*
    	 * flood fill all pixels that are of color in sr,sc or become of consecutively.
    	 * 
    	 * approach 1 : BFS : Time : O(m*n) as we are visiting each element in worst case (all pixel are of same color)
    	 * 					  Space : O(m*n) in worst case at a time there will be approx all pixels in queue.
    	 * 								also visited grid will require O(m*n) space. 
    	 * 
    	 * approach 2 : DFS : Time : O(m*n) visiting all elements.
    	 * 					  Space : O(m*n) all nodes are in stack. + visited array.
    	 * */
    	
        int[][] result = new int[image.length][image[0].length];
        boolean[][] visited = new boolean[image.length][image[0].length];
        for(int i=0; i<image.length; i++){
            for(int j=0; j<image[i].length; j++){
                result[i][j] = image[i][j];
            }
        }
        Queue<Pair> queue = new LinkedList<>();

        queue.add(new Pair(sr,sc));
        visited[sr][sc] = true;

        int[] deltaRow = {-1, 0, 1, 0};
        int[] deltaCol = {0, 1, 0, -1};
        while(!queue.isEmpty()){
            Pair node = queue.poll();
            result[node.row][node.col] = color;
            for(int neighbour=0; neighbour<4; neighbour++){
                int cRow = node.row + deltaRow[neighbour];
                int cCol = node.col + deltaCol[neighbour];
                if(cRow>-1 && cRow< image.length && cCol >-1 && cCol<image[cRow].length && !visited[cRow][cCol] && image[cRow][cCol] == image[node.row][node.col]){
                    queue.add(new Pair(cRow, cCol));
                    visited[cRow][cCol] = true;
                }
            }
        }

        return result;
    }
    
    class Pair2{
        int node;
        int from;
        
        Pair2(int curr, int parent){
            node=curr;
            from=parent;
        }
    }
    
    public boolean isCycle(ArrayList<ArrayList<Integer>> adj) {
        /*
         * Detect cycle in undirected graph.
         * 
         * when traversing graph, if a node is already visited but is not parent node then that means it has already
         * been visited before. 
         * 
         * */
        int count = adj.size();
        boolean hasloop = false;
        boolean[] visited = new boolean[count];
        for(int i =0; i<count; i++){
            //for each unvisited node, do dfs.
            if(!visited[i]) hasloop = bfs(adj, visited, i);
            
            if(hasloop) return true;
            
        }
        return hasloop;
    }
    
    private boolean bfs(ArrayList<ArrayList<Integer>> adj, boolean[] visited, int startNode){
        visited[startNode] = true;
        Queue<Pair2> queue = new LinkedList<>();
        queue.add(new Pair2(startNode, -1));
        
        while(!queue.isEmpty()){
            Pair2 item = queue.poll();
            
            for(int neighbor : adj.get(item.node)){
                if(!visited[neighbor]){
                    visited[neighbor] = true;
                    queue.add(new Pair2(neighbor, item.node));    
                }
                else if(neighbor != item.from){
                    return true;
                }
            }
        }
        return false;
    }
    
    class Triplet2{
        int r;
        int c;
        int dist;

        public Triplet2(int row, int col, int distance){
            r=row;
            c=col;
            dist=distance;
        }
    }
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] result = new int[m][n];
        Queue<Triplet2> queue = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(mat[i][j] == 0){
                    queue.add(new Triplet2(i,j,0));
                    visited[i][j] = true;
                }
            }
        }
        int[] deltaR = new int[]{-1, 0, 1, 0};
        int[] deltaC = new int[]{0, 1, 0, -1};
        while(!queue.isEmpty()){
        	Triplet2 curr = queue.poll();
            int row = curr.r;
            int col = curr.c;
            int dist = curr.dist;
            result[row][col] = dist;
            for(int neighbor=0; neighbor<4; neighbor++){
                int nR = row + deltaR[neighbor];
                int nC = col + deltaC[neighbor];
                if(nR<m && nR>-1 && nC<n && nC>-1 && !visited[nR][nC]){
                    queue.add(new Triplet2(nR,nC,dist+1));
                    visited[nR][nC] = true;
                }
            }
        }
        return result;
    }
    
    public void solve(char[][] board) {
    	
    	/*
    	 * 130. Surrounded Regions - leetcode
    	 * 
    	 * */
    	
        int m = board.length;
        int n = board[0].length;
        boolean[][] unconvertible = new boolean[m][n];
        Queue<Pair> queue = new LinkedList<>();
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(board[i][j] == 'O' && !unconvertible[i][j]){
                   if(i==0 || i == m-1 || j==0 || j==n-1){
                    //node is on the edge
                    findAndMark(i,j,board,unconvertible);
                   }
                }
            }
        }

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(board[i][j] == 'O' && !unconvertible[i][j])
                    board[i][j] = 'X';
            }
        }
    }

    private void findAndMark(int r, int c, char[][] board, boolean[][] unconvertible){
        if(!unconvertible[r][c] && board[r][c]=='O'){
            unconvertible[r][c] = true;
        }
        int m = board.length;
        int n = board[0].length;
        int[] delR = new int[]{-1,0,1,0};
        int[] delC = new int[]{0,1,0,-1};

        for(int i=0; i<4; i++){
            int nR = r+delR[i];
            int nC = c+delC[i];
            if(nR<m && nR>-1 && nC<n && nC>-1 && !unconvertible[nR][nC])
                findAndMark(nR, nC, board, unconvertible);
        }
    }
}
