package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {

	protected float cost;
	private boolean marked; // true if the node is marked
	private Node father;
	private Node node;
	private boolean inPile; // true if the node is in the pile
		
	public Label(Node noeud){
		this.node = noeud;
		this.marked = false;
		this.cost = Float.POSITIVE_INFINITY;
		this.father = null; 
		this.inPile = false;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public float getCost() {
		return this.cost;
	}
	
	public float getTotalCost() {
		return this.cost;
	}
	
	// Returns true if the node has been marked
	public boolean getMark() {
		return this.marked;
	}
	
	public Node getFather() {
		return this.father;
	}
	
	// Returns true if the node has been put in the pile
	public boolean getInPile() {
		return this.inPile;
	}	
	
	public void setMark() {
		this.marked = true;
	}
	
	public void setCost(float cout) {
		this.cost = cout;
	}
	
	public void setFather(Node father) {
		this.father = father;
	}
	
	public void setInPile() {
		this.inPile = true;
	}
	
	// Compare the Labels according to their cost
	public int compareTo(Label autre) {
		int resultat;
		if (this.getTotalCost() < autre.getTotalCost()) {
			resultat = -1;
		}
		else if (this.getTotalCost() == autre.getTotalCost()) {
			resultat = 0;
		}
		else {
			resultat = 1;
		}
		return resultat;
	}
}
