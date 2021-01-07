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
		DijkstraSP.findShortestPaths(G);
		findClusters(G);
		DijkstraSP.printCountSP(G);
		while (clusters.size() < n){
			int startingNode = -1;
			int destinationNode = -1;
			int edge_betweenness = 0;
			DijkstraSP.findShortestPaths(G);
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

			for(Iterator<DirectedEdge> iterator = G.getMap().get(startingNode).iterator(); iterator.hasNext(); ) {
				DirectedEdge edge = iterator.next();
				if (edge.to() == destinationNode){
					iterator.remove();
				}
			}
			
			for(Iterator<DirectedEdge> iterator = G.getMap().get(destinationNode).iterator(); iterator.hasNext(); ) {
				DirectedEdge edge = iterator.next();
				if (edge.to() == startingNode){
					iterator.remove();
				}
			}
			findClusters(G);
		}
		
		for (Map.Entry<Integer, Cluster> cluster : GraphClustering.getClusters().entrySet()) {
			System.out.print("Cluster " + cluster.getKey() + " : ");
			for (int clusterUnit : cluster.getValue().getNodes()) {
				System.out.print(clusterUnit + " ");
			}
		}
		System.out.println("\n" + GraphClustering.getClusters().size() + " clusters have been found");
	}
	
	
}