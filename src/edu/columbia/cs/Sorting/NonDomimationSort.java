package edu.columbia.cs.Sorting;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Give any data matrix (2 D) and the sorting algorithm
 * will give sorted solutions in the form of an ArrayList<ArrayList<T[]>>
 * Each internal ith ArrayList<T[]> represents the ith front 
 * @author Apoorv
 *
 * @param <T>
 */
public class NonDomimationSort<T extends Number> {

	private T[][] Data;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Double[][] data = {{10.0,0.0},{1.0,1.0},{1.0,0.0},{0.0,1.0},{2.0,3.0},{3.0,2.0}};
		NonDomimationSort nds = new NonDomimationSort(data);
		ArrayList<ArrayList<Double[]>> sortedList = nds.sort();
		for(int i=0;i<sortedList.size();i++){
			for(int j=0;j<sortedList.get(i).size();j++){
				System.out.print(Arrays.asList(sortedList.get(i).get(j)).toString());
			}
			System.out.println();
		}
		
	}
	
	public NonDomimationSort(T[][] Data){
		this.Data = Data;
	}
	
	public ArrayList<ArrayList<T[]>> sort(){
		ArrayList<NDSObject> ProcessedList = processData();
		ArrayList<ArrayList<NDSObject>> VariousFronts = segregateFronts(ProcessedList);
		ArrayList<ArrayList<T[]>> SortedFronts = new ArrayList<ArrayList<T[]>>();
		
		for(int i=0;i<VariousFronts.size();i++){
			ArrayList<T[]> IFront = new ArrayList<T[]>();
			for(int j=0;j<VariousFronts.get(i).size();j++){
				IFront.add((T[]) VariousFronts.get(i).get(j).getDataValue());
			}
			SortedFronts.add(IFront);
		}
		
		return SortedFronts;
	}
	
	// Write this recursively ...
	public ArrayList<ArrayList<NDSObject>> segregateFronts(ArrayList<NDSObject> List){
		if(List.size() == 0){
			return new ArrayList<ArrayList<NDSObject>>();
		}else{
			ArrayList<NDSObject> Front = new ArrayList<NDSObject>();
			for(int i=0;i<List.size();i++){
				if(List.get(i).getDominatedBy() == 0){
					Front.add(List.get(i));
				}
			}
			for(int i=0;i<Front.size();i++){
				for(int j=0;j<Front.get(i).getDominates().size();j++){
					int index = List.indexOf(Front.get(i).getDominates().get(j));
					int newDomination = List.get(index).getDominatedBy();
					newDomination--;
					List.get(index).setDominatedBy(newDomination);
				}
			}
			List.removeAll(Front);
			
			ArrayList<ArrayList<NDSObject>> Result = segregateFronts(List);
			Result.add(0, Front);
			
			return Result;
		}
		
	}
	
	private class NDSObject<T extends Number> {
		int DominatedBy;
		ArrayList<NDSObject> Dominates;
		T[] DataValue;
		
		public NDSObject(int DominatedBy, ArrayList<NDSObject> Dominates,T[] DataValue){
			this.DominatedBy = DominatedBy;
			this.Dominates = Dominates;
			this.DataValue = DataValue;
		}
		
		public void setDominatedBy(int DominationNumber){
			this.DominatedBy = DominationNumber;
		}
		
		public void setDominates(ArrayList<NDSObject> DominatedSolutions){
			this.Dominates = DominatedSolutions;
		}
		
		public void setDataValue(T[] Value){
			this.DataValue = Value;
		}
		
		public int getDominatedBy(){
			return this.DominatedBy;
		}
		
		public ArrayList<NDSObject> getDominates(){
			return this.Dominates;
		}
		
		public T[] getDataValue(){
			return this.DataValue;
		}
		
		public boolean dominates(NDSObject AnotherObject){
			boolean Result = true;
			T[] DataValue1 = this.DataValue;
			T[] DataValue2 = (T[]) AnotherObject.getDataValue();
			for(int i=0;i<DataValue1.length;i++){
				if((double)DataValue1[i]==(double)DataValue2[i]){
					// do nothing
				}else if((double)DataValue1[i]<(double)DataValue2[i]){
					Result = Result && true;
				}else{
					Result = Result && false;
				}
			}
			return Result;
		}
	}
	
	/**
	 * Processes the data to fill in
	 * the DominatedBy and Dominates values
	 * for each solution. Returns the processed 
	 * list of solutions
	 * @return
	 */
	public ArrayList<NDSObject> processData(){
		ArrayList<NDSObject> ProcessedData = new ArrayList<NDSObject>();
		for(int i=0;i<this.Data.length;i++){
			NDSObject object = new NDSObject(0, new ArrayList<NDSObject>(), this.Data[i]);
			ProcessedData.add(object);
		}
		for(int i=0;i<ProcessedData.size();i++){
			int DominationCount = 0;
			for(int j=0;j<ProcessedData.size();j++){
				if(i!=j){
					if(ProcessedData.get(i).dominates(ProcessedData.get(j))){
						ProcessedData.get(i).getDominates().add(ProcessedData.get(j));
					}else if(ProcessedData.get(j).dominates(ProcessedData.get(i))){
						DominationCount++;
					}
				}
			}
			ProcessedData.get(i).setDominatedBy(DominationCount);
		}
		
		return ProcessedData;
	}

}
