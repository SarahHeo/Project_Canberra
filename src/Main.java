import java.io.File;
import java.util.Map;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) throws Exception {

		int input;
		int answer;
		int answer2;
		Scanner scanner = new Scanner(System.in);
		
		File stopTimesFile = new File("data\\stop_times.txt");
		File stopsFile = new File("data\\stops.txt");
		Graph UWGraph = new Graph();
		Graph WGraph = new Graph();
		Graph UndirectedWGraph = new Graph();
		
		do {
			System.out.println("\n"
			+ "-------- Part 2 --------" + "\n"
			+ "1- Build unweighted graph" + "\n"
			+ "2- Build weighted graph" + "\n"
			+ "3- Print unweighted graph" + "\n"
			+ "4- Print weighted graph" + "\n"
			
			+ "-------- Part 3 --------" + "\n"
			+ "5- Find shortest path on unweighted graph (BFS)" + "\n"
			+ "6- Display all shortest paths on unweighted graph (BFS) [/!\\ takes lots of resources]" + "\n"
			+ "7- Find shortest path on weighted graph (Dijkstra)" + "\n"
			+ "8- Display all shortest paths on weighted graph (Dijkstra) [/!\\ takes lots of resources]" + "\n"
			
			+ "-------- Part 4 --------" + "\n"
			+ "9-" + "\n"
			
			+ "0- Quit" + "\n");
			
			
			input = Integer.parseInt(scanner.nextLine());
			
			switch (input) {
				/******* Part 2 *******/
					case 1:
						UWGraph = new Graph();
						UWGraph.convertTxt(stopsFile, stopTimesFile, false, true);
						System.out.println("Number of nodes: " + UWGraph.getNbOfNodes()); //Number of nodes: 2433
						System.out.println("Number of edges: " + UWGraph.getNbOfEdges()); //Number of edges: 2759
						break;
					case 2:
						WGraph = new Graph();
						WGraph.convertTxt(stopsFile, stopTimesFile, true, true);
						System.out.println("Number of nodes: " + WGraph.getNbOfNodes());
						System.out.println("Number of edges: " + WGraph.getNbOfEdges());
						break;
					case 3:
						UWGraph.printAdj();
						break;
					case 4:
						WGraph.printAdj();
						break;
						
				/******* Part 3 *******/
					case 5:
						System.out.print("Find path from: ");
						answer = Integer.parseInt(scanner.nextLine());
						BFSSP.bfs(UWGraph,answer); //6155
						System.out.print("To: ");
						answer2 = Integer.parseInt(scanner.nextLine());
						BFSSP.printShortestPath(answer, answer2); //6155, 6104
						break;
					case 6:
						//The following lines are taking a lot of time and resources to process, be careful when testing
						BFSSP.findShortestPaths(UWGraph);
						BFSSP.printAllShortestPaths();
						break;
					case 7:
						System.out.print("Find path from: ");
						answer = Integer.parseInt(scanner.nextLine());
						DijkstraSP.dijkstra(WGraph,answer); //6155
						System.out.print("To: ");
						answer2 = Integer.parseInt(scanner.nextLine());
						DijkstraSP.printShortestPath(answer,answer2); //6155, 6104
						break;
					case 8:
						//The following lines are taking a lot of time and resources to process, be careful when testing
						DijkstraSP.findShortestPaths(WGraph);
						DijkstraSP.printAllShortestPaths();
						break;
						
					/******* Part 4 *******/	
					case 9:
						UndirectedWGraph = new Graph();
						UndirectedWGraph.convertTxt(stopsFile, stopTimesFile, true, false);
						System.out.println("Number of nodes: " + UndirectedWGraph.getNbOfNodes()); //Number of nodes: 2433
						System.out.println("Number of edges: " + UndirectedWGraph.getNbOfEdges()); //Number of edges: 2759
						//UndirectedWGraph.printAdj();
						
						//DijkstraSP.dijkstra(UndirectedWGraph,6155);
						//DijkstraSP.printShortestPath(6155,6104);
						//DijkstraSP.findShortestPaths(UndirectedWGraph);
						//DijkstraSP.printCountSP(UndirectedWGraph);
						//DijkstraSP.printAllShortestPaths();

						/*GraphClustering.findClusters(UndirectedWGraph); // 1
						GraphClustering.printClusters();*/
						
						GraphClustering.createClusters(UndirectedWGraph, 2);

						break;
				case 0:
					break;
				default:
					System.out.println("Incorrect input");
			}
			
		} while (input != 0);

	}
}
