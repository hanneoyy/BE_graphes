package org.insa.graphs.algorithm.shortestpath;

//import org.insa.graphs.algorithm.utils.*;
import org.insa.graphs.model.*;
//import org.insa.graphs.algorithm.shortestpath.*;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
	@Override
	protected Label newLabel(Node node, ShortestPathData data) {
		return new LabelStar(node, data);
	}

}
