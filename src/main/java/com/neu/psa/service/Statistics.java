package com.neu.psa.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neu.psa.pojo.Match;
import com.neu.psa.pojo.MatchData;
import com.neu.psa.pojo.Team;
import com.neu.psa.pojo.TeamDirectory;

public class Statistics {


	private static double totalMeanOfAllTeamForAverage;
	private static double totalStdDeviationForAverage;
	private static double totalMeanOfAllTeamForRPO;
	private static double totalStdDeviationForRPO;
	private static int totalCount;  
	
	
	public static void calculateMeanAndStdDeviation(List<MatchData> matchList,TeamDirectory teamDir,Set<String> teamNameStat) {


		for(String teamName : teamNameStat) {
			Team team = new Team();
			team.setTeamName(teamName);

			// Calculate Mean
			double teamAverageSum = 0.0;
			double teamRPOSum = 0.0;
			int countNoOfSeries = 0;
			int countNoOfMatches = 0;


			for(MatchData match : matchList) {
				if(match.getTeam().equals(teamName)) {
					teamAverageSum += match.getAverage();
					teamRPOSum += match.getRpo();

					// Match Count
					countNoOfMatches += match.getMatches();

					// Series Count
					countNoOfSeries++;

					totalMeanOfAllTeamForAverage += match.getAverage();
					totalCount++;

					totalMeanOfAllTeamForRPO += match.getRpo();

				}
			}
			team.setTeamMatchCount(countNoOfMatches);
			team.setTeamSeriesCount(countNoOfSeries);
			team.setMean_Of_Average(teamAverageSum/countNoOfSeries);
			team.setMean_Of_RPO(teamRPOSum/countNoOfSeries);

			// Calculate Standard deviation For Average and RPO 
			double stdDeviationForAverage = 0.0;
			double stdDeviationForRPO = 0.0;
			for(MatchData match : matchList) {
				if(match.getTeam().equals(teamName)) {
					stdDeviationForAverage += Math.pow(match.getAverage(), 2);
					stdDeviationForRPO += Math.pow(match.getRpo(), 2);

					totalStdDeviationForAverage += Math.pow(match.getAverage(), 2);
					totalStdDeviationForRPO += Math.pow(match.getRpo(),2);
				}
			}
			stdDeviationForAverage = Math.sqrt(stdDeviationForAverage/countNoOfSeries);
			team.setStdDeviation_ForAverage(stdDeviationForAverage);

			stdDeviationForRPO = Math.sqrt(stdDeviationForRPO/countNoOfSeries);
			team.setStdDeviation_ForRPO(stdDeviationForRPO);

			teamDir.addInTeamMap(team);
			//teamDir.addInTeamDirectory(team);

//			System.out.println(teamName);
//			System.out.println(teamAverageSum/countNoOfSeries + "----- Mean");
//			System.out.println("------ Std Deviation---" + stdDeviationForAverage);
//			System.out.println(Math.pow(stdDeviationForAverage, 2) + "-----Variance-----");
		}

		totalMeanOfAllTeamForAverage = totalMeanOfAllTeamForAverage/totalCount;
		totalMeanOfAllTeamForRPO = totalMeanOfAllTeamForRPO/totalCount;

		totalStdDeviationForAverage = Math.sqrt(totalStdDeviationForAverage/totalCount);
		totalStdDeviationForRPO = Math.sqrt(totalStdDeviationForRPO/totalCount);

	}

	public static void calculateTProbability(TeamDirectory teamDir) {

		// Calculating T-Distribution (as it requires total Mean and total Standard distribution)
		System.out.println("Check ---- Total Std Deviation  "+ totalStdDeviationForAverage);
		double tProbUsingAverage;
		double tProbUsingRPO;


		for(Map.Entry<String,Team> team : teamDir.getTeamMap().entrySet()) {

			tProbUsingAverage = WelchTTest.welch_ttest(team.getValue(),totalMeanOfAllTeamForAverage, Math.pow(totalStdDeviationForAverage,2),totalCount,"Average");
			System.out.println(tProbUsingAverage + "----for Team using Average ----" + team.getKey());

			tProbUsingRPO = WelchTTest.welch_ttest(team.getValue(), totalMeanOfAllTeamForRPO, Math.pow(totalStdDeviationForRPO,2), totalCount,"RPO");
			System.out.println(tProbUsingRPO + "----for Team Using RPO ----" + team.getKey());

			team.getValue().settProbByAverage(tProbUsingAverage);
			team.getValue().settProbByRPO(tProbUsingRPO);

		}

	}

