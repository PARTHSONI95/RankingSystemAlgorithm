package com.neu.psa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neu.psa.pojo.Match;
import com.neu.psa.pojo.MatchData;
import com.neu.psa.pojo.Team;
import com.neu.psa.pojo.TeamDirectory;

/**
 * <h1>Class performs mathematical analysis for Cricket data system</h1>
 * <p>It computes mean, standard deviation, variance and basic probability calculations</p>
 * 
 * 
 * @author parth
 * 
 */
public class Statistics {


	private static double totalMeanOfAllTeamForAverage;
	private static double totalStdDeviationForAverage;
	private static double totalMeanOfAllTeamForRPO;
	private static double totalStdDeviationForRPO;
	private static int totalCount;  

	/**
	 * <h1>Statistics - Mean and Standard Deviation</h1>
	 * <p>Function computes following :</p>
	 * <ul>
	 * <li>Mean of each team based on Average factor</li>
	 * <li>Mean of each team based on RPO factor</li>
	 * <li>Standard Deviation using calculated mean for each team based on Average factor</li>
	 * <li>Standard Deviation using calculated mean for each team based on RPO factor</li>
	 * <li>Even record total count of matches to calculate probability</li>
	 * </ul>
	 * 	
	 * @param matchList
	 * @param teamDir
	 * @param teamNameStat
	 */
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
					stdDeviationForAverage += Math.pow(match.getAverage() - team.getMean_Of_Average(), 2);
					stdDeviationForRPO += Math.pow(match.getRpo() - team.getMean_Of_RPO(), 2);

					totalStdDeviationForAverage += Math.pow(match.getAverage() - team.getMean_Of_Average(), 2);
					totalStdDeviationForRPO += Math.pow(match.getRpo() - team.getMean_Of_RPO(),2);
				}
			}
			stdDeviationForAverage = Math.sqrt(stdDeviationForAverage/countNoOfSeries);
			team.setStdDeviation_ForAverage(stdDeviationForAverage);

			stdDeviationForRPO = Math.sqrt(stdDeviationForRPO/countNoOfSeries);
			team.setStdDeviation_ForRPO(stdDeviationForRPO);

			teamDir.addInTeamMap(team);
		}

		totalMeanOfAllTeamForAverage = totalMeanOfAllTeamForAverage/totalCount;
		totalMeanOfAllTeamForRPO = totalMeanOfAllTeamForRPO/totalCount;

		totalStdDeviationForAverage = Math.sqrt(totalStdDeviationForAverage/totalCount);
		totalStdDeviationForRPO = Math.sqrt(totalStdDeviationForRPO/totalCount);
	}

	/**
	 * <h1> calls Welch T-Distribution test</h1>
	 * <p>Using the calculated values like:</p> 
	 * To calculate p-score based on Average factor - 
	 * <ul>
	 * <li>Current Mean of Team Average</li>
	 * <li>Total Mean of Team Average</li>
	 * <li>Total Standard Deviation of Team Average</li>
	 * <li>Team series count</li>
	 * <li>Pivot fixed value to differentiate Average/RPO</li>
	 * </ul>
	 * To calculate p-score based on RPO factor - 
	 * <ul>
	 * <li>Current Mean of Team RPO</li>
	 * <li>Total Mean of Team RPO</li>
	 * <li>Total Standard Deviation of Team RPO</li>
	 * <li>Team series count</li>
	 * <li>Pivot fixed value to differentiate Average/RPO</li>
	 * </ul> 
	 * @param teamDir of {@link TeamDirectory} class
	 */
	public static void calculateTProbability(TeamDirectory teamDir) {

		// Calculating T-Distribution (as it requires total Mean and total Standard distribution)
		double tProbUsingAverage;
		double tProbUsingRPO;


		for(Map.Entry<String,Team> team : teamDir.getTeamMap().entrySet()) {

			tProbUsingAverage = WelchTTest.welch_ttest(team.getValue(),totalMeanOfAllTeamForAverage, Math.pow(totalStdDeviationForAverage,2),totalCount,"Average");

			tProbUsingRPO = WelchTTest.welch_ttest(team.getValue(), totalMeanOfAllTeamForRPO, Math.pow(totalStdDeviationForRPO,2), totalCount,"RPO");

			team.getValue().settProbByAverage(tProbUsingAverage);
			team.getValue().settProbByRPO(tProbUsingRPO);

		}

	}
