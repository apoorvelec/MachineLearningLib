package edu.columbia.cs.Graph;

public class Node {
	
	private int ID;
	
	private String LABEL;
	
	private Colour COLOUR;
	
	public enum Colour{
		WHITE, BLACK, GREY;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Default constructor
	 */
	public Node(){
		this.ID = 1;
		this.LABEL = "Default";
		this.COLOUR = Colour.WHITE;
	}
	
	/**
	 * Better constructor
	 * @param id
	 * @param label
	 * @param colour
	 */
	public Node(int id, String label, Colour colour){
		this.ID = id;
		this.LABEL = label;
		this.COLOUR = colour;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public String getLabel(){
		return this.LABEL;
	}
	
	public Colour getColour(){
		return this.COLOUR;
	}
	
	public void setID(int id){
		this.ID = id;
	}
	
	public void setLabel(String label){
		this.LABEL = label;
	}
	
	public void setColour(Colour colour){
		this.COLOUR = colour;
	}

}
