package lab3;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;


class SolutionTest {

	@Test
	void test_9875_DigitSum() {
		assertEquals(29, Solution.digitSum(9875));
	}
	
	@Test
	void test_29_DigitSum() {
		assertEquals(11, Solution.digitSum(29));
	}
	
	@Test
	void test_11_DigitSum() {
		assertEquals(2, Solution.digitSum(11));
	}
	
	@Test
	void test_2_DigitSum() {
		assertEquals(2, Solution.digitSum(2));
	}
	
	@Test
	void test_9875_SuperDigit() {
		assertEquals(2, Solution.superDigit(9875));
	}
	
	@Test
	void test_5_SuperDigit() {
		assertEquals(5, Solution.superDigit(5));
	}

}
