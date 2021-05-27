package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;

public class DijkstraAlgorithmTest {
	
	private static Graph roadedMap, nonRoadedMap;
	
	// List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    //@SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

    // Some paths...
    private static Path emptyPath, singleNodePath, shortPath, longPath, loopPath, longLoopPath, invalidPath;

    public static void initAll() {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[5];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);

        roadedMap = new Graph("ID", "", Arrays.asList(nodes), null);

        emptyPath = new Path(nonRoadedMap, new ArrayList<Arc>());
        singleNodePath = new Path(roadedMap, nodes[1]);
        shortPath = new Path(roadedMap, Arrays.asList(new Arc[] { a2b, b2c, c2d_1 }));
        longPath = new Path(roadedMap, Arrays.asList(new Arc[] { a2b, b2c, c2d_1, d2e }));
        loopPath = new Path(roadedMap, Arrays.asList(new Arc[] { a2b, b2c, c2d_1, d2a }));
        longLoopPath = new Path(roadedMap,
                Arrays.asList(new Arc[] { a2b, b2c, c2d_1, d2a, a2c, c2d_3, d2a, a2b, b2c }));
        invalidPath = new Path(roadedMap, Arrays.asList(new Arc[] { a2b, c2d_1, d2e }));
    }
    
    
}

