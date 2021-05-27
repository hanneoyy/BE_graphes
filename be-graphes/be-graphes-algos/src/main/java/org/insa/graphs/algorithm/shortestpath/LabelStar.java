package org.insa.graphs.algorithm.shortestpath;

//import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.AbstractInputData;

public class LabelStar extends Label implements Comparable<Label>{
	private float inf;

	public LabelStar(Node node, ShortestPathData data) {
		super(node);

		if (data.getMode() == AbstractInputData.Mode.LENGTH) { // shortest traveling distance
			// inf is the best-case-scenario distance, if you could go in a straight line between the two nodes
			this.inf = (float)Point.distance(node.getPoint(),data.getDestination().getPoint());
		} else { // quickest traveling time
			// inf is the best-case-scenario time to travel between the two nodes
			// ie the shortest possible distance between the two nodes when traveled by the maximum speed
			int speed = Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed());
			this.inf = (float)Point.distance(node.getPoint(),data.getDestination().getPoint())/(speed*1000.0f/3600.0f);
		}
	}

	@Override
	// Returns the cost from origin to the current node + the possible cost from the current node to the destination
	public float getTotalCost() {
		return this.inf + this.cost;
	}
}