package com.neu.psa.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import com.neu.psa.dao.DBOperation;
import com.neu.psa.pojo.Match;
import com.neu.psa.pojo.MatchData;
import com.neu.psa.pojo.TeamDirectory;


/**
 * <h1>Welcome to Cricket Prediction Ranking System</h1>
 * <h2>Driver class which includes main function</h2>
 * <p>performs calling operations as follows:</p>
 * <ol> 
 * <li>fetches team data from MySQL database and stores in a list of type {@link MatchData} class</li>
 * <li>generate unique set of team names in a HashSet of type {@link String}</li>
 * <li>call Mean & Std deviation function</li>
 * <li>call Probability function for discrete and continuous dataset</li>
 * <li>Get user input of two teams which user wants to compare using {@link Scanner} class</li>
 * <li>Show the winning and losing result on the console</li>
 * </ol>
 * 
 * @author Parth
 *
 */
public class DataService {

	private static TeamDirectory teamDir;
	static
	{
		teamDir = new TeamDirectory();

	}

	/**
	 * <h1>Main Function</h1>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		DBOperation dbOps = new DBOperation();

		// Fetch Match Data from database
		List<MatchData> matchList = dbOps.getDataFromDB();

		// Generate unique set of Team Names
		Set<String> teamNameStat = new HashSet<String>();

		for(MatchData match : matchList) 
			teamNameStat.add(match.getTeam());


		Statistics.calculateMeanAndStdDeviation(matchList,teamDir,teamNameStat);

		Statistics.calculateTProbability(teamDir);

		// Creating HashMap Data Structure
		HashMap<String,Match> teamResult= new HashMap<String,Match>();

		Statistics.calculateProbabilityBetweenAllTeams(teamDir,matchList,teamNameStat,teamResult); 

		System.out.println("---- Welcome to Ranking World ----");
		System.out.println("<<<< Lets predict the future >>>>");
		System.out.println("---- Please enter two team names to be compared ----");
		Scanner scRank = new Scanner(System.in);
		System.out.println("Team 1 name --");
		String cmpA = scRank.next();
		System.out.println("Team 2 name --");
		String cmpB = scRank.next();
		Match match = null;
		if(teamResult.containsKey(cmpA+"-"+cmpB)) {
			match = teamResult.get(cmpA+"-"+cmpB);

			System.out.println("Winning team : " + match.getTeamWinName());
			System.out.println("Winning team probability : " + match.getTeamWin_winProb());

			System.out.println("Losing team : " + match.getTeamLossName());
			System.out.println("Losing team probability : " + match.getTeamLoss_winProb());

		}else if(teamResult.containsKey(cmpB+"-"+cmpA)){
			match = teamResult.get(cmpB+"-"+cmpA);  

			System.out.println("Winning team : " + match.getTeamWinName());
			System.out.println("Winning probability : " + match.getTeamWin_winProb());

			System.out.println("Losing team : " + match.getTeamLossName());
			System.out.println("Losing probability : " + match.getTeamLoss_winProb());

		}else {
			System.out.println("Incorrect Input Details");
		}
		
		// Closing Scanner class
		scRank.close();


	}
}

