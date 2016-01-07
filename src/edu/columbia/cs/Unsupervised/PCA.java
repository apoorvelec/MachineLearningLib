package edu.columbia.cs.Unsupervised;

import Jama.Matrix;

/**
 * PCA means Principal Component Analysis. It is helpful in reducing the 
 * dimension of data.
 * TODO: Code not working as expected ... Improve it.
 * @author Apoorv
 *
 */
public class PCA {
	/**
	 * MxN matrix => M observations of N dimensional data
	 */
	private double[][] Data; 

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] data = {{1,2,0},{7,7,0},{1,9,0}};
		PCA pca = new PCA(data);
		double[][] reducedData = pca.getReducedDimensionData(3);
		for(int i=0;i<reducedData.length;i++){
			for(int j=0;j<reducedData[i].length;j++){
				System.out.print(reducedData[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public PCA(double[][] Data){
		this.Data = Data;
	}
	/**
	 * TODO: Preprocess the data so that each dimension has zero mean. Optionally
	 * each dimension should be scaled to some common metric.  
	 * This method reduces the dimension of the data to the specified value
	 * given in SpecifyDimension
	 * @param SpecifyDimension Specify to what dimension should the 
	 * data be reduced
	 * @return The reduced data in double[][] format
	 *   
	 */
	public double[][] getReducedDimensionData(int SpecifyDimension){
		preProcessData();
		Matrix CorrMat = computeCorrelationMatrix();
		Matrix EigenVectorsOfCorrMat = CorrMat.eig().getV();
		Matrix ReducedEigenVectorsOfCorrMat = EigenVectorsOfCorrMat.getMatrix(0, EigenVectorsOfCorrMat.getRowDimension()-1, 0, SpecifyDimension-1);
		Matrix DataMatrix = new Matrix(this.Data);
		Matrix ReducedDimensionData = (ReducedEigenVectorsOfCorrMat.transpose().times(DataMatrix.transpose())).transpose();
		return ReducedDimensionData.getArray();
	}

	private Matrix computeCorrelationMatrix(){
		Matrix JamaDataMatrix = new Matrix(this.Data);
		Matrix CorrelationMatrix = JamaDataMatrix.transpose().times(JamaDataMatrix).times(1.0/(this.Data[0].length));
		return CorrelationMatrix;
	}
	
	/**
	 * Calculates mean of each dimension and subtracts it 
	 * from every data point of that dimension
	 */
	private void preProcessData(){
		for(int i=0;i<this.Data[0].length;i++){
			// calculate mean
			double mean = 0;
			for(int j=0;j<this.Data.length;j++){
				mean+=this.Data[i][j];
			}
			mean = mean/this.Data.length;
			// update the dimension
			for(int j=0;j<this.Data.length;j++){
				this.Data[i][j] = this.Data[i][j] - mean;
			}
		}
	}
}
