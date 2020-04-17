package com.neu.psa.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neu.psa.dao.DBOperation;
import com.neu.psa.pojo.Match;
import com.neu.psa.pojo.MatchData;
import com.neu.psa.pojo.TeamDirectory;



public class DataService {
	
			static int loop;
			private static TeamDirectory teamDir;
			static
			{
				teamDir = new TeamDirectory();
				
			}
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
			   
			  
			  String cmpA = "New Zealand";
			  String cmpB = "West Indies";
			  
			  Match match = teamResult.get(cmpA+"-"+cmpB);
			  
			  System.out.println("Winning team : " + match.getTeamWinName());
			  System.out.println("Winning probability : " + match.getTeamWin_winProb());
			  
			  System.out.println("Losing team : " + match.getTeamLossName());
			  System.out.println("Losing probability : " + match.getTeamLoss_winProb());
			     
		   }
		   
		   		
	}

