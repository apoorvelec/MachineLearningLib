package edu.columbia.cs.Sorting;

import java.util.ArrayList;
import java.util.List;

public class HeapSort {
	
	private List<Double> NumberList;
	private int HeapLength;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Double> somelist = new ArrayList<Double>();
		somelist.add(4.5);
		somelist.add(1.5);
		somelist.add(0.5);
		somelist.add(14.5);
		somelist.add(0.5);
		somelist.add(3.5);
		somelist.add(3.5);
		HeapSort hsort = new HeapSort(somelist);
		hsort.sort();
		for(int i=0;i< hsort.getList().size();i++){
			System.out.println(hsort.getList().get(i).toString());
		}
	}
	
	public HeapSort(List<Double> NumberList){
		this.NumberList = NumberList;
		this.HeapLength = this.NumberList.size();
	}
	
	public List<Double> sort(){
		//setHeapLength(this.NumberList.size());
		//int lengthofHeap = getHeapLength();
		buildMaxHeap();
		//this.NumberList.
		for(int i=this.NumberList.size()-1;i>=0;i--){
			// swap ith and 1st element
			Double inter = this.NumberList.get(i);
			this.NumberList.set(i, this.NumberList.get(0));
			this.NumberList.set(0, inter);
			this.HeapLength--;
			maxHeapify(0);
		}
		return this.NumberList;
	}
	
	// Code the subroutines here ...
	
	private void buildMaxHeap(){
		int HeapSize = this.NumberList.size();
		for(int i=giveParent(HeapSize-1);i>=0;i--){
			maxHeapify(i);
		}
	}
	
	// Make the values starting from Node into a heap
	private void maxHeapify(int Node){
		int l= giveLeftNode(Node);
		int r= giveRightNode(Node);
		int largest;
		if(l<this.HeapLength && NumberList.get(l)>NumberList.get(Node)){
			largest = l;
		}else{
			largest=Node;
		}
		if(r<this.HeapLength && NumberList.get(r)>NumberList.get(largest)){
			largest = r;
		}
		if(largest!=Node){
			// then swap the elements at indexes largest and Node
			double inter = NumberList.get(Node);
			NumberList.set(Node, NumberList.get(largest));
			NumberList.set(largest, inter);
			maxHeapify(largest);
		}
	}
	
	private int giveParent(int Node){
		return (int) Math.floor((Node-1)/2.0);
	}
	
	private int giveLeftNode(int Node){
		return 2*Node+1;
	}
	
	private int giveRightNode(int Node){
		return (2*Node+2);
	}
	
	
	
	public List<Double> getList(){
		return this.NumberList;
	}
}
