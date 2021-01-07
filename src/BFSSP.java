import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BFSSP {
	private static Map<Integer, Boolean> marked = new TreeMap<Integer, Boolean>();
	private static Map<Integer, Integer> previous = new TreeMap<Integer, Integer>();
	private static Map<Integer, Integer> distance = new TreeMap<Integer, Integer>();
	//private static Map<Integer, Map<Integer, List<Integer>>> shortestPaths2 = new TreeMap<Integer, Map<Integer, List<Integer>>>();
	private static Map<Integer, Map<Integer, String>> shortestPaths = new TreeMap<Integer, Map<Integer, String>>();
	
	public static List<Integer> bfs(Graph G, int s) {
		List<Integer> path = new ArrayList<Integer>();
		List<Integer> toVisitNodes = new ArrayList<Integer>();
		for (Integer key : G.getMap().keySet()) {
			marked.put(key, false);
		}
		toVisitNodes.add(s);
		previous.put(s, -1);
		distance.put(s, 0);
		
		if (G.isWeighted()) {
			
			System.out.println("BFS cannot be used on weighted graph, please consider using Dijkstra Algorithm instead.");
			
		} else {
			
			while (!toVisitNodes.isEmpty()) {

				int currentNode = toVisitNodes.remove(0);
				marked.replace(currentNode, true);
				path.add(currentNode);
				
				for (DirectedEdge edge : G.getMap().get(currentNode)) {
					if (!toVisitNodes.contains(edge.to()) && !marked.get(edge.to())) {
						toVisitNodes.add(edge.to());
						previous.put(edge.to(), currentNode);
						distance.put(edge.to(), distance.get(currentNode)+1);					
					}
				}
			}
		}
		return path;
	}	
	
	public static void findShortestPaths(Graph G) {
		// Launch a bfs from every starting node
		for (int startingNode : G.getMap().keySet()) {
			marked.clear();
			previous.clear();
			distance.clear();
			bfs(G, startingNode);
			Map<Integer, String> paths = new TreeMap<Integer, String>();
			// Find the shortest path for every reached node
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
	
	/*
	public static void printAllShortestPaths() {
		for (Map.Entry<Integer, Map<Integer, List<Integer>>> paths : shortestPaths.entrySet()) {
			for(Map.Entry<Integer, List<Integer>> path : paths.getValue().entrySet()) {
				System.out.println("Shortest path from " + paths.getKey() + " to " + path.getKey() + " is ");
				for (int i : path.getValue()) {
					System.out.print(i + " ");
				}
			}
		}
	}
	*/
	
	public static void printShortestPath(int startingNode, int destinationNode) {
		List<Integer> path = new ArrayList<Integer>();
		// If there is a path found
		if (hasPathTo(destinationNode)) {
			// If the starting node is the destination node, there is no need to print the path
			if (startingNode == destinationNode) {
				System.out.println("The destination node is the starting node!");
				
			} else {
				int currentNode = destinationNode;
				
				while (currentNode != startingNode && currentNode != -1) {
					path.add(0,currentNode) ;
					currentNode = previous.get(currentNode);
				}
				
				path.add(0,startingNode);
				if (currentNode == -1){
					System.out.println("There is not any path between " + startingNode + " and " + destinationNode);
				} else {
					System.out.print("The path from " + startingNode + " to node " + destinationNode + " is ");
					for (int i : path) {
						System.out.print(i + " ");
					}
					System.out.println();
				}
				
			}
			
		} else {
			System.out.println("There is not any path between " + startingNode + " and " + destinationNode);
		}
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
		for (Map.Entry<Integer, Integer> entry : distance.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
