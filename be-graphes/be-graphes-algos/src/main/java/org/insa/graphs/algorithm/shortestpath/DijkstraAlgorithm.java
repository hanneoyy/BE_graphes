package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        boolean fin = false;
		Graph graph = data.getGraph();
		int tailleGraphe = graph.size();

		// Table of Labels, the labels are placed according to their ID 
		Label tabLabels[] = new Label [tailleGraphe];

		// Heap of labels 
		BinaryHeap<Label> heap = new BinaryHeap<Label>();

		// Table of predecessors 
		Arc[] predecessorArcs = new Arc[tailleGraphe];


		// Adding the first node 
		Label deb = newLabel(data.getOrigin(), data);
		tabLabels[deb.getNode().getId()] = deb;
		heap.insert(deb);
		deb.setInPile();
		deb.setCost(0);

		// Notify the observers of the starting node 
		notifyOriginProcessed(data.getOrigin());

		// While there are still non-marked nodes and destination is not found
		while(!heap.isEmpty() && !fin){      	

			Label current = heap.deleteMin();
			
			// Notifying the observers that the node is marked 
			notifyNodeMarked(current.getNode());
			current.setMark();
			
			// When we have reached the destination, we stop 
			if (current.getNode() == data.getDestination()) {
				fin = true;
			}
			
			// Go through the successors of the current node 
			Iterator<Arc> arc = current.getNode().iterator();
			while (arc.hasNext()) {
				Arc arcIter = arc.next();

				// Verification if the arc is allowed or not
				if (!data.isAllowed(arcIter)) {
					continue;
				}

				Node successeur = arcIter.getDestination();

				// Get the label corresponding to the current successor node 
				Label successorLabel = tabLabels[successeur.getId()];

				// If the label doesn't exist, we create it 
				if (successorLabel == null) {
					
					// We notify the observers that we've reached the node for the first time 
					notifyNodeReached(arcIter.getDestination());
					successorLabel = newLabel(successeur, data);
					tabLabels[successorLabel.getNode().getId()] = successorLabel;
				}

				// If the successor is not yet marked
				if (!successorLabel.getMark()) {
					
					//If we reach a better cost, we update it
					if(0>(current.getCost()+data.getCost(arcIter)-successorLabel.getCost())
						|| (successorLabel.getCost()==Float.POSITIVE_INFINITY)){
						
						successorLabel.setCost(current.getCost()+(float)data.getCost(arcIter));
						successorLabel.setFather(current.getNode());
						
						// If the label is already in the heap, we update its position
						if(successorLabel.getInPile()) {
							heap.remove(successorLabel);
						} else { // If not we add it to the heap
							successorLabel.setInPile();
						}
						heap.insert(successorLabel);
						predecessorArcs[arcIter.getDestination().getId()] = arcIter;
					}
				}
			}
		}

		// Destination has no predecessor, the solution is infeasible
		if (predecessorArcs[data.getDestination().getId()] == null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {

			// The destination has been found, notify the observers
			notifyDestinationReached(data.getDestination());

			// Create the path from the array of predecessors...
			ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = predecessorArcs[data.getDestination().getId()];

			while (arc != null) {
				arcs.add(arc);
				arc = predecessorArcs[arc.getOrigin().getId()];
			}

			// Reverse the path
			Collections.reverse(arcs);

			// Create the final solution
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
			
		}

        return solution;
    }
    
    //Creates and returns the Label corresponding to the Node
	protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}
	
}
