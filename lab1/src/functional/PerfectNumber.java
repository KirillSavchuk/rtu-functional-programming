package functional;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		
		divisors.addAll(
			IntStream
				.rangeClosed(2, (int) Math.ceil(Math.sqrt(n))) 	// Apply Math logic described bellow
				.filter(i -> n % i == 0) 						// Only numbers who can be divided with 0 without remainder
				.flatMap(i -> IntStream.of(i, (int)(n/i))) 		// Add dividend and division result | 
				.boxed()										// Convert to Integer
				.collect(Collectors.toSet())					// Repacks Integer Stream to Integer Set
		);
		
		return divisors;
		/*  Math logic:
		 *  Iterate from 2 to the square root of the of the number {ceil(sqrt(n))} because of Math laws (optimization):
		 *  {n} / {smaller than sqrt(n)} = {divisors larger than n} 	|	144 /  4 = 36
		 *	{n} / sqrt(n) = {n}											|	144 / 12 = 12
		 *	{n} / {larger than sqrt(n)} = {smaller larger than n}		|	144 / 36 =  4
		 */
	}
	
	public static STATE process(Integer n) {
		Set<Integer> divisors = divisors(n);
		
		long sum = divisors
				.stream()					// Create Integer Stream
				.filter(i -> i != n) 		// Perfect number sum doesn't contains number itself
				.reduce(0, Integer::sum);	// Sum up all divisors
		
		return (sum < n) ? STATE.DEFICIENT : (sum == n) ? STATE.PERFECT : STATE.ABUNDANT;
	}

}
