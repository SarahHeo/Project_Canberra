import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

@SuppressWarnings("resource")
public class UWGraph {

	private Map<Integer, List<Integer>> map = new TreeMap<Integer,List<Integer>>();
	
	// convert GTFS data into map for an unweigted graph
	public void convertTxt(File stopsFile, File stopTimesFile) throws FileNotFoundException {
		addNodesFromTxt(stopsFile);
		addEdgesFromTxt(stopTimesFile);
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
				List<Integer> list = new ArrayList<Integer>();
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
			
			// if we are still in the same trip
			if (trip_id.equals(trip_id0)) { // then the 2 stations are connected
				if (!this.map.containsKey(stop_id0)) {
					System.out.println("Node does not exist: " + stop_id0);
				} else if (!this.map.get(stop_id0).contains(stop_id)){ // to avoid duplicates
					this.map.get(stop_id0).add(stop_id);
				}
			}

			trip_id0 = trip_id;
			stop_id0 = stop_id;
		}
	}

	public void printAdj() {
		for (Map.Entry<Integer,List<Integer>> entry : this.map.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
	
	public int getNbOfNodes(){

		int number = 0;
		for (Map.Entry<Integer,List<Integer>> entry : this.map.entrySet()) {
			number++;
		}
		return number;
	}
	
	public int getNbOfEdges(){

		int number = 0;
		for (Map.Entry<Integer,List<Integer>> entry : this.map.entrySet()) {
			for (Integer edge : entry.getValue()) {
				number++;
			}
		}
		return number;
	}
}
