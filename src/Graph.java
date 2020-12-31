import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

@SuppressWarnings("resource")
public class Graph {

	private Map<Integer, List<DirectedEdge>> map = new TreeMap<Integer,List<DirectedEdge>>();
	
	// convert GTFS data into map for a graph
	public void convertTxt(File stopsFile, File stopTimesFile, boolean weighted) throws FileNotFoundException {
		addNodesFromTxt(stopsFile);
		addEdgesFromTxt(stopTimesFile);
		if (weighted) {
			addWeightsFromTxt(stopsFile);
		}
	}
	
	private void addNodesFromTxt(File stopsFile) throws FileNotFoundException {

		Scanner myReader = new Scanner(stopsFile);
		myReader.nextLine(); // skip headers line
		int stop_id;
		
		while (myReader.hasNextLine()) {
			
			// split the line
			String line = myReader.nextLine();
			String[] arr = line.split(",");
		
			// add the id of the node
			stop_id = Integer.parseInt(arr[0]);
			if (this.map.containsKey(stop_id)) {
				System.out.println("Node in duplicate: " + stop_id);
			} else {
				List<DirectedEdge> list = new ArrayList<DirectedEdge>();
				this.map.put(stop_id, list);
			}
			
		}
	}	

	private void addEdgesFromTxt(File stopTimesFile) throws FileNotFoundException {
		
		Scanner myReader = new Scanner(stopTimesFile);
		myReader.nextLine(); // skip headers line
		
		String trip_id;
		String trip_id0 = null; // to save the trip_id of the previous line
		int stop_id;
		int stop_id0 = 0; // to save the stop_id of the previous line
		
		while (myReader.hasNextLine()) {
			
			// split the line
			String line = myReader.nextLine();
			String[] arr = line.split(",");
		
			// 
			trip_id = arr[0];
			stop_id = Integer.parseInt(arr[3]);
			
			// if we are still in the same trip, then the 2 stations are connected
			if (trip_id.equals(trip_id0)) { 
				if (!this.map.containsKey(stop_id0)) {
					System.out.println("Node does not exist: " + stop_id0);
				} else {
					List<DirectedEdge> edgesList = this.map.get(stop_id0);
					boolean exists = false;
					for (int i = 0; i < edgesList.size(); i++) { // to avoid duplicates
						if (edgesList.get(i).to() == stop_id) {
							exists = true;
							break;
						}
					}
					if (!exists) {
						DirectedEdge newEdge = new DirectedEdge(stop_id0, stop_id, 1);
						edgesList.add(newEdge);
					}
				}
			}

			trip_id0 = trip_id;
			stop_id0 = stop_id;
		}
	}

	private void addWeightsFromTxt(File stopsFile) throws FileNotFoundException {
		// TODO
	}
	
	
	public void printAdj() {
		for (Map.Entry<Integer,List<DirectedEdge>> entry : this.map.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
	
	public int getNbOfNodes(){

		int number = 0;
		for (Map.Entry<Integer,List<DirectedEdge>> entry : this.map.entrySet()) {
			number++;
		}
		return number;
	}
	
	public int getNbOfEdges(){

		int number = 0;
		for (Map.Entry<Integer,List<DirectedEdge>> entry : this.map.entrySet()) {
			for (DirectedEdge edge : entry.getValue()) {
				number++;
			}
		}
		return number;
	}
}
