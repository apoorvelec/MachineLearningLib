package edu.columbia.cs.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import edu.columbia.cs.Graph.Node.Colour;

public class Graph {

	private boolean DIRECTED;
	
	private ArrayList<Node> NODELIST;
	
	private ArrayList<Edge> EDGELIST;
	
	private double[][] ADJMAT;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		double[][] adjmat = {{0,1},{1,0}};
		Graph graph = new Graph(adjmat, false);
		for(int i=0;i<2;i++){
			System.out.println(graph.NODELIST.get(i).getID());
		}
		for(int i=0;i<graph.EDGELIST.size();i++){
			System.out.println(graph.EDGELIST.get(i).getNodes().get(0).getID());
			System.out.println(graph.EDGELIST.get(i).getNodes().get(1).getID());
		}
		*/
		double[][] adjmat = {{0,1},{1,0}};
		Graph graph = new Graph(adjmat, false);
		Graph g = graph.kroneckerProduct(graph);
		printMatrix(g.getAdjacencyMatrix());
		printAdjacencyList(g.getAdjacencyListRepresentation());
	}
	
	public Graph(double[][] adjmat, boolean directed){
		this.ADJMAT = adjmat;
		this.NODELIST = getNodeList(this.ADJMAT);
		this.EDGELIST = getEdgeList(this.ADJMAT, directed);
	}
	
	private ArrayList<Node> getNodeList(double[][] adjmat){
		ArrayList<Node> NodeList = new ArrayList<Node>();
		// assuming adjmat is square
		int N = adjmat.length; // No. of rows
		for(int i=0;i<N;i++){
			Node node = new Node(i,Integer.toString(i),Colour.WHITE);
			NodeList.add(node);
		}
		return NodeList;
	}
	/**
	 * Constructs list of edges in graph. Note that it assumes that 
	 * the list of nodes is known and stored in NODELIST correctly.
	 * @param adjmat
	 * @param directed
	 * @return
	 */
	private ArrayList<Edge> getEdgeList(double[][] adjmat, boolean directed){
		ArrayList<Edge> EdgeList = new ArrayList<Edge>();
		if(directed){
			for(int i=0;i<adjmat.length;i++){
				for(int j=0;j<adjmat[i].length;j++){
					if(adjmat[i][j] != 0){
						// Construct an edge
						Edge edge = new Edge(this.NODELIST.get(i),
								this.NODELIST.get(j), true, adjmat[i][j]);
						EdgeList.add(edge);
					}
				}
			}
		}else{
			// traverse only the lower triangular region because 
			// the adjmat will be symmetric
			for(int i=0;i<adjmat.length;i++){
				for(int j=0;j<=i;j++){
					if(adjmat[i][j] != 0){
						// Construct an edge
						Edge edge = new Edge(this.NODELIST.get(i),
								this.NODELIST.get(j), false, adjmat[i][j]);
						EdgeList.add(edge);
					}
				}
			}
		}
		return EdgeList;
	}
	
	/**
	 * Computes a new graph which is the tensor product of the current
	 * Graph object and the argument.
	 * @param graph
	 * @return
	 */
	//TODO: Kronecker product successfully done. Check 
	// cases of direc/undirec input combinations
	public Graph kroneckerProduct(Graph graph){
		double[][] adjmat = graph.getAdjacencyMatrix();
		int M = adjmat.length;
		int N = adjmat[0].length;
		int P = this.ADJMAT.length;
		int Q = this.ADJMAT[0].length;
		double[][] AdjMatProd = new double[M*P][N*Q];
		int rowInd = 0;
		for(int i=0;i<M*P;i++){
			if((i)%M==0 && i!=0){
				rowInd++;
			}
			int colInd = 0;
			for(int j=0;j<N*Q;j++){
				if((j)%N==0 && j!=0){
					colInd++;
				}
				AdjMatProd[i][j] = ADJMAT[rowInd][colInd]*
						adjmat[i%M][j%N];
			}
		}
		// Here I am only considering product graph of undirected
		// graphs since I don't know what will be the case when
		// the graphs whose product is being taken are directed or
		// any other combination of directed/undirected.
		Graph ProductGraph = new Graph(AdjMatProd,false);
		return ProductGraph;
	}
	
	// Getter Methods
	
	public ArrayList<LinkedList<Node>> getAdjacencyListRepresentation(){
		ArrayList<LinkedList<Node>> Result 
			= new ArrayList<LinkedList<Node>>();
		for(int i=0;i<this.ADJMAT.length;i++){
			// For each ith node, create its LinkedList<Node>
			LinkedList<Node> linkedlist = new LinkedList<Node>();
			Node node = this.NODELIST.get(i);
			linkedlist.add(node);
			for(int j=0;j<this.ADJMAT[i].length;j++){
				if(this.ADJMAT[i][j] != 0){
					// then add node j to list
					linkedlist.add(this.NODELIST.get(j));
				}
			}
			Result.add(linkedlist);
		}
		return Result;
	}
	
	public double[][] getAdjacencyMatrix() {
		// TODO Auto-generated method stub
		return this.ADJMAT;
	}

	public void getShortestPath(){
		
	}
	
	public void createRandomGraph(int n){
		
	}
	
	// Utility
	
	public static void printAdjacencyList(
			ArrayList<LinkedList<Node>> adjlist){
		for(int i=0;i<adjlist.size();i++){
			printLinkedList(adjlist.get(i));
		}
	}
	
	private static void printLinkedList(LinkedList<Node> list) {
		// TODO Auto-generated method stub
		ListIterator<Node> iterator = list.listIterator();
		while(iterator.hasNext()){
			System.out.print(iterator.next().getID()+"-->");
		}
		System.out.print("END");
		System.out.println();
	}
	
	public static void printArray(double[] array) {
		// TODO Auto-generated method stub
		for(int i=0;i<array.length;i++){
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}

	public static void printMatrix(double[][] array) {
		// TODO Auto-generated method stub
		for(int i=0;i<array.length;i++){
			printArray(array[i]);
		}
		System.out.println();
	}
}
