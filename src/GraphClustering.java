import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GraphClustering {

	private static Map<Integer, Cluster> clusters = new HashMap<Integer, Cluster>();
	private static Map<Integer, Boolean> marked = new TreeMap<Integer, Boolean>(); // new map for clustering as bfs will reset the marked value at each call
	
	public static Map<Integer, Cluster> getClusters() {
		return clusters;
	}

	public static void findClusters(Graph g) {
		System.out.println("...Searching for clusters...");
		for (int key : g.getMap().keySet()) {
			marked.put(key, false);
		}
		
		int i = 0;
		for (int node : g.getMap().keySet()) {
			if (marked.get(node) == false) {
				Cluster cluster = new Cluster();
				List<Integer> bfsOutput = BFSSP.bfs(g, node); // bfs will go through each connected component
				for (int bfsNode : bfsOutput) {
					cluster.addNode(bfsNode);
					marked.put(bfsNode, true);
				}
				clusters.put(i++,  cluster);
			}
		}
	}
	
	public static void createClusters(Graph G, int n) throws Exception	{
		System.out.println("");
		int startingNode;
		int destinationNode;
		int edge_betweenness;
		
		//G.removeEdge(8889,3356);
		//G.removeEdge(8888,3003);
		//G.removeEdge(3356,3419);
		//G.removeEdge(3419,4530);
		
		G.resetAllCountSP();
		DijkstraSP.findShortestPaths(G);
		findClusters(G);
		printClusters();
		
		while (clusters.size() < n){
			
			startingNode = -1;
			destinationNode = -1;
			edge_betweenness = 0;
			
			System.out.println("\n...Calculating edge betweenness...");
			// Find the highest edge_betweenness
			for (Map.Entry<Integer, List<DirectedEdge>> entry : G.getMap().entrySet()){
				for (DirectedEdge edge : entry.getValue()){
					if (edge.getCountSP() > edge_betweenness){
						startingNode = edge.from();
						destinationNode = edge.to();
						edge_betweenness = edge.getCountSP();
					}
				}
			}
			
			System.out.println("Edge (" + startingNode + ";" + destinationNode + ") with " + edge_betweenness + " SP going through will be removed");
			// Remove the edge
			G.removeEdge(startingNode, destinationNode);
			
			G.resetAllCountSP();
			DijkstraSP.findShortestPaths(G);
			findClusters(G);
			printClusters();
		}
	}
	
	public static void printClusters() {
		int nb;
		int nbOfNodes;
		for (Map.Entry<Integer, Cluster> cluster : GraphClustering.getClusters().entrySet()) {
			nb = cluster.getKey() + 1;
			nbOfNodes = cluster.getValue().getNodes().size();
			System.out.print("Cluster " + nb + " (" + nbOfNodes + " stations): ");
			for (int clusterUnit : cluster.getValue().getNodes()) {
				System.out.print(clusterUnit + " ");
			}
			System.out.print("\n");
		}
		System.out.println("\n" + GraphClustering.getClusters().size() + " cluster(s) have been found");
	}
	
}