package lab3;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number:  ");
        Integer num = scanner.nextInt();
        scanner.close();
        
        System.out.println(superDigit(num));  
	}
	
	static long superDigit(long x) {
		return (x / 10 == 0) ? x : superDigit(digitSum(x));
	}
	
	static long digitSum(long x) {
		return (x == 0) ? 0 : x % 10 + digitSum(x / 10);
	}

}
