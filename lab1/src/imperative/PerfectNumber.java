package imperative;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PerfectNumber {

	public static enum STATE {
		DEFICIENT,
		PERFECT,
		ABUNDANT,
		ERROR;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number:  ");
        Integer num = scanner.nextInt();
        scanner.close();
        
        System.out.println(process(num));        
	}
	
	public static Set<Integer> divisors(Integer n) {
		// Create Set (only unique values) with always divisors of {n}: 1 and n 
		Set<Integer> divisors = new HashSet<>();
		divisors.add(1);
		divisors.add(n);
		
		// Iterate from 2 to the square root of the of the number {ceil(sqrt(n))} because of Math laws (optimization):
		// n / {smaller than sqrt(n)} = {divisors larger than n} 	|	144 /  4 = 36
		// n / sqrt(n) = n											|	144 / 12 = 12
		// n / {larger than sqrt(n)} = {smaller larger than n}		|	144 / 36 =  4
		for (int i = 2; i <= Math.ceil(Math.sqrt(n)); i++) {
            if (n % i == 0) {
            	divisors.add(i);
            	divisors.add(n / i);
            }
        }
		
		return divisors;
	}
	
	public static STATE process(Integer n) {
		Set<Integer> divisors = divisors(n);
		
		long sum = 0;
		for (Integer divisor : divisors) {
			sum += (divisor != n) ? divisor : 0; // Perfect number sum doesn't contains number itself
		}

		if (sum < n) 
			return STATE.DEFICIENT;
		else if (sum == n) 
			return STATE.PERFECT;
		else if (sum > n)
			return STATE.ABUNDANT;
		
		return STATE.ERROR;
	}

}
