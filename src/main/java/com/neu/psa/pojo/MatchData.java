package com.neu.psa.pojo;

/**
 * 
 * <h1>Data Transaction Object - MatchData</h1>
 * <p>Used while storing the object values fetched from MySQL database</p>
 * 
 * @version 2.0
 * @author parth
 *
 */
public class MatchData {

	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getOpponent() {
		return opponent;
	}
	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}
	public int getMatches() {
		return matches;
	}
	public void setMatches(int matches) {
		this.matches = matches;
	}
	public int getWon() {
		return won;
	}
	public void setWon(int won) {
		this.won = won;
	}
	public int getLost() {
		return lost;
	}
	public void setLost(int lost) {
		this.lost = lost;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public double getRpo() {
		return rpo;
	}
	public void setRpo(double rpo) {
		this.rpo = rpo;
	}

	private String team;
	private String opponent;
	private int matches;
	private int won;
	private int lost;
	private double average;
	private double rpo;


}

