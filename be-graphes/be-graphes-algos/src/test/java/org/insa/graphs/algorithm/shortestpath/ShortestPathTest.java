package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;

import org.insa.graphs.algorithm.*;
import org.insa.graphs.model.*;

public class ShortestPathTest {
		
	// Small graph use for tests
	private static Graph graph;

	// List of nodes
	private static Node[] nodes;

	// List of arcs in the graph, a2b is the arc from node A (0) to B (1)
	private static Arc a2b, a2c, b2d, b2e, b2f, c2a, c2b, c2f, e2c, e2d, e2f, f2e;

	@BeforeClass
	public static void initAll() throws IOException {

		// Define roads
		RoadInformation RoadInfo = new RoadInformation(RoadInformation.RoadType.UNCLASSIFIED, null, true, 1, null);

		// Create nodes
		nodes = new Node[6];
		for (int i = 0; i < nodes.length; ++i) {
			nodes[i] = new Node(i, null);
		}

		// Add arcs
		a2b = Node.linkNodes(nodes[0], nodes[1], 7, RoadInfo, null);
		a2c = Node.linkNodes(nodes[0], nodes[2], 8, RoadInfo, null);
		b2d = Node.linkNodes(nodes[1], nodes[3], 4, RoadInfo, null);
		b2e = Node.linkNodes(nodes[1], nodes[4], 1, RoadInfo, null);
		//b2f = Node.linkNodes(nodes[1], nodes[5], 5, RoadInfo, null);
		c2a = Node.linkNodes(nodes[2], nodes[0], 7, RoadInfo, null);
		c2b = Node.linkNodes(nodes[2], nodes[1], 2, RoadInfo, null);
		//c2f = Node.linkNodes(nodes[2], nodes[5], 2, RoadInfo, null);
		e2c = Node.linkNodes(nodes[4], nodes[2], 2, RoadInfo, null);
		e2d = Node.linkNodes(nodes[4], nodes[3], 2, RoadInfo, null);
		//e2f = Node.linkNodes(nodes[4], nodes[5], 3, RoadInfo, null);
		//f2e = Node.linkNodes(nodes[5], nodes[4], 3, RoadInfo, null);

		// Initialize the graph
		graph = new Graph("ID", "", Arrays.asList(nodes), null);

	}

	@Test
	@SuppressWarnings("static-access")
	public void testDoRun() {
		System.out.println("#####-----Validation test on a simple graph-----#####");

		for (int i=0;  i < nodes.length; ++i) {

			/* Affichage du point de départ */
			System.out.print("x"+(nodes[i].getId()+1) + ":");

			for (int j=0;  j < nodes.length; ++j) {
				
				if(nodes[i]==nodes[j]) {
					System.out.print("     -    ");
				} else {

					ArcInspector arcInspectorDijkstra = new ArcInspectorFactory().getAllFilters().get(0);
					ShortestPathData data = new ShortestPathData(graph, nodes[i],nodes[j], arcInspectorDijkstra);

					BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm D = new DijkstraAlgorithm(data);

					/* Récupération des solutions de Bellman et Dijkstra pour comparer */
					ShortestPathSolution solution = D.run();
					ShortestPathSolution expected = B.run();

					/* Pas de chemin trouvé */
					if (solution.getPath() == null) {
						assertEquals(expected.getPath(), solution.getPath());
						System.out.print("(infini)  ");
					} else {
					/* Un plus court chemin trouvé */

						/* Calcul du coût de la solution */
						float costSolution = solution.getPath().getLength();
						float costExpected = expected.getPath().getLength();
						assertEquals(costExpected, costSolution, 0);

						/* On récupère l'avant dernier sommet du chemin de la solution (=sommet père de la destination) */
						List<Arc> arcs_sol = solution.getPath().getArcs();
						Node originOfLastArc = arcs_sol.get(arcs_sol.size()-1).getOrigin();

						/* Affiche le couple (coût, sommet père du Dest) */
						System.out.print("("+costSolution+ ", x" + (originOfLastArc.getId()+1) + ") ");
					}
						
				}

			}

			/* Retour à la ligne */ 
			System.out.println();

		}
		System.out.println();
	}

