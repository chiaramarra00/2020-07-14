package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
	Integer teamID;
	String name;
	private Integer points;
	private Integer numReporter;
	private List<Team> squadreMigliori;
	private List<Team> squadrePeggiori;
	
	public List<Team> getSquadrePeggiori() {
		return squadrePeggiori;
	}

	public List<Team> getSquadreMigliori() {
		return squadreMigliori;
	}

	public Integer getNumReporter() {
		return numReporter;
	}

	public void setNumReporter(Integer numReporter) {
		this.numReporter = numReporter;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
		points = 0;
		numReporter = 0;
		squadreMigliori = new ArrayList<Team>();
		squadrePeggiori = new ArrayList<Team>();
	}
	
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}

	public void addSquadraPeggiore(Team t) {
		squadrePeggiori.add(t);
	}

	public void addSquadraMigliore(Team t) {
		squadreMigliori.add(t);
	}
	
}
