package edu.columbia.cs.Clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * K-Means clustering is used to find patterns in data.
 * It is a form of unsupervised learning which strives to find
 * good clusters using an iterative process.
 * @author Apoorv
 *
 */

public class KMeans {
	//TODO: Change termination criteria for the while loop in cluster method ...
	private class KMeansObject{
		double[] position;
		int clusterindex;
		
		public KMeansObject(double[] position, int index){
			this.position = position;
			this.clusterindex = index;
		}
		
		public double getDistanceFrom(double[] position){
			double distance = 0;
			for(int i=0;i<position.length;i++){
				distance += Math.pow(this.position[i]-position[i], 2);
			}
			distance = Math.pow(distance, 0.5);
			return distance;
		}
		
		public void assignClusterIndex(ArrayList<double[]> ClusterCenters){
			ArrayList<Double> distances = new ArrayList<Double>();
			for(int i=0;i<ClusterCenters.size();i++){
				double distance = getDistanceFrom(ClusterCenters.get(i));
				distances.add(distance);
			}
			// Find the smallest distance's index
			this.clusterindex = distances.indexOf(Collections.min(distances));
			//System.out.println(this.clusterindex);
		}
		
		public double[] getPosition(){
			return this.position;
		}
		
		public void setPosition(double[] position){
			this.position = position;
		}
		public int getClusterIndex(){
			return this.clusterindex;
		}
		public void setClusterIndex(int ClusterIndex){
			this.clusterindex = ClusterIndex;
		}
	}

	private double[][] Data; // Should be MxN, where M= Number of Data Points
						  // N= Number of Dimensions
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] data = {{0.78,0},{01.25,0},{1.7,0},{2.47,0},{3.50,0},{3.9,0},{4,0},{4.78,0},{4.99,0},{5.2,0}};
		KMeans kmeans = new KMeans(data);
		Object[] res = kmeans.cluster(4);
		System.out.println(Arrays.toString((int[]) res[0]));
		System.out.println((((ArrayList<double[]>)res[1]).toString()));
		System.out.println(((ArrayList<Double>)res[2]).toString());

	}

	public KMeans(double[][] Data){
		this.Data = Data;
	}
	
	// K= Number of Clusters
	public Object[] cluster(int K){
		
		int M = this.Data.length;
		int N = this.Data[0].length;
		
		ArrayList<KMeansObject> DataObjects = new ArrayList<KMeansObject>();
		for(int i=0;i<this.Data.length;i++){
			KMeansObject kmeansobject = new KMeansObject(Data[i],-1);
			DataObjects.add(kmeansobject);
		}
		
		// Choose K random centers
		ArrayList<Integer> CenterIndexes = getRandomCenters(K);
		ArrayList<double[]> Centers = new ArrayList<double[]>();
		for(int i=0;i<K;i++){
			Centers.add(this.Data[CenterIndexes.get(i)]);
		}
		int iterations = 0;
		while(iterations<1000){
			// assign cluster indexes to each datapoint
			for(int i=0;i<M;i++){
				DataObjects.get(i).assignClusterIndex(Centers);
			}
			// compute centers again
			Centers = computeCenters(DataObjects,K);
			iterations++;
		}
		int[] FinalIndices = new int[M];
		for(int i=0;i<M;i++){
			FinalIndices[i] = DataObjects.get(i).getClusterIndex();
		}
		ArrayList<Double> ClusterVariances = computeVariances(DataObjects,Centers);
		Object[] Result = new Object[3];
		Result[0] = FinalIndices; // indices
		Result[1] = Centers; // Centroids or centers
		Result[2] = ClusterVariances;
		return Result;
	}
	
	// Check this
	private ArrayList<double[]> computeCenters(ArrayList<KMeansObject> dataObjects, int K) {
		// TODO Auto-generated method stub
		
		int[] Count = new int[K];
		double[][] zeroVector = new double[K][this.Data[0].length];
		for(int i=0;i<zeroVector.length;i++){
			for(int j=0;j<zeroVector[0].length;j++){
				zeroVector[i][j] = 0;
			}
		}
	
		for(int i=0;i<dataObjects.size();i++){
			//System.out.println("c:"+dataObjects.get(i).getClusterIndex());
			addVectors(zeroVector[dataObjects.get(i).getClusterIndex()], dataObjects.get(i).getPosition());
			Count[dataObjects.get(i).getClusterIndex()] += 1;
		}
		//System.out.println("Count:"+Arrays.toString(Count));
		
		ArrayList<double[]> Centers = new ArrayList<double[]>();
		for(int i=0;i<zeroVector.length;i++){
			Centers.add(zeroVector[i]);
		}
		ArrayList<double[]> Result = new ArrayList<double[]>();
		for(int i=0;i<K;i++){
			Result.add(divideVectorByInt(Centers.get(i),Count[i]));
			//System.out.println(Arrays.toString(Result.get(i)));
		}
		return Result;
	}
	
	private ArrayList<Double> computeVariances(ArrayList<KMeansObject> dataObjects, ArrayList<double[]> Centers){
		ArrayList<Double> Variances = new ArrayList<Double>();
		for(int i=0;i<Centers.size();i++){
			double SumOfSquaredDistances = 0;
			double NumOfDataInst = 0.0;
			for(int j=0;j<dataObjects.size();j++){
				if(dataObjects.get(j).getClusterIndex() == i){
					double dist = dataObjects.get(j).getDistanceFrom(Centers.get(i));
					SumOfSquaredDistances+=dist;
					NumOfDataInst++;
				}
			}
			Variances.add(SumOfSquaredDistances/NumOfDataInst);
		}
		
		return Variances;
	}
	
	// B is added to A
	private void addVectors(double[] A, double[] B){
		if(A.length!=B.length){
			System.out.println("Vector sizes must be same necessarily!!!");
		}else{
			for(int i=0;i<A.length;i++){
				A[i] = A[i] + B[i];
			}
		}
	}
	
	private double[] divideVectorByInt(double[] A, int B){
		for(int i=0;i<A.length;i++){
			A[i] = A[i]/B;
		}
		return A;
	}

	// K = no of centers
	private ArrayList<Integer> getRandomCenters(int K){
		int M = this.Data.length;
		ArrayList<Integer> Centers = new ArrayList<Integer>();
		
		Random CenterSelector = new Random();
		
		while(Centers.size()<K){
			int center = CenterSelector.nextInt(M);
			if(!Centers.contains(center)){
				Centers.add(center);
			}
			
		}
		return Centers;
	}
}
