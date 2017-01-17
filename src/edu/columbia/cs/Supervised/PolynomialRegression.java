package edu.columbia.cs.Supervised;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Jama.Matrix;


/**
 * Builds a polynomial regression model out of the given data.
 * Data should be given as input in the following way,
 * <p> Polynomial Regression falls under supervised learning
 * algorithms. Hence a double[][] <b>Input</b> and a double[][] 
 * <b>Output</b> matrix must be given by the user. The double[][] <b>Input</b>
 * has dimensions MxN, where M are the number of data-points and
 * N are the dimensions of each data point. double[][] <b>Output</b>
 * is an MxL dimension matrix, where L is the dimension of the labels.</p>
 * @author Apoorv
 *
 */
public class PolynomialRegression {

	/**
	 * A variable to store the input data. 
	 */
	private Matrix InputData;
	
	/**
	 * A variable to store desired output data.
	 */
	private Matrix OutputData;
	
	/**
	 * A variable to store model parameters
	 */
	private Matrix Theta;
	
	private Matrix ExpandedInputData;
	
	private ArrayList<String> ExpansionOrder;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double[][] input = {{1},{2},{3},{4}};
		double[][] output = {{1},{4},{9},{16}};
		
		PolynomialRegression pr = new PolynomialRegression(input,output);
		pr.buildModel(3);
		double[][] input1 = {{1},{2},{3},{4},{5},{100}};
		pr.predict(input1);
		