	public static void calculateProbabilityBetweenAllTeams(TeamDirectory teamDir,List<MatchData> matchList,Set<String> teamNameStat,HashMap<String,Match> teamResult) {
		
		  for(int i=0; i < matchList.size(); i++) {
			  String key1 = matchList.get(i).getTeam() + "-"+ matchList.get(i).getOpponent();
			  String key2 = matchList.get(i).getOpponent() + "-" + matchList.get(i).getTeam(); 
			  Match match = new Match();
			  if(teamResult.containsKey(key1) || teamResult.containsKey(key2)){
				  match = teamResult.get(key1);
				  
			  }else {
				  calculateProbability(teamDir,matchList.get(i).getTeam(),matchList.get(i).getOpponent(),matchList,match);
				  //countWinsBetweenTeams(matchList.get(i).getTeam(),matchList.get(i).getOpponent(),matchList);
				  teamResult.put(key1,match);	  
			  }
			  
			  //System.out.println(key);
		  }
		  		  
	}
	
	public static void calculateProbability(TeamDirectory teamDir,String teamName, String opponentTeamName,List<MatchData> matchlist,Match match) {
		   
		   int[] stats = countWinsBetweenTeams(teamName,opponentTeamName,matchlist);
		   float probabilityTeam = BinomialProbability.calculateProbability(stats[2],stats[0],0.666f);
		   System.out.println("Binomial Prob of Team " + probabilityTeam);
		   
		   double tProbUsingAverageForTeam = teamDir.getTeamMap().get(teamName).gettProbByAverage();
		   double tPRrobUsingRPOForTeam = teamDir.getTeamMap().get(teamName).gettProbByRPO();
		   
		   double averageProbabilityOfTeam = (probabilityTeam + tProbUsingAverageForTeam + tPRrobUsingRPOForTeam)/3;
		   System.out.println("Overall Prob between " + teamName + " - " + opponentTeamName);
		   System.out.println("Team - " + teamName + ":" + averageProbabilityOfTeam);
		   
		   float probabilityOpponent = BinomialProbability.calculateProbability(stats[2],stats[1],0.666f);
		   System.out.println("Binomial Prob of Opponent wins " + probabilityOpponent);
		   
		   double tProbUsingAverageForOpponent = teamDir.getTeamMap().get(opponentTeamName).gettProbByAverage();
		   double tPRrobUsingRPOForOpponent = teamDir.getTeamMap().get(opponentTeamName).gettProbByRPO();

		   double averageProbabilityOfOpponent = (probabilityTeam + tProbUsingAverageForOpponent + tPRrobUsingRPOForOpponent)/3;
		   System.out.println("Team - " + opponentTeamName + ":" + averageProbabilityOfOpponent);
		   
		   if(averageProbabilityOfTeam > averageProbabilityOfOpponent) {
			   match.setTeamWinName(teamName);
			   match.setTeamWin_winProb(averageProbabilityOfTeam);
			   match.setTeamLossName(opponentTeamName);
			   match.setTeamLoss_winProb(averageProbabilityOfOpponent);
			   
		   }else if(averageProbabilityOfTeam < averageProbabilityOfOpponent) {
			   match.setTeamWinName(opponentTeamName);
			   match.setTeamWin_winProb(averageProbabilityOfOpponent);
			   match.setTeamLossName(teamName);
			   match.setTeamLoss_winProb(averageProbabilityOfTeam);
		   }
		   
	   }
	   
	   public static int[] countWinsBetweenTeams(String teamName, String opponentTeamName,List<MatchData> matchlist) {
		   
		   int noOfWinsTeam = 0, noOfWinsOpponent = 0;
		   int totalMatchesBetween = 0;
		   
		   for(MatchData Allmatches : matchlist){
			   if(Allmatches.getTeam().equals(teamName) && Allmatches.getOpponent().equals(opponentTeamName)) {
				   noOfWinsTeam += Allmatches.getWon();
				   noOfWinsOpponent += Allmatches.getLost();
				   totalMatchesBetween += Allmatches.getMatches();
				   
			   }
		   }
//		   System.out.println(teamName);
//		   System.out.println(opponentTeamName);
//		   System.out.println("Team wins " + noOfWinsTeam);
//		   System.out.println("Opponent wins " + noOfWinsOpponent);
//		   System.out.println("Matches played" + totalMatchesBetween);
//		   
		   return new int[]{noOfWinsTeam,noOfWinsOpponent,totalMatchesBetween};
	   }


}
