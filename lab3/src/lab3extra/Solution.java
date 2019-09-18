package lab3extra;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter X:  ");
        Integer X = scanner.nextInt();
        System.out.print("Enter N:  ");
        Integer N = scanner.nextInt();
        scanner.close();
        
        System.out.println(powerSum(X, N));  
	}
	
	static long powerSum(long X, long N) {
		return powerSumRecursion(X, N, 1);
	}
	
	static long powerSumRecursion(long X, long N, long largest) {
		System.out.println(X + ", " + N + ", " + largest);
		long largestPower = (long) Math.pow(largest, N);
		if (largestPower > X) 
			return 0;
		else if (largestPower == X) 
			return 1;
		else 
			return powerSumRecursion(X, N, largest+1) + powerSumRecursion(X-largestPower, N, largest+1);
	}
	/*  IDEA of powerSumRecursion(10, 2, 1):
	 	---> [1 step] ---> 1^2=1:
	 	(10, 2, 1)
	 	
	 	---> [2 step] ---> 2^2=4:
	 	(10, 2, 1) + (10, 2, 2)
	 	(10, 2, 1) + ( 9, 2, 2) 
	 	
	 	---> [3 step] ---> 3^2=9:
	 	(10, 2, 1) + (10, 2, 2) + (10, 2, 3)
	 	(10, 2, 1) + (10, 2, 2) + ( 6, 2, 3)
	 	(10, 2, 1) + ( 9, 2, 2) + ( 9, 2, 3)
	 	(10, 2, 1) + ( 9, 2, 2) + ( 5, 2, 3) 
	 */

}
