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
		long largestPower = (long) Math.pow(largest, N);
		if (largestPower > X) 
			return 0;
		else if (largestPower == X) 
			return 1;
		else 
			return powerSumRecursion(X, N, largest+1) + powerSumRecursion(X-largestPower, N, largest+1);
	}

}
