package lab3extra;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;


class SolutionTest {

	@Test
	void test_13_2_powerSum() {
		assertEquals(1, Solution.powerSum(13, 2));
	}	
	
	@Test
	void test_100_2_powerSum() {
		assertEquals(3, Solution.powerSum(100, 2));
	}	

}
