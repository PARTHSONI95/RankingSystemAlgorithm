package com.neu.psa.service;
/**
 * 
 * @version 1.0
 * @author parth
 *
 */
public class BinomialProbability {
	
	public BinomialProbability() {
	}

	/**
	 * <h1>Binomial Probability calculator for discrete values</h1>
	 * 
	 * @param total
	 * @param success
	 * @param successProbInEachTrial
	 * @return
	 */
	public static float calculateBinomialProbability(int total, int success, float successProbInEachTrial) {
		
		return calculateCombination(total,success) * (float) Math.pow(successProbInEachTrial,success) * (float) Math.pow(1-successProbInEachTrial,total-success);		
	}
	/**
	 * <h1>Combination calculator</h1>
	 * <p>calculate the total outcomes of an event where order of the outcomes does not matter</p>
	 * <p>formula nCr = n! / r! * (n - r)!</p>
	 * 
	 */
	public static int calculateCombination(int n,int r){
			
			if (r > n / 2) 
	            r = n - r; 
			int answer = 1; 
	        for (int i = 1; i <= r; i++) { 
	            answer *= (n - r + i); 
	            answer /= i; 
	        }
	        return answer; 
		
	}
	
	
	/**
	 * Driver stub to test binomial probability
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		
		int total = 5; 
        int successWins = 4; 
        float successProbInEachTrial = 0.666f; 
        float probability = calculateBinomialProbability(total, successWins, successProbInEachTrial); 
		
        System.out.println(probability);
	}
}
