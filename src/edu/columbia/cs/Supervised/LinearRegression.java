package edu.columbia.cs.Supervised;

import Jama.Matrix;

/**
 * Builds a linear regression model out of the given data.
 * Data should be given as input in the following way,
 * <p> Linear Regression is falls under supervised learning
 * algorithms. Hence a double[][] <b>Input</b> and a double[][] 
 * <b>Output</b> matrix must be given by the user. The double[][] <b>Input</b>
 * has dimensions MxN, where M are the number of data-points and
 * N are the dimensions of each data point. double[][] <b>Output</b>
 * is an MxL dimension matrix, where L is the dimension of the labels.</p>
 * @author Apoorv
 *
 */
public class LinearRegression {

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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] input = {{1},{2},{3},{4}};
		double[][] output = {{1},{2},{3},{4}};
		
		LinearRegression lr = new LinearRegression(input,output);
		lr.buildModel();
		double[][] input1 = {{1},{2},{3},{4},{5},{100}};
		lr.predict(input1);
		
		System.out.println(lr.calculateError(input, output));
	}
	
	/**
	 * Constructor for the class.
	 * @param input An MxN dimensional matrix; M = Number of Data-points
	 * and N = dimensionality of input
	 * @param output An MxL dimension matrix; L = dimensionality of output
	 */
	public LinearRegression(double[][] input, double[][] output){
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
		// After the ones have been appended, create the matrices
		this.InputData = new Matrix(X);
		this.OutputData = new Matrix(output);
	}
	
	/**
	 * This method learns the parameters of the regression line.
	 * The parameters are stored in <b>Theta</b>, which has dimensions
	 * (N+1)x1.
	 * @return The <b>Theta</b> as a Matrix
	 */
	public Matrix buildModel(){
		Matrix X = this.InputData;
		Matrix XT = this.InputData.transpose();
		this.Theta = XT.times(X).inverse().times(XT).times(OutputData);
		return this.Theta;
	}
	
	/**
	 * This method is used to predict an output value for any specified 
	 * input value.
	 * @param input A matrix containing inputs
	 * @return A Matrix containing the predictions
	 */
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
		Matrix OutputMatrix = InputMatrix.times(this.Theta);
		double[][] output = OutputMatrix.getArray();
		for(int i=0;i<output.length;i++){
			for(int j=0;j<output[i].length;j++){
				System.out.print(output[i][j]+" ");
			}
			System.out.println();
		}
		return OutputMatrix;
	}
	
	public double calculateError(double[][] testdataX, double[][] testdataY){
		//Append the vector of ones
		double[][] X = new double[testdataX.length][testdataX[0].length+1];
		for(int i=0;i<X.length;i++){
			for(int j=0;j<X[i].length;j++){
				if(j==0){
					X[i][j] = 1;
				}else{
					X[i][j] = testdataX[i][j-1];
				}
			}
		}
		
		Matrix Xtest = new Matrix(X);
		Matrix YFromLearntModel = Xtest.times(Theta);
		
		return YFromLearntModel.normF()/(2*testdataX.length);
	}

}