	@Test
	public void testDistanceINSA() throws Exception {
		String mapName = "/Users/hanneoy/Documents/GitHub/BE_graphes/be-graphes/maps/insa.mapgr";
			
		ShortestPathJUnitTest test = new  ShortestPathJUnitTest();
		int origin;
		int destination;
			
		System.out.println("#####----- Validation test on a map-----######");
		System.out.println("#####----- Map : INSA ------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------######");
		System.out.println();
			
		System.out.println("----- Path from node 0 to itself ------");
		origin = 0;
		destination = 0;
		test.test(mapName, 1, 0, origin, destination);  
			
		System.out.println("----- Simple path ------");
		origin = 0;
		destination = 1;
		test.test(mapName, 1, 0, origin, destination);  	
	
			
		System.out.println("----- Non-existing nodes --------------");
		System.out.println("----- Origin : Doesn't exist ----------");
		System.out.println("----- Destination : Exists ------------");
		origin = -1;
		destination = 1;
		test.test(mapName, 1, 0, origin, destination);  	

		System.out.println("----- Non-existing nodes -------------");
		System.out.println("----- Origin : Exists ----------------");
		System.out.println("----- Destination : Doesn't exist ----");
		origin = 1;
		destination = 2000000;
		test.test(mapName, 1, 0, origin, destination); 	
			
		System.out.println("----- Non-existing nodes --------------");
		System.out.println("----- Origin : Doesn't exist ----------");
		System.out.println("----- Destination : Doesn't exist -----");
		origin = -1;
		destination = 2000000;
		test.test(mapName, 1, 0, origin, destination);  	
	}

		
	@Test
	public void testTimeINSA() throws Exception {
		String mapName = "/Users/hanneoy/Documents/GitHub/BE_graphes/be-graphes/maps/belgium.mapgr";
			
		ShortestPathJUnitTest test = new ShortestPathJUnitTest();
		int origin;
		int destination;
				
		System.out.println("#####----- Validation test on a map-----######");
		System.out.println("#####----- Map : INSA ------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------######");
		System.out.println();
				
		System.out.println("----- Path from node 0 to itself ------");
		origin = 0;
		destination = 0;
		System.out.println("origin:"+origin);
		System.out.println("destination:"+destination);
		test.test(mapName, 0, 0, origin, destination);    
				
		System.out.println("----- Simple path ------");
		origin = 0;
		destination = 1;
		test.test(mapName, 0, 0, origin, destination);    	
				
		System.out.println("----- Non-existing nodes --------------");
		System.out.println("----- Origin : Doesn't exist ----------");
		System.out.println("----- Destination : Exists ------------");
		origin = -1;
		destination = 1;
		test.test(mapName, 0, 0, origin, destination);    	

		System.out.println("----- Non-existing nodes --------------");
		System.out.println("----- Origin : Exists -----------------");
		System.out.println("----- Destination : Doesn't exist -----");
		origin = 1;
		destination = 2000000;
		test.test(mapName, 0, 0, origin, destination);    	
				
		System.out.println("----- Non-existing nodes -------------");
		System.out.println("----- Origin : Doesn't exist ---------");
		System.out.println("----- Destination : Doesn't exist-----");
		origin = -1;
		destination = 2000000;
		test.test(mapName, 0, 0, origin, destination);    	
	}	
	
	@Test
	public void testDistanceAstarINSA() throws Exception {
		String mapName = "/Users/hanneoy/Documents/GitHub/BE_graphes/be-graphes/maps/insa.mapgr";
			
		ShortestPathJUnitTest test = new  ShortestPathJUnitTest();
		int origin;
		int destination;
			
		System.out.println("#####----- Validation test on a map-----######");
		System.out.println("#####----- Map : INSA ------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------######");
		System.out.println();
			
		System.out.println("----- Path from node 0 to itself ------");
		origin = 0;
		destination = 0;
		test.test(mapName, 1, 1, origin, destination);  
			
		System.out.println("----- Simple path ------");
		origin = 0;
		destination = 1;
		test.test(mapName, 1, 1, origin, destination);  	
	
			
		System.out.println("----- Non-existing nodes --------------");
		System.out.println("----- Origin : Doesn't exist ----------");
		System.out.println("----- Destination : Exists ------------");
		origin = -1;
		destination = 1;
		test.test(mapName, 1, 1, origin, destination);  	

		System.out.println("----- Non-existing nodes -------------");
		System.out.println("----- Origin : Exists ----------------");
		System.out.println("----- Destination : Doesn't exist ----");
		origin = 1;
		destination = 2000000;
		test.test(mapName, 1, 1, origin, destination); 	
			
		System.out.println("----- Non-existing nodes --------------");
		System.out.println("----- Origin : Doesn't exist ----------");
		System.out.println("----- Destination : Doesn't exist -----");
		origin = -1;
		destination = 2000000;
		test.test(mapName, 1, 1, origin, destination);  	
	}

		
	@Test
	public void testTimeAstarINSA() throws Exception {
		String mapName = "/Users/hanneoy/Documents/GitHub/BE_graphes/be-graphes/maps/belgium.mapgr";
			
		ShortestPathJUnitTest test = new ShortestPathJUnitTest();
		int origin;
		int destination;
				
		System.out.println("#####----- Validation test on a map-----######");
		System.out.println("#####----- Map : INSA ------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------######");
		System.out.println();
				
		System.out.println("----- Path from node 0 to itself ------");
		origin = 0;
		destination = 0;
		System.out.println("origin:"+origin);
		System.out.println("destination:"+destination);
		test.test(mapName, 0, 1, origin, destination);    
				
		System.out.println("----- Simple path ------");
		origin = 0;
		destination = 1;
		test.test(mapName, 0, 1, origin, destination);    	
				
		System.out.println("----- Non-existing nodes --------------");
		System.out.println("----- Origin : Doesn't exist ----------");
		System.out.println("----- Destination : Exists ------------");
		origin = -1;
		destination = 1;
		test.test(mapName, 0, 1, origin, destination);    	

		System.out.println("----- Non-existing nodes --------------");
		System.out.println("----- Origin : Exists -----------------");
		System.out.println("----- Destination : Doesn't exist -----");
		origin = 1;
		destination = 2000000;
		test.test(mapName, 0, 1, origin, destination);    	
				
		System.out.println("----- Non-existing nodes -------------");
		System.out.println("----- Origin : Doesn't exist ---------");
		System.out.println("----- Destination : Doesn't exist-----");
		origin = -1;
		destination = 2000000;
		test.test(mapName, 0, 1, origin, destination);    	
	}
}
