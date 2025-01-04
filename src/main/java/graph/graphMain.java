package graph;

public class graphMain {

	public static void main(String[] args) {
		Undirected unGraph = new Undirected();
		
		int[][] graph = new int[3][];
		graph[0] = new int[] {2,1,1};
		graph[1] = new int[] {1,1,0};
		graph[2] = new int[] {0,1,1};

		unGraph.orangesRotting(graph);

	}

}
