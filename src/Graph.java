import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

@SuppressWarnings("resource")
public class Graph {

	private Map<Integer, List<DirectedEdge>> map = new TreeMap<Integer,List<DirectedEdge>>();
	private boolean weighted;
	private boolean directed;
	
	// convert GTFS data into map for a graph
	public void convertTxt(File stopsFile, File stopTimesFile, boolean weighted, boolean directed) throws FileNotFoundException {
		this.weighted = weighted;
		this.directed = directed;
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
			
			if (directed){
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
			} else if (!directed){
				if (trip_id.equals(trip_id0)) { 
					if (!this.map.containsKey(stop_id0)) {
						System.out.println("Node does not exist: " + stop_id0);
					} else if (!this.map.containsKey(stop_id)) {
						System.out.println("Node does not exist: " + stop_id);
					} else {	
						List<DirectedEdge> edgesList0 = this.map.get(stop_id0);
						List<DirectedEdge> edgesList = this.map.get(stop_id);
						boolean exists = false;
						for (int i = 0; i < edgesList0.size(); i++) { // to avoid duplicates
							if (edgesList0.get(i).to() == stop_id) {
								exists = true;
								break;
							}
						}
						if (!exists) {
							DirectedEdge newEdge0 = new DirectedEdge(stop_id0, stop_id, 1);
							DirectedEdge newEdge = new DirectedEdge(stop_id, stop_id0, 1);
							edgesList.add(newEdge);
							edgesList0.add(newEdge0);
						}
					}
				}
			}

			trip_id0 = trip_id;
			stop_id0 = stop_id;

		}
		
		/*DirectedEdge newEdge = new DirectedEdge(6155, 6092, 1);
		this.map.get(6155).add(newEdge);
		newEdge = new DirectedEdge(6092, 6104, 1);
		this.map.get(6092).add(newEdge);
		
		newEdge = new DirectedEdge(6155, 4919, 1);
		this.map.get(6155).add(newEdge);
		newEdge = new DirectedEdge(4919, 6104, 1);
		this.map.get(4919).add(newEdge);*/
	}

	private void addWeightsFromTxt(File stopsFile) throws FileNotFoundException {
		
		Scanner myReader = new Scanner(stopsFile);
		myReader.nextLine(); // skip headers line
		int stop_id;
		double stop_lat;
		double stop_long;
		
		// Create a map (node, coordinates)
		Map<Integer, Coordinates> mapCoord = new TreeMap<Integer,Coordinates>();			
		while (myReader.hasNextLine()) {
			
			String line = myReader.nextLine();
			String[] arr = line.split(",");
		
			stop_id = Integer.parseInt(arr[0]);
			stop_lat = Double.parseDouble(arr[3]);
			stop_long = Double.parseDouble(arr[4]);
			
			if (mapCoord.containsKey(stop_id)) {
				System.out.println("Node in duplicate: " + stop_id);
			} else {
				Coordinates coord = new Coordinates(stop_lat, stop_long);
				mapCoord.put(stop_id, coord);
			}
		}
		
		int source;
		int destination;
		double sourceLat;
		double sourceLong;
		double destinationLat;
		double destinationLong;
		double distance;
		
		for (Map.Entry<Integer,List<DirectedEdge>> entry : this.map.entrySet()) {
			for (DirectedEdge edge : entry.getValue()) {
				source = edge.from();
				destination = edge.to();
				sourceLat = mapCoord.get(source).getLatitude();
				sourceLong = mapCoord.get(source).getLongitude();
				destinationLat = mapCoord.get(destination).getLatitude();
				destinationLong = mapCoord.get(destination).getLongitude();
				
				// euclidian distance: sqrt((x2-x1)�+(y2-y1)�)
				distance = Math.sqrt(Math.pow((destinationLat-sourceLat),2) + Math.pow((destinationLong-sourceLong),2));
				edge.setWeight(distance);
			}
		}
	}

	public void printAdj() {
		for (Map.Entry<Integer,List<DirectedEdge>> entry : this.map.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	public int getNbOfNodes(){
		return this.map.entrySet().size();
	}

	public int getNbOfEdges(){
		int number = 0;
		for (Map.Entry<Integer,List<DirectedEdge>> entry : this.map.entrySet()) {
			number += entry.getValue().size();
		}
		return number;
	}

	public DirectedEdge findEdge(int source, int destination) {
		for(DirectedEdge edge : this.getMap().get(source)) {
			if (edge.to() == destination) {
				return edge;
			}
		}
		return null;
	}
	
	public void removeEdge(int source, int destination) {
		List<DirectedEdge> listOfEdges = this.getMap().get(source);
		for(DirectedEdge edge : listOfEdges) {
			if (edge.to() == destination) {
				listOfEdges.remove(edge);
				break;
			}
		}
		if (!this.directed) {
			listOfEdges = this.getMap().get(destination);
			for(DirectedEdge edge : listOfEdges) {
				if (edge.to() == source) {
					listOfEdges.remove(edge);
					break;
				}
			}
		}
		
	}
	
	public void resetAllCountSP() {
		for (Map.Entry<Integer,List<DirectedEdge>> entry : this.getMap().entrySet()) {
			for(DirectedEdge edge : entry.getValue()) {
				edge.resetCountSP();
			}
		}
	}
	
	public Map<Integer,List<DirectedEdge>> getMap(){
		return this.map;
	}
	
	public boolean isWeighted() {
		return this.weighted;
	}

}
