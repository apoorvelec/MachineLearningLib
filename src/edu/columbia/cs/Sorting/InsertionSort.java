package edu.columbia.cs.Sorting;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort{
	
	private List<Double> NumberList;

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
		InsertionSort isort = new InsertionSort(somelist);
		isort.sort();
		for(int i=0;i< isort.getList().size();i++){
			System.out.println(isort.getList().get(i).toString());
		}
		
	}
	
	public InsertionSort(List<Double> NumberList){
		this.NumberList = NumberList;
	}

	public List<Double> sort(){
		int ListLength = this.NumberList.size();
		for(int i=1;i<ListLength;i++){
			// Put this key in the right place in its list of preceding numbers
			int j=i;
			while(this.NumberList.get(j)<this.NumberList.get(j-1)){
				// swap
				Double a=this.NumberList.remove(j);
				Double b=this.NumberList.remove(j-1);
				this.NumberList.add(j-1, a);
				this.NumberList.add(j, b);
				if(j>1){
					j--;
				}
				
			}
		}
		return this.NumberList;
	}
	
	public List<Double> getList(){
		return this.NumberList;
	}
}
