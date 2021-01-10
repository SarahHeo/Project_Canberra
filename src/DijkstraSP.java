import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class DijkstraSP {
	private static Map<Integer, MPD> mapMPD = new TreeMap<Integer, MPD>();
	
	public static List<Integer> dijkstra(Graph G, int startingNode) {
		List<Integer> toVisitNodes = new ArrayList<Integer>();
		List<Integer> path = new ArrayList<Integer>();
		List<DirectedEdge> listOfEdges;
		toVisitNodes.add(startingNode);
		mapMPD.put(startingNode, new MPD(false, -1, 0.0));
		
		if (verifyNonNegative(G)) {
			for (Integer key : G.getMap().keySet()) {
				if (key != startingNode){
					mapMPD.put(key, new MPD(false, -1, Double.POSITIVE_INFINITY));
				}
			}
			
			while (!toVisitNodes.isEmpty()) {  // If there is not any node left to visit, we stop the loop
				double minimalDistance = Double.POSITIVE_INFINITY;
				int currentNode = startingNode;
				// With the for loop below, we choose our node with the minimal distance as our next node
				for (int i = 0 ; i < toVisitNodes.size() ; i ++) {
					if (mapMPD.get(toVisitNodes.get(i)).distance < minimalDistance) {
						minimalDistance = mapMPD.get(toVisitNodes.get(i)).distance;
						currentNode = toVisitNodes.get(i);
					}
					
				}
				// We set our node as marked after it is selected and removed from toVisitNodes
				toVisitNodes.remove((Integer)currentNode);
				mapMPD.get(currentNode).marked = true;
				path.add(currentNode);
				
				listOfEdges = G.getMap().get(currentNode);
				for (DirectedEdge edge : listOfEdges) {
					// We update the distance of the neighbors nodes
					// We check the distance, if they are shorter, we change it, if not, we do not
					if (mapMPD.get(edge.to()).distance > mapMPD.get(edge.from()).distance + edge.weight()) {
						mapMPD.get(edge.to()).distance = mapMPD.get(edge.from()).distance + edge.weight();
						mapMPD.get(edge.to()).previous = edge.from();						
					}
					// We take care not to select again a marked node
					if (!mapMPD.get(edge.to()).marked && !toVisitNodes.contains(edge.to())) {
						toVisitNodes.add(edge.to());
					}
				}
				
				
			}
		} else {
			System.out.println("Dijkstra algorithm cannot be used as there is a negative weight in the graph!");
		}
		return path;
	}
	
	public static void updateCountSP(Graph G) {
		//G.printAdj();
		System.out.println("...Updating edge-betweenness of all edges...");
		// Launch Dijkstra from every starting node
		
		int previousNode;
		int currentNode;
		
		// Initialize unvisited
		TreeSet<Integer> unvisited = new TreeSet<Integer>();
		for (Map.Entry<Integer,List<DirectedEdge>> entry : G.getMap().entrySet()) {
			unvisited.add(entry.getKey());
		}
		
		for (int startingNode : G.getMap().keySet()) {
			mapMPD.clear();
			dijkstra(G, startingNode);

			// Find the shortest path for every reached node
			for(int destinationNode : unvisited) {
				currentNode = destinationNode;
				// Find shortest path, by looking for previous nodes
				while (currentNode != startingNode) { // while path not complete
					if (mapMPD.get(currentNode).previous == -1) { // it means there is no path between to the 2 edges
						currentNode = startingNode;
					} else {
						previousNode = mapMPD.get(currentNode).previous;
						// Find the edge between previous and currentNode
						DirectedEdge edge = G.findEdge(previousNode, currentNode);
						edge.addToCountSP(1); // We add the "score" to the edge
						DirectedEdge edge2 = G.findEdge(currentNode, previousNode);
						edge2.addToCountSP(1);
						currentNode = previousNode;
					}
				}
			}
			unvisited.remove(startingNode);
		}
	}
	
	public static void printAllShortestPaths(Graph G) throws Exception{
		// Uncomment the following lines to write the output in a textfile	
		/*PrintStream fileOut = new PrintStream("./out.txt");
		System.setOut(fileOut);*/
		for (int startingNode : G.getMap().keySet()) {
			dijkstra(G, startingNode);
			for (int destinationNode : G.getMap().keySet()) {
				printShortestPath(startingNode, destinationNode);
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
				int currentNode = destinationNode;
					while (currentNode != startingNode && currentNode != -1) {
						path = currentNode + " " + path;
						currentNode = mapMPD.get(currentNode).previous;
					}
					path = startingNode + " " + path;
				if (currentNode == -1){
					System.out.println("There is not any path between " + startingNode + " and " + destinationNode);
				} else {
					System.out.println("The path from " + startingNode + " to node " + destinationNode + " is " + path);
				}
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
		return mapMPD.get(destination).marked;
	}
	
	public static void printMarked() {
		System.out.println("Existing paths between starting node and: ");
		for (Map.Entry<Integer, MPD> entry : mapMPD.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue().marked);
		}
	}
	
	
	public static void printPrevious() {
		System.out.println("Previous node of: ");
		for (Map.Entry<Integer, MPD> entry : mapMPD.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue().previous);
		}
	}
	
	
	public static void printDistance() {
		System.out.println("Distance between starting node and: ");
		for (Map.Entry<Integer, MPD> entry : mapMPD.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue().distance);
		}
	}
	
}