/**
 * Deals with calling probability driver function and storing the end results in a HashMap 
 * 
 * @param teamDir
 * @param matchList
 * @param teamNameStat
 * @param teamResult
 */
	public static void calculateProbabilityBetweenAllTeams(TeamDirectory teamDir,List<MatchData> matchList,Set<String> teamNameStat,HashMap<String,Match> teamResult) {

		for(int i=0; i < matchList.size(); i++) {
			String key1 = matchList.get(i).getTeam() + "-"+ matchList.get(i).getOpponent();
			String key2 = matchList.get(i).getOpponent() + "-" + matchList.get(i).getTeam(); 
			Match match = new Match();
			if(teamResult.containsKey(key1) || teamResult.containsKey(key2)){
				continue;
				// Changes needed
				//match = teamResult.get(key1);

			}else {
				calculateProbability(teamDir,matchList.get(i).getTeam(),matchList.get(i).getOpponent(),matchList,match);
				teamResult.put(key1,match);	  
			}

		}

	}
/**
 * 
 * <h1>Probability Calculator</h1>
 * <p>It's a driver function which makes call to probability function in {@link BinomialProbability} class</p>
 * <p>It's a vital method which even calculates average probability considering all factors:</p>
 * <ul>
 * <li>Win-Loss factor using Binomial Distribution</li>
 * <li>Average factor using T distribution (Welch Unpaired Test)</li>
 * <li>Runs Per Over(RPO) factor using T distribution (Welch Unpaired Test)</li>
 * </ul>
 * 
 * 
 * @param teamDir
 * @param teamName
 * @param opponentTeamName
 * @param matchlist
 * @param match
 */
	public static void calculateProbability(TeamDirectory teamDir,String teamName, String opponentTeamName,List<MatchData> matchlist,Match match) {

		final float successProbInEachTrial = 0.666f;
		int[] stats = countWinsBetweenTeams(teamName,opponentTeamName,matchlist);
		float probabilityTeam = BinomialProbability.calculateBinomialProbability(stats[2],stats[0],successProbInEachTrial);


		double tProbUsingAverageForTeam = teamDir.getTeamMap().get(teamName).gettProbByAverage();
		double tPRrobUsingRPOForTeam = teamDir.getTeamMap().get(teamName).gettProbByRPO();
		double averageProbabilityOfTeam=0.0,averageProbabilityOfOpponent=0.0;


		float probabilityOpponent = BinomialProbability.calculateBinomialProbability(stats[2],stats[1],successProbInEachTrial);

		double tProbUsingAverageForOpponent = teamDir.getTeamMap().get(opponentTeamName).gettProbByAverage();
		double tPRrobUsingRPOForOpponent = teamDir.getTeamMap().get(opponentTeamName).gettProbByRPO();

		if(tProbUsingAverageForTeam < tProbUsingAverageForOpponent){
			averageProbabilityOfTeam += probabilityTeam + 0.75;
			averageProbabilityOfOpponent += probabilityOpponent + 0.25;
		}else if(tProbUsingAverageForTeam > tProbUsingAverageForOpponent) {
			averageProbabilityOfTeam += probabilityTeam + 0.25;
			averageProbabilityOfOpponent += probabilityOpponent + 0.75;
		}

		if(tPRrobUsingRPOForTeam < tPRrobUsingRPOForOpponent){
			averageProbabilityOfTeam += 0.75;
			averageProbabilityOfOpponent += 0.25;
		}else if(tPRrobUsingRPOForTeam > tPRrobUsingRPOForOpponent) {
			averageProbabilityOfTeam += 0.25;
			averageProbabilityOfOpponent += 0.75;
		}

		averageProbabilityOfTeam /= 3;
		averageProbabilityOfOpponent /=3;

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
	/**
	 * <h1>Win Count for Team & Opponent</h1>
	 * <p>Calculate match win count between two teams</p>
	 * @param teamName
	 * @param opponentTeamName
	 * @param matchlist
	 * @return
	 */
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
		return new int[]{noOfWinsTeam,noOfWinsOpponent,totalMatchesBetween};
	}


}
