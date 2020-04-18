package com.neu.psa.pojo;
/**
 * 
 * <h1> Data transaction object </h1>
 * Used while storing output of the result in HashMap
 * 
 * @version 1.0
 * @author parth
 *
 */
public class Match {


	public String getTeamWinName() {
		return teamWinName;
	}

	public void setTeamWinName(String teamWinName) {
		this.teamWinName = teamWinName;
	}

	public double getTeamWin_winProb() {
		return teamWin_winProb;
	}

	public void setTeamWin_winProb(double teamWin_winProb) {
		this.teamWin_winProb = teamWin_winProb;
	}

	public String getTeamLossName() {
		return teamLossName;
	}

	public void setTeamLossName(String teamLossName) {
		this.teamLossName = teamLossName;
	}

	public double getTeamLoss_winProb() {
		return teamLoss_winProb;
	}

	public void setTeamLoss_winProb(double teamLoss_winProb) {
		this.teamLoss_winProb = teamLoss_winProb;
	}
	
	private String teamWinName;
	private double teamWin_winProb;
	private String teamLossName;
	private double teamLoss_winProb;
	
}
