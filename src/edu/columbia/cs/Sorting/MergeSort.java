package edu.columbia.cs.Sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Divide into two sub lists
 * Apply Merge Sort on each of them
 * Combine the sorted sublists
 * @author Apoorv
 *
 */
public class MergeSort {
	
	private List<Double> NumberList;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		a.add(0.78);
		a.add(0.67);
		a.add(0.53);
		a.add(0.29);
		a.add(0.25);
		a.add(0.55);
		a.add(0.35);
		a.add(0.35);
		MergeSort ms = new MergeSort(a);
		
		List<Double> c=ms.sort(a);
		for(int i=0;i< c.size();i++){
			System.out.println(c.get(i).toString());
		}
	}
	
	public MergeSort(List<Double> NumberList){
		this.NumberList = NumberList;
	}
	
	public List<Double> sort(List<Double> List){
		List<Double> c;
		if(List.size() == 1){
			return List;
		}else{
			ArrayList<List<Double>> dividedLists = divideList(List);
			//MergeSort mergeSort1 = new MergeSort(dividedLists.get(0));
			List<Double> a=sort(dividedLists.get(0));
			//MergeSort mergeSort2 = new MergeSort(dividedLists.get(1));
			List<Double> b=sort(dividedLists.get(1));
		
		c=mergeLists(a,b);
		}
		return c;
		
	}
	
	// Done
	public List<Double> mergeLists(List<Double> List1, List<Double> List2){
		
		/*
		double SentinelValue = Math.pow(Math.E, 100000000);
		List1.add(SentinelValue);
		List2.add(SentinelValue);
		ArrayList<Double> Result = new ArrayList<Double>();
		int i=0;
		int j=0;
		while(i<(List1.size()-1) || j<(List2.size()-1)){
			if(List1.get(i)<List2.get(j)){
				Result.add(List1.get(i));
				i++;
			}else{
				Result.add(List2.get(j));
				j++;
			}
		}
		System.out.println("Merged");
		for(int k=0;k< Result.size();k++){
			System.out.println(Result.get(k).toString());
		}
		System.out.println("--------------------------------");
		return Result;
		*/
		//List1.addAll(List2);
		//InsertionSort is = new InsertionSort(List1);
		//List<Double> Result = is.sort();
		return List1;
	}
	
	// Done
	public ArrayList<List<Double>> divideList(List<Double> List){
		System.out.println("Divided");
		int N = List.size();
		int N1 = (int) Math.floor((N/2.0)); // Length of 1st divided list
		int N2 = N-N1;// Length of 2nd divided list
		ArrayList<List<Double>> Result = new ArrayList<List<Double>>();
		Result.add(List.subList(0, N1));
		Result.add(List.subList(N1, N));
		for(int i=0;i< Result.size();i++){
			System.out.println(Result.get(i).toString());
		}
		System.out.println("--------------------------------");
		return Result;
	}
	
	public List<Double> getList(){
		return this.NumberList;
	}

}
