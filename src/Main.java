import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		File stopTimesFile = new File("data\\stop_times.txt");
		File stopsFile = new File("data\\stops.txt");
		
		Graph UWGraph = new Graph();
		UWGraph.convertTxt(stopsFile, stopTimesFile, false);
		
		//UWGraph.printAdj();
		System.out.println("Number of nodes: " + UWGraph.getNbOfNodes());
		System.out.println("Number of edges: " + UWGraph.getNbOfEdges());
		//Number of nodes: 2433
		//Number of edges: 2759
		
		Graph WGraph = new Graph();
		WGraph.convertTxt(stopsFile, stopTimesFile, true);
		
		WGraph.printAdj();
		System.out.println("Number of nodes: " + WGraph.getNbOfNodes());
		System.out.println("Number of edges: " + WGraph.getNbOfEdges());
	}


	
}
