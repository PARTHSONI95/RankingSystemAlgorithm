package com.neu.psa.pojo;

public class Team {
	
	private String teamName;
	private int teamSeriesCount;
	private int teamMatchCount;
	private double mean_Of_Average;
	private double mean_Of_RPO;
	private double stdDeviation_ForAverage;
	private double stdDeviation_ForRPO;
	private double tProbByAverage;
	private double tProbByRPO;
	
	
	
	public double getStdDeviation_ForAverage() {
		return stdDeviation_ForAverage;
	}
	public void setStdDeviation_ForAverage(double stdDeviation_ForAverage) {
		this.stdDeviation_ForAverage = stdDeviation_ForAverage;
	}
	public double getStdDeviation_ForRPO() {
		return stdDeviation_ForRPO;
	}
	public void setStdDeviation_ForRPO(double stdDeviation_ForRPO) {
		this.stdDeviation_ForRPO = stdDeviation_ForRPO;
	}
	public double getMean_Of_Average() {
		return mean_Of_Average;
	}
	public void setMean_Of_Average(double mean_Of_Average) {
		this.mean_Of_Average = mean_Of_Average;
	}
	public double getMean_Of_RPO() {
		return mean_Of_RPO;
	}
	public void setMean_Of_RPO(double mean_Of_RPO) {
		this.mean_Of_RPO = mean_Of_RPO;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
	public int getTeamSeriesCount() {
		return teamSeriesCount;
	}
	public void setTeamSeriesCount(int teamSeriesCount) {
		this.teamSeriesCount = teamSeriesCount;
	}
	
	
	public int getTeamMatchCount() {
		return teamMatchCount;
	}
	public void setTeamMatchCount(int teamMatchCount) {
		this.teamMatchCount = teamMatchCount;
	}
	public double gettProbByAverage() {
		return tProbByAverage;
	}
	public void settProbByAverage(double tProbByAverage) {
		this.tProbByAverage = tProbByAverage;
	}
	public double gettProbByRPO() {
		return tProbByRPO;
	}
	public void settProbByRPO(double tProbByRPO) {
		this.tProbByRPO = tProbByRPO;
	}
	
	
}