		//System.out.println(lr.calculateError(input, output));
		
	}
	
	/**
	 * Constructor for the class.
	 * @param input An MxN dimensional matrix; M = Number of Data-points
	 * and N = dimensionality of input
	 * @param output An MxL dimension matrix; L = dimensionality of output
	 */
	public PolynomialRegression(double[][] input, double[][] output){
		//Append the vector of ones
		double[][] X = new double[input.length][input[0].length+1];
		for(int i=0;i<X.length;i++){
			for(int j=0;j<X[i].length;j++){
				if(j==0){
					X[i][j] = 1;
				}else{
					X[i][j] = input[i][j-1];
				}
			}
		}
		this.InputData = new Matrix(X);
		this.OutputData = new Matrix(output);
	}
	
	public void buildModel(int polynomialDegree){
		// create the expansion order
		this.ExpansionOrder = new ArrayList<String>(
				createAllMultinomialCoefficients(polynomialDegree, 
						this.InputData.getColumnDimension()-1));
		// Expand the InputData Matrix and store in ExpandedInputData
		double[][] expandedMat = 
				new double[this.InputData.getRowDimension()][this.InputData.getColumnDimension()+this.ExpansionOrder.size()];
		
		for(int i=0;i<expandedMat.length;i++){
			for(int j=0;j<expandedMat[i].length;j++){
				if(j<this.InputData.getColumnDimension()){
					expandedMat[i][j] = this.InputData.get(i, j);
				}else{
					String str = this.ExpansionOrder.get(j-this.InputData.getColumnDimension());
					
					double expandedValue = 1; //init
					for(int k=0;k<str.length();k++){
						expandedValue = expandedValue*
								Math.pow(this.InputData.get(i, k+1), 
										Character.getNumericValue(str.charAt(k)));
					}
					expandedMat[i][j] = expandedValue;
				}
			}
		}
		this.ExpandedInputData = new Matrix(expandedMat);
		
		// Now with the expanded matrix, calculate the theta
		
		Matrix ExpandedInputDataTranspose = this.ExpandedInputData.transpose();
		this.Theta = ExpandedInputDataTranspose.times(this.ExpandedInputData).inverse().
				times(ExpandedInputDataTranspose).times(this.OutputData); // The popular linear regression equation
	}
	
	public Matrix predict(double[][] input){
		double[][] X = new double[input.length][input[0].length+1];
		for(int i=0;i<X.length;i++){
			for(int j=0;j<X[i].length;j++){
				if(j==0){
					X[i][j] = 1;
				}else{
					X[i][j] = input[i][j-1];
				}
			}
		}
		Matrix InputMatrix = new Matrix(X);
		
		// expand the InputMatrix
		double[][] expandedMat = 
				new double[InputMatrix.getRowDimension()][InputMatrix.getColumnDimension()+this.ExpansionOrder.size()];
		
		for(int i=0;i<expandedMat.length;i++){
			for(int j=0;j<expandedMat[i].length;j++){
				if(j<InputMatrix.getColumnDimension()){
					expandedMat[i][j] = InputMatrix.get(i, j);
				}else{
					String str = this.ExpansionOrder.get(j-InputMatrix.getColumnDimension());
					
					double expandedValue = 1; //init
					for(int k=0;k<str.length();k++){
						expandedValue = expandedValue*
								Math.pow(InputMatrix.get(i, k+1), 
										Character.getNumericValue(str.charAt(k)));
					}
					expandedMat[i][j] = expandedValue;
				}
			}
		}
		Matrix ExpandedInputMatrix = new Matrix(expandedMat);
		
		Matrix OutputMatrix = ExpandedInputMatrix.times(this.Theta);
		double[][] output = OutputMatrix.getArray();
		for(int i=0;i<output.length;i++){
			for(int j=0;j<output[i].length;j++){
				System.out.print(output[i][j]+" ");
			}
			System.out.println();
		}
		return OutputMatrix;
	}
	
	private static Set<String> createAllMultinomialCoefficients(int polynomialDegree
			, int numOfVars){
		Set<String> AllCoefficients = new HashSet<String>(); // Result var init
		
		ArrayList<String> partitions = returnAllUniquePartitions(polynomialDegree);
		for(int i=0;i<partitions.size();i++){
			String str = partitions.get(i); // store string
			int strlen = str.length();
			
			// Now process the string to have appropriate no. of zeros to the right
			String processedStr = str;
			for(int j=0;j<(numOfVars-strlen);j++){
				processedStr+="0";
			}
			//replace the ith string with the processed one
			partitions.remove(i);
			partitions.add(i, processedStr);
		}
		
		ArrayList<String> procPartitions = new ArrayList<String>();
		if(polynomialDegree>numOfVars){
			// then remove those partitions whose length is more than numOfVars
			for(int i=0;i<partitions.size();i++){
				if(partitions.get(i).length()>numOfVars){
					continue;
				}else{
					procPartitions.add(partitions.get(i));
				}
			}
		}
		
		partitions.removeAll(partitions);
		partitions.addAll(procPartitions);
		// For each partition, generate all permutations and return final result
		for(int i=0;i<partitions.size();i++){
			String str = partitions.get(i);
			AllCoefficients.addAll(generatePerm(str));
		}
		return AllCoefficients;
	}

	/**
	 * Generates all possible permutations of a given string
	 * @param input - any string like "5112" etc.
	 * @return a set containing all the string permutations
	 */
	private static Set<String> generatePerm(String input){
	    Set<String> set = new HashSet<String>();
	    if (input == "")
	        return set;

	    Character a = input.charAt(0);

	    if (input.length() > 1)
	    {
	        input = input.substring(1);

	        Set<String> permSet = generatePerm(input);

	        for (String x : permSet)
	        {
	            for (int i = 0; i <= x.length(); i++)
	            {
	                set.add(x.substring(0, i) + a + x.substring(i));
	            }
	        }
	    }
	    else
	    {
	        set.add(a + "");
	    }
	    return set;
	}
	
	private static String returnAPartition(int p[], int n)
	{
		String partition = "";
	    for (int i = 0; i < n; i++){
	    	partition += p[i];
	    }
	    return partition;
	}
	
	/**
	 * This method returns all partitions of a given natural number n
	 * @param n - The number whose partitions are to be made
	 * @return - An arraylist of strings containing the partitions
	 */
	private static ArrayList<String> returnAllUniquePartitions(int n){
		ArrayList<String> res = new ArrayList<String>();
	    int[] p = new int[n]; // An array to store a partition
	    int k = 0;  // Index of last element in a partition
	    p[k] = n;  // Initialize first partition as number itself
	 
	    // This loop first prints current partition, then generates next
	    // partition. The loop stops when the current partition has all 1s
	    while (true)
	    {
	        // print current partition
	        res.add(returnAPartition(p, k+1));
	 
	        // Generate next partition
	 
	        // Find the rightmost non-one value in p[]. Also, update the
	        // rem_val so that we know how much value can be accommodated
	        int rem_val = 0;
	        while (k >= 0 && p[k] == 1)
	        {
	            rem_val += p[k];
	            k--;
	        }
	 
	        // if k < 0, all the values are 1 so there are no more partitions
	        if (k < 0)  return res;
	 
	        // Decrease the p[k] found above and adjust the rem_val
	        p[k]--;
	        rem_val++;
	 
	 
	        // If rem_val is more, then the sorted order is violeted.  Divide
	        // rem_val in differnt values of size p[k] and copy these values at
	        // different positions after p[k]
	        while (rem_val > p[k])
	        {
	            p[k+1] = p[k];
	            rem_val = rem_val - p[k];
	            k++;
	        }
	 
	        // Copy rem_val to next position and increment position
	        p[k+1] = rem_val;
	        k++;
	    }
	}

}
