package edu.columbia.cs.Graph;

import java.util.ArrayList;

public class Edge {
	
	/**
	 * True if Directed, else undirected
	 */
	private boolean ISDIR;

	/**
	 * One of the node forming the edge
	 */
	private Node NODE1;
	
	/**
	 * One of the node forming the edge
	 */
	private Node NODE2;
	
	/**
	 * Start node of directed edge
	 */
	private Node STARTNODE;
	
	/**
	 * End node of directed edge
	 */
	private Node ENDNODE;
	
	/**
	 * Weight of an edge
	 */
	private double WEIGHT;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Simple undirected edge with weight 1 
	 * @param n1
	 * @param n2
	 */
	public Edge(Node n1, Node n2){
		this.NODE1 = n1;
		this.NODE2 = n2;
		this.ISDIR = false;
		this.STARTNODE = null;
		this.ENDNODE = null;
		this.WEIGHT = 1.0;
	}
	/**
	 * An edge from n1 to n2 if directed=true, else a normal undirected
	 * edge. Weight will be assigned in both cases.
	 * @param n1
	 * @param n2
	 * @param directed
	 */
	public Edge(Node n1, Node n2, boolean directed, double weight){
		if(directed){
			this.NODE1 = n1;
			this.NODE2 = n2;
			this.ISDIR = true;
			this.STARTNODE = n1;
			this.ENDNODE = n2;
		}else{
			this.NODE1 = n1;
			this.NODE2 = n2;
			this.ISDIR = false;
			this.STARTNODE = null;
			this.ENDNODE = null;
		}
		this.WEIGHT = weight;
	}
	
	// Getter methods

	/**
	 * Returns true if graph is directed, else false
	 * @return
	 */
	public boolean isDirected(){
		return this.ISDIR;
	}
	
	/**
	 * Returns ArrayList of the two nodes forming the edge 
	 * @return
	 */
	public ArrayList<Node> getNodes(){
		ArrayList<Node> NodesInEdge = new ArrayList<Node>();
		NodesInEdge.add(NODE1);
		NodesInEdge.add(NODE2);
		return NodesInEdge;
	}
	
	/**
	 * Returns the start node
	 * @return
	 */
	public Node getStartNode(){
		return this.STARTNODE;
	}
	
	/**
	 * Returns the end node
	 * @return
	 */
	public Node getEndNode(){
		return this.ENDNODE;
	}
	
	public double getWeight(){
		return this.WEIGHT;
	}
	
	// Setter methods
	
	public void setDirection(Node from, Node to){
		// Check whether the from node and to node are the 
		// same nodes as were initialized for this object
		if((from.equals(NODE1) && to.equals(NODE2))||(from.equals(NODE2) && to.equals(NODE1))){
			// then set the direction
			if(this.ISDIR == false){
				this.ISDIR = true;
				this.STARTNODE = new Node();
				this.ENDNODE = new Node();
			}
			this.STARTNODE = from;
			this.ENDNODE = to;
		}
	}
	
	public void setWeight(double weight){
		this.WEIGHT = weight;
	}
	
}
