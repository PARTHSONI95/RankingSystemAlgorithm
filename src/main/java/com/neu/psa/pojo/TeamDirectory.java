package com.neu.psa.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamDirectory {

	
	private Map<String,Team> teamMap = new HashMap<String,Team>();
	
	
	public Map<String, Team> getTeamMap() {
		return teamMap;
	}

	public void setTeamMap(Map<String, Team> teamMap) {
		this.teamMap = teamMap;
	}
	
	public void addInTeamMap(Team team){
		
		teamMap.put(team.getTeamName(),team);
	}
	
	
}
