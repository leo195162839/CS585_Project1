package edu.csupomona.cs331.matrix;

import java.util.Random;
import java.util.Scanner;

/**
 * 
 *
 */
public class Project1 
{
	
    public static void main( String[] args )
    {
    	Scanner input = new Scanner(System.in);
        System.out.print( "Please enter the size of matrix: " );
        int n = input.nextInt();
        
        int[][] matrixA = matrixGenerator(n);
        int[][] matrixB = matrixGenerator(n);
        System.out.println("------------------------------------------------------------------------");
        
        //testing classic method time
		System.out.println("The result of the classic matrix multilication is: ");
        double startClassic = System.nanoTime();
        classicMatrixMultiplication(matrixA, matrixB);
        double endClassic = System.nanoTime();
        double durationClassic = (endClassic - startClassic) / 1000000000.0; 
        System.out.println("The classic matrix multiplication takes " + durationClassic + " seconds to complete.");
        System.out.println("------------------------------------------------------------------------");
        
		System.out.println("The result of the divide and conquer matrix multilication is: ");
        double startDivide = System.nanoTime();
        //divideAndConquerMatrixMultiplication(matrixA);
        double endDivide = System.nanoTime();
        double durationDivide = (endDivide - startDivide) / 1000000000.0; 
        System.out.println("The Divide and Conquer matrix multiplication takes " + durationDivide + " seconds to complete.");     
        System.out.println("------------------------------------------------------------------------");

        double startStrassen = System.nanoTime();
        //strassenMatrixMultiplication(matrix);
        double endStrassen = System.nanoTime();
        double durationStrassen = (endStrassen - startStrassen) / 1000000000.0; 
        System.out.println("The Strassen matrix multiplication takes " + durationStrassen + " seconds to complete.");   
        System.out.println("------------------------------------------------------------------------");

        input.close();
    }
	
	public static void printMatrix (int n, int[][] matrix) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j == n - 1) {
					System.out.printf("%-8d", matrix[i][j]);
					System.out.print("|");
					System.out.println("");
				} else {
					System.out.printf("%-8d",matrix[i][j]);
					System.out.print("|");
				}
			}
		}
	}
	
	public static int[][] divideMatrix () {
		
		
		
		return null;
	}
	
	public static int[][] matrixAddition (int[][] matrixA, int[][] matrixB) {
		
		int n = matrixA.length; //getting the size of the matrix
		int[][] result = new int[n][n]; //initialize the result matrix
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result[i][j] = matrixA[i][j] + matrixB[i][j];
			}	
		}
		
		System.out.println("This is addition.");
		printMatrix(n,result);
		
		return result;
	}
	
	public static int[][] matrixGenerator(int n) {
		
		int[][] result = new int[n][n]; //initialize the result matrix
		Random rand = new Random();
		
		System.out.println("The matrix is generated as below: ");
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result[i][j] = rand.nextInt(100) + 1;
			}
		}
		
		printMatrix(n,result);
		
		return result;
	}
	
	public static int[][] classicMatrixMultiplication(int[][] matrixA, int[][] matrixB) {
		
		int n = matrixA.length; //getting the size of the matrix
		int[][] result = new int[n][n]; //initialize the result matrix
				
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++){
					result[i][j] += matrixA[i][k]*matrixB[k][j];
				}
			}
		}
		
		printMatrix(n,result);
		
		return result;
		
	}

	public static int[][] divideAndConquerMatrixMultiplication(int[][] matrix) {
		
		int n = matrix.length; //getting the size of the matrix
		int[][] result = new int[n][n]; //initialize the result matrix
				
		if (matrix.length == 1) {
			result[0][0] = matrix[0][0]*matrix[0][0];
		} else {
			int[][] topLeft = new int[n/2][n/2];
			int[][] topRight = new int[n/2][n/2];
			int[][] bottomLeft= new int[n/2][n/2];
			int[][] bottomRight = new int[n/2][n/2];
			
			int[][] resultTopLeft = new int[n/2][n/2];
			int[][] resultTopRight = new int[n/2][n/2];
			int[][] resultBottomLeft= new int[n/2][n/2];
			int[][] resultBottomRight = new int[n/2][n/2];
			
			for (int i = 0; i < n/2; i++) {
				for (int j = 0; j < n/2; j++) {
					topLeft[i][j] = matrix[i][j];
					topRight[i][j] = matrix[i][j + (n/2)];
					bottomLeft[i][j] = matrix[i + (n/2)][j];
					bottomRight[i][j] = matrix[i + (n/2)][j + (n/2)];
				}
			}
			
			printMatrix(n/2,topLeft);
			printMatrix(n/2,topRight);
			printMatrix(n/2,bottomLeft);
			printMatrix(n/2,bottomRight);
			
			resultTopLeft = matrixAddition(divideAndConquerMatrixMultiplication(topLeft), divideAndConquerMatrixMultiplication(topLeft));
			resultTopRight = matrixAddition(divideAndConquerMatrixMultiplication(topRight), divideAndConquerMatrixMultiplication(topRight));
			resultBottomLeft = matrixAddition(divideAndConquerMatrixMultiplication(bottomLeft), divideAndConquerMatrixMultiplication(bottomLeft));
			resultBottomRight = matrixAddition(divideAndConquerMatrixMultiplication(bottomRight), divideAndConquerMatrixMultiplication(bottomRight));
			
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i < n/2 && j < n/2) {
						result[i][j] = resultTopLeft[i][j];
					} else if (i < n/2 && j >= n/2) {
						result[i][j] = resultTopRight[i][j - (n/2)];
					} else if (i >= n/2 && j< n/2) {
						result[i][j] = resultBottomLeft[i - (n/2)][j];
					} else {
						result[i][j] = resultBottomRight[i - (n/2)][j - (n/2)];
					}
				}
			}
			
			
		}
		
		printMatrix(n,result);
		
		return result;
	}
	
	public static void strassenMatrixMultiplication(int[][] matrix) {
		
	}

}
