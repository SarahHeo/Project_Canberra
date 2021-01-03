import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DijkstraSP {
	private static Map<Integer, Boolean> marked = new TreeMap<Integer, Boolean>();
	private static Map<Integer, List<Integer>> previous = new TreeMap<Integer, List<Integer>>();
	private static Map<Integer, Double> distance = new TreeMap<Integer, Double>();
	private static Map<Integer, Map<Integer, String>> shortestPaths = new TreeMap<Integer, Map<Integer, String>>();
	
	public static void dijkstra(Graph G, int startingNode) {
		List<Integer> toVisitNodes = new ArrayList<Integer>();
		toVisitNodes.add(startingNode);
		previous.put(startingNode, new ArrayList<Integer>(startingNode));
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
						// If the list doesn't exist we create a new one
						
						
						/********* Need to define a threshold *********/ 
						if (!previous.containsKey(edge.to())){
							previous.put(edge.to(), new ArrayList<Integer>(Arrays.asList(edge.from())));
						// Else we update it and add the new shortest path of equal distance
						} else {
							List<Integer> updatedList = previous.get(edge.to());
							updatedList.add(edge.from());
							previous.replace(edge.to(), updatedList);
						}
						
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
		// Launch Dijkstra from every starting node
		//for (int startingNode : G.getMap().keySet()) {
		int startingNode = 6155;
			marked.clear();
			previous.clear();
			distance.clear();
			dijkstra(G, startingNode);
			Map<Integer, String> paths = new TreeMap<Integer, String>();
			// Find the shortest path for every reached node
			for(Map.Entry<Integer, List<Integer>> markedNode : previous.entrySet()) {
				// Checks if there are multiple previous node, if there are, multiple equivalent SP exist
				for (int i = 0 ; i < markedNode.getValue().size() ; i ++) {
					String path = "";
					path = markedNode.getKey() + " ";
					int currentNode = markedNode.getValue().get(i);
					while (currentNode != startingNode) {
						path = currentNode + " " + path;
						// We add the "score" to the edge
						for(DirectedEdge edge : G.getMap().get(currentNode)) {
							if (edge.from() == currentNode) {
								edge.addToCountSP(1/markedNode.getValue().size());
							}
						}
						currentNode = previous.get(currentNode).get(i);
					}
					if (paths.containsKey(markedNode.getKey())) {
						path = paths.get(markedNode.getKey()) + " / " + startingNode + " " + path;
						paths.replace(markedNode.getKey(), path);
					} else {
						path = startingNode + " " + path;
						paths.put(markedNode.getKey(), path);
					}
				}	
			}
			shortestPaths.put(startingNode, paths);
		}
	//}
	
	public static void printAllShortestPaths() throws Exception{
		PrintStream fileOut = new PrintStream("./out.txt");
		System.setOut(fileOut);
		for (Map.Entry<Integer, Map<Integer, String>> paths : shortestPaths.entrySet()) {
			for(Map.Entry<Integer, String> path : paths.getValue().entrySet()) {
				System.out.println("Shortest path from " + paths.getKey() + " to " + path.getKey() + " is " + path.getValue());
			}
		}
	}
	
	public static void printShortestPath(int startingNode, int destinationNode) {
		// If there is a path found
		if (hasPathTo(destinationNode)) {
			// If the starting node is the destination node, there is no need to print the path
			if (startingNode == destinationNode) {
				System.out.println("The destination node is the starting node!");
				
			} else {
				String path = "";
				String paths = "";
				int currentNode = destinationNode;
				for (int i = 0 ; i < previous.get(currentNode).size() ; i ++) {
					while (currentNode != startingNode) {
						path = currentNode + " " + path;
						// We add the "score" to the edge
						currentNode = previous.get(currentNode).get(i);
					}
					paths = startingNode + " " + path + " / " + paths;
					currentNode = destinationNode;
				}
				
				System.out.println("The path from " + startingNode + " to node " + destinationNode + " is " + paths);
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
	
	public static void printCountSP(Graph G) {
		for (Map.Entry<Integer, List<DirectedEdge>> entry : G.getMap().entrySet()) {
			for (DirectedEdge edge : entry.getValue()) {
				System.out.println("(" + edge.from() + ", " + edge.to() + ", " + edge.getCountSP() + ")");
			}
		}
	}
	
	public static void printMarked() {
		System.out.println("Existing paths between starting node and: ");
		for (Map.Entry<Integer, Boolean> entry : marked.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
	
	
	public static void printPrevious() {
		System.out.println("Previous node of: ");
		for (Map.Entry<Integer, List<Integer>> entry : previous.entrySet()) {
			System.out.print(entry.getKey() + " : ");
			for (int node : entry.getValue()) {
				System.out.print(node + " ");
			}
			System.out.println();
		}
	}
	
	
	public static void printDistance() {
		System.out.println("Distance between starting node and: ");
		for (Map.Entry<Integer, Double> entry : distance.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
