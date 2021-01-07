import java.util.HashMap;
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

}