package edu.columbia.cs.NeuralNets;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import Jama.Matrix;
import edu.columbia.cs.Clustering.KMeans;


// Almost done. Just change the precision in calculating phi function
// Also incorporate automatic choosing of Hidden Neurons
public class RBFN {
	
	private double[][] InputData;
	private double[][] TargetData;
	private ArrayList<Object> LearntParameters;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		MathContext mc = new MathContext(20);
		BigDecimal bd = new BigDecimal((double)2.0/100,mc);
		BigDecimal bd1 = new BigDecimal((double)2.0/100,mc);
		BigDecimal bd2 = bd1.multiply(bd);
		//bd.setScale(100, RoundingMode.HALF_UP);
		System.out.println(Math.pow(Math.E, 1/0.00001));
		//System.out.println(mc.getPrecision());
		*/
		double[][] data = {{0.0,0.0},{0.0,1.0},{1.0,0.0},{1.0,1.0}};
		double[][] XOR_INPUT1 = { { 0.0, 0.0 }, { 1.0, 0.0 },
				{ 0.0, 1.0 }, { 1.0, 1.0 } };
	    double XOR_IDEAL1[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };
		RBFN rbfn = new RBFN(XOR_INPUT1, XOR_IDEAL1);
		rbfn.learn(5);
		double[][] arr = {{0.0,1.0},{0.1,1.1}};
		rbfn.estimate(arr);
	}
	
	public RBFN(double[][] InputData, double[][] TargetData){
		this.InputData = InputData;
		this.TargetData = TargetData;
		this.LearntParameters = new ArrayList<Object>();
	}
	
	@SuppressWarnings("unchecked")
	public void learn(int HiddenNeurons){
		// The intelligence goes here
		KMeans kmeans = new KMeans(this.InputData);
		Object[] MuSigmaData = kmeans.cluster(HiddenNeurons); // Here are the centers and variances
		// First layer Learning done !!! ...
		
		// For the second layer, construct the phi matrix.
		double[][] PHI = new double[this.InputData.length][HiddenNeurons];
		for(int i=0;i<PHI.length;i++){
			for(int j=0;j<PHI[i].length;j++){
				PHI[i][j] = phi(((ArrayList<double[]>)MuSigmaData[1]).get(j),((ArrayList<Double>)MuSigmaData[2]).get(j),this.InputData[i]);
			}
		}
		for(int i=0;i<PHI.length;i++){
			for(int j=0;j<PHI[i].length;j++){
				//System.out.print(PHI[i][j]);
			}
			//System.out.println();
		}
		Matrix PHImatrix = new Matrix(PHI);
		Matrix PHIPseudoInverse = PHImatrix.inverse();
		Matrix TargetMatrix = new Matrix(this.TargetData);
		Matrix WeightMatrixTranspose = PHIPseudoInverse.times(TargetMatrix);
		Matrix WeightMatrix = WeightMatrixTranspose.transpose();
		this.LearntParameters.add(MuSigmaData[1]); // myu data
		this.LearntParameters.add(MuSigmaData[2]); // sigmaSquare data 
		this.LearntParameters.add(WeightMatrix); // Learned weights
	}
	// Calculate this with precision
	private double phi(double[] myu, Double sigmaSquare, double[] input) {
		// TODO Auto-generated method stub
		double normSquare = 0.0;
		for(int i=0;i<myu.length;i++){
			//System.out.println("mu-inp"+(myu[i]-input[i]));
			normSquare+= Math.pow((myu[i]-input[i]), 2);
			//System.out.println("normSquare"+normSquare);
		}
		System.out.println("normSquare"+normSquare);
		System.out.println("sigmaSquare"+sigmaSquare);
		//System.out.println("phivalue"+Math.pow(Math.E, (-normSquare)/(2*(sigmaSquare))));
		//MathContext precision = new MathContext(20);
		//BigDecimal HighPrecisionValue = new BigDecimal(Math.pow(Math.E, (-normSquare)/(2*(sigmaSquare))),precision);
		if(normSquare == 0.0){
			return 1.0;
		}
		return Math.pow(Math.E, (-normSquare)/(2*(sigmaSquare)));
	}

	// Each row signifies an input i.e
	// for an MxN matrix as input
	// we have given M inputs of N dimension each
	@SuppressWarnings("unchecked")
	public double[][] estimate(double[][] input){
		// Used for estimation
		int HiddenNeurons = ( (ArrayList<double[]>) this.LearntParameters.get(0)).size();
		double[][] PHI = new double[input.length][HiddenNeurons];
		for(int i=0;i<PHI.length;i++){
			for(int j=0;j<PHI[i].length;j++){
				PHI[i][j] = phi(((ArrayList<double[]>)this.LearntParameters.get(0)).get(j),((ArrayList<Double>)this.LearntParameters.get(1)).get(j),input[i]);
			}
		}
		Matrix PHIMatrix = new Matrix(PHI);
		Matrix WeightMatrix = (Matrix) this.LearntParameters.get(2);
		Matrix EstimateMatrix = PHIMatrix.times(WeightMatrix.transpose());
		double[][] Estimate = EstimateMatrix.getArray();
		
		for(int i=0;i<Estimate.length;i++){
			for(int j=0;j<Estimate[i].length;j++){
				System.out.print(Estimate[i][j]+" ");
			}
			System.out.println();
		}
		return Estimate;
	}

}
