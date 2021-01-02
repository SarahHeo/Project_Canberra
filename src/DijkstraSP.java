import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DijkstraSP {
	private static Map<Integer, Boolean> marked = new TreeMap<Integer, Boolean>();
	private static Map<Integer, Integer> previous = new TreeMap<Integer, Integer>();
	private static Map<Integer, Double> distance = new TreeMap<Integer, Double>();
	private static Map<Integer, Map<Integer, String>> shortestPaths = new TreeMap<Integer, Map<Integer, String>>();
	
	public static void dijkstra(Graph G, int startingNode) {
		List<Integer> toVisitNodes = new ArrayList<Integer>();
		toVisitNodes.add(startingNode);
		previous.put(startingNode, startingNode);
		distance.put(startingNode, 0.0);
		
		if (verifyNonNegative(G)) {
			for (Integer key : G.getMap().keySet()) {
				if (key != startingNode) {
					marked.put(key, false);
					distance.put(key, Double.POSITIVE_INFINITY); // 999999999 is supposed to be infinity
				}	
			}
			
			boolean end = false;
			
			while (!end) {
				end = false;
				double minimalDistance = Double.POSITIVE_INFINITY;
				int currentNode = startingNode;
				// With the for loop below, we choose our node with the minimal distance as our next node
				for (int i = 0 ; i < toVisitNodes.size() ; i ++) {
					if (distance.get(toVisitNodes.get(i)) < minimalDistance) {
						minimalDistance = distance.get(toVisitNodes.get(i));
						currentNode = toVisitNodes.get(i);
					}
					// We select our next node and remove it from toVisitNodes
					toVisitNodes.remove((Integer)currentNode);
				}
				// We set our node as marked after it is selected and removed from toVisitNodes
				marked.put(currentNode, true);
				
				for (DirectedEdge edge : G.getMap().get(currentNode)) {
					// We update the distance of the neighbors nodes
					// We check the distance, if they are shorter, we change it, if not, we do not
					if (distance.get(edge.to()) > distance.get(edge.from()) + edge.weight()) {
						distance.put(edge.to(), distance.get(edge.from()) + edge.weight());
						previous.put(edge.to(), edge.from());
					}
					// We take care not to select again a marked node
					if (!marked.get(edge.to())) {
						toVisitNodes.add(edge.to());
					}
				}
				
				// If there is not any node left to visit, we stop the loop
				if (toVisitNodes.isEmpty()) end = true;
			}
		} else {
			System.out.println("Dijkstra algorithm cannot be used as there is a negative weight in the graph!");
		}
	}
	
	public static void findShortestPaths(Graph G) {
		for (int startingNode : G.getMap().keySet()) {
			marked.clear();
			previous.clear();
			distance.clear();
			dijkstra(G, startingNode);
			Map<Integer, String> paths = new TreeMap<Integer, String>();
			for(Map.Entry<Integer, Integer> markedNode : previous.entrySet()) {
				String path = "";			
				int currentNode = markedNode.getKey();
				while (currentNode != startingNode) {
					path = currentNode + " " + path;
					currentNode = previous.get(currentNode);
				}
				path = startingNode + " " + path;
				paths.put(markedNode.getKey(), path);
			}
			shortestPaths.put(startingNode, paths);
		}
	}
	
	
	
	public static void printAllShortestPaths() {
		for (Map.Entry<Integer, Map<Integer, String>> paths : shortestPaths.entrySet()) {
			for(Map.Entry<Integer, String> path : paths.getValue().entrySet()) {
				System.out.println("Shortest path from " + paths.getKey() + " to " + path.getKey() + " is " + path.getValue());
			}
		}
	}
	
	public static void printShortestPath(int startingNode, int destinationNode) {
		List<Integer> path = new ArrayList<Integer>();
		// If there is a path found
		if (hasPathTo(destinationNode)) {
			// If the starting node is the destination node, there is no need to print the path
			if (startingNode == destinationNode) {
				System.out.println("The destination node is the starting node!");
				
			} else {
				int currentNode = destinationNode;
				
				while (currentNode != startingNode) {
					path.add(0,currentNode) ;
					currentNode = previous.get(currentNode);
				}
				
				path.add(0,startingNode);
				System.out.print("The path from " + startingNode + " to node " + destinationNode + " is ");
				for (int i : path) {
					System.out.print(i + " ");
				}
				System.out.println();
			}
			
		} else {
			System.out.println("There is not any path between " + startingNode + " and " + destinationNode);
		}
	}
	
	public static boolean verifyNonNegative(Graph G) {
		boolean result = true;
		for (Map.Entry<Integer, List<DirectedEdge>> entry : G.getMap().entrySet()) {
			for (DirectedEdge edge: entry.getValue()) {
				if (edge.weight() < 0) {
					result = false;
				}
			}
		}
		return result;
	}
	
	public static boolean hasPathTo(int destination) {
		return marked.get(destination);
	}
	
	public static void printMarked() {
		System.out.println("Existing paths between starting node and: ");
		for (Map.Entry<Integer, Boolean> entry : marked.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
	public static void printPrevious() {
		System.out.println("Previous node of: ");
		for (Map.Entry<Integer, Integer> entry : previous.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
	public static void printDistance() {
		System.out.println("Distance between starting node and: ");
		for (Map.Entry<Integer, Double> entry : distance.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
