package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.*;
import org.insa.graphs.model.io.*;

public class ShortestPathJUnitTest {

	@Test 
	public void test(String mapName, int mode, int algo, int originID, int destinationID) throws Exception {
	// mode is to indicate if we're comparing time (0) or distance (1)
	// algo is to indicate if we're using Dijkstra (0) or AStar (1)
		
		//Create a graph reader and read the graph
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new FileInputStream(mapName)));
		Graph graph = reader.read();
		
		//Origin or Destination is outside of map, or mode or algo is not defined
		if ((mode != 0 && mode != 1) || originID < 0 || originID >= graph.size() || destinationID < 0 || destinationID >= graph.size() || (algo != 0 && algo != 1)) {
			System.out.println("ERROR: Invalid parameters");
		} else {
			Node origin = graph.get(originID);
			Node destination = graph.get(destinationID);
			ArcInspector inspector = ArcInspectorFactory.getAllFilters().get(0);
			
			/*if (mode == 0) { // We're looking at time (mode = 0)
				System.out.println("Time");
				inspector = ArcInspectorFactory.getAllFilters().get(2);	
			} else { // We're looking at distance (mode = 1)
				System.out.println("Distance");
				inspector = ArcInspectorFactory.getAllFilters().get(0);	
			}*/
			
			System.out.println("Origin : " + origin);
			System.out.println("Destination : " + destination);
			
			if (origin == destination) {
				System.out.println("Origin and destination are the same, Solution Cost: 0");
			} else {
				ShortestPathData data = new ShortestPathData(graph, origin, destination, inspector);
				// We're comparing our solution to the results of BellmanFordAlgorithm 
				ShortestPathSolution solution;
				ShortestPathSolution expected;
				if (algo == 0) { //We're looking at Djikstra
					BellmanFordAlgorithm E = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm S = new DijkstraAlgorithm(data);
					solution = S.run();
					expected = E.run();
				} else { //We're looking at AStar
					BellmanFordAlgorithm E = new BellmanFordAlgorithm(data);
					AStarAlgorithm S = new AStarAlgorithm(data);
					solution = S.run();
					expected = E.run();
				}
				
				if (solution.getPath() == null) {
					assertEquals(expected.getPath(), solution.getPath());
					System.out.println("No solution");
					//System.out.println("(infinity) ");
				} else { //A shorter path is found
					double solutionCost;
					double expectedCost;
					if (mode == 0) { // We're looking at time
						solutionCost = solution.getPath().getMinimumTravelTime();
						expectedCost = expected.getPath().getMinimumTravelTime();
					} else {
						solutionCost = solution.getPath().getLength();
						expectedCost = expected.getPath().getLength();
					}
					assertEquals(expectedCost, solutionCost, 0.001);
					System.out.println("Solution cost: " + solutionCost);
				}	
			}	
		}
		System.out.println("");
		System.out.println("");
	}
}
