import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BFSSP {
	private static Map<Integer, MPD> mapMPD = new TreeMap<Integer, MPD>();
	private static Map<Integer, Map<Integer, String>> shortestPaths = new TreeMap<Integer, Map<Integer, String>>();
	
	public static List<Integer> bfs(Graph G, int startingNode) {
		List<Integer> path = new ArrayList<Integer>();
		List<Integer> toVisitNodes = new ArrayList<Integer>();
		for (Integer key : G.getMap().keySet()) {
			mapMPD.put(key, new MPD(false, -1, 0.0));
		}
		toVisitNodes.add(startingNode);
		mapMPD.put(startingNode, new MPD(false, -1, 0.0));
		
		if (G.isWeighted()) {
			
			System.out.println("Please consider using Dijkstra Algorithm on weighted graph if you would like to find shortest paths.");
			
		}
			
			while (!toVisitNodes.isEmpty()) {

				int currentNode = toVisitNodes.remove(0);
				mapMPD.get(currentNode).marked = true;
				path.add(currentNode);
				
				for (DirectedEdge edge : G.getMap().get(currentNode)) {
					if (!toVisitNodes.contains(edge.to()) && !mapMPD.get(edge.to()).marked) {
						toVisitNodes.add(edge.to());
						mapMPD.get(edge.to()).previous = currentNode;
						mapMPD.get(edge.to()).distance = mapMPD.get(currentNode).distance+1;					
					}
				}
			}
		//}
		return path;
	}	
	
	public static void printAllShortestPaths(Graph G) throws Exception{
		// Uncomment the following lines to write the output in a textfile	
		/*PrintStream fileOut = new PrintStream("./out.txt");
		System.setOut(fileOut);*/
		for (int startingNode : G.getMap().keySet()) {
			bfs(G, startingNode);
			for (int destinationNode : G.getMap().keySet()) {
				printShortestPath(startingNode, destinationNode);
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
				
				while (currentNode != startingNode && currentNode != -1) {
					path.add(0,currentNode) ;
					currentNode = mapMPD.get(currentNode).previous;
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
