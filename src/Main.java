import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

// import Unweigted_graphs.BFSShortestPaths;

public class Main {

	public static void main(String[] args) throws Exception {

		/******* Part 2 *******/
			File stopTimesFile = new File("data\\stop_times.txt");
			File stopsFile = new File("data\\stops.txt");
			
			Graph UWGraph = new Graph();
			UWGraph.convertTxt(stopsFile, stopTimesFile, false);
			
			//UWGraph.printAdj();
			//System.out.println("Number of nodes: " + UWGraph.getNbOfNodes());
			//System.out.println("Number of edges: " + UWGraph.getNbOfEdges());
			//Number of nodes: 2433
			//Number of edges: 2759
			
			Graph WGraph = new Graph();
			WGraph.convertTxt(stopsFile, stopTimesFile, true);
			
			//WGraph.printAdj();
			System.out.println("Number of nodes: " + WGraph.getNbOfNodes());
			System.out.println("Number of edges: " + WGraph.getNbOfEdges());
			
		
		/******* Part 3 *******/
			
			//System.out.println("Using BFS on unweighted graph:");
			//BFSSP.bfs(UWGraph,6155);
			//BFSSP.printShortestPath(6155, 6104);
			
			//The following lines are taking a lot of time and resources to process, be careful when testing
			//BFSSP.findShortestPaths(UWGraph);
			//BFSSP.printAllShortestPaths();
			
			//System.out.println("Using Dijkstra on weighted graph:");
			//DijkstraSP.dijkstra(UWGraph,6155);
			//DijkstraSP.printShortestPath(6155,6104);
			
			//The following lines are taking a lot of time and resources to process, be careful when testing
			//DijkstraSP.findShortestPaths(UWGraph);
			//DijkstraSP.printAllShortestPaths();
			
		/******* Part 4 *******/
			
			//DijkstraSP.dijkstra(UWGraph,6155);
			//DijkstraSP.printShortestPath(6155,6104);
			
			DijkstraSP.findShortestPaths(UWGraph);
			DijkstraSP.printCountSP(UWGraph);
			DijkstraSP.printAllShortestPaths();
			//DijkstraSP.printAllShortestPaths();
			
			/*BFSShortestPaths BFSShortestPaths = new BFSShortestPaths();
			BFSShortestPaths.BFS_SP(graphUWUD, 1);
			int node = 3;
			System.out.println("Path to node " + node + "? " + BFSShortestPaths.hasPathTo(node));
			System.out.println("Distance to node " + node + ": " + BFSShortestPaths.distTo(node));
			System.out.print("Path to node " + node + ": "); BFSShortestPaths.printSP(node);
			System.out.println();*/
	}

}
