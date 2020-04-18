package com.neu.psa.pojo;

import java.util.HashMap;
import java.util.Map;
/**
 * <h1>Directory storing team data</h1>
 * <p>It uses a HashMap to store the computes team values where:</p>
 * <ul><li>Key - Team Name</li>
 * <li>Value - Team Object of class {@link Team}</li>
 * 
 * 
 * @author parth
 *
 */
public class TeamDirectory {

	public Map<String, Team> getTeamMap() {
		return teamMap;
	}

	public void setTeamMap(Map<String, Team> teamMap) {
		this.teamMap = teamMap;
	}
	
	public void addInTeamMap(Team team){
		
		teamMap.put(team.getTeamName(),team);
	}
	
	private Map<String,Team> teamMap = new HashMap<String,Team>();
	
}
