# rtu-functional-programming

A perfect number is a positive integer that is equal to the sum of its proper positive divisors,

that is, the sum of its positive divisors excluding the number itself.

See http://en.wikipedia.org/wiki/Perfect_number

If the sum of proper positive divisors is less than the number then we'll call it "deficient".

If it is greater - the number will be called "abundant".

The task is to create a PerfectNumber.detect(int n) method, which for a given positive integer will return it's classification: deficient, perfect or abundant.

First, in order to understand task and debug the algorithm, create an imperative solution consisting of divisor list generator (public static Set<Integer> divisors(int n)) and number classifier (public static STATE process(int n)) using the tests to verify both.
 
