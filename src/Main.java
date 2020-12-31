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
		
		UWGraph UWGraph = new UWGraph();
		UWGraph.convertTxt(stopsFile, stopTimesFile);
		
		UWGraph.printAdj();
		System.out.println("Number of nodes: " + UWGraph.getNbOfNodes());
		System.out.println("Number of edges: " + UWGraph.getNbOfEdges());
	}


	
}
