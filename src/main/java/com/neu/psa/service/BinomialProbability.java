package com.neu.psa.service;

public class BinomialProbability {
	
	public BinomialProbability() {
	}

	public static float calculateProbability(int total, int success, float successProbInEachTrial) {
		
		return calculateCombination(total,success) * (float) Math.pow(successProbInEachTrial,success) * (float) Math.pow(1-successProbInEachTrial,total-success);
		
		
	}
	
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
	

	public static void main(String args[]) {
		
		int total = 5; 
        int successWins = 4; 
        float successProbInEachTrial = 0.333f; 
        float probability = calculateProbability(total, successWins, successProbInEachTrial); 
		
        System.out.println(probability);
	}
}
