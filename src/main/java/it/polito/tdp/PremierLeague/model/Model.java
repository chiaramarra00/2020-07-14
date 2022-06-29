package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	private PremierLeagueDAO dao;

	private Graph<Team, DefaultWeightedEdge> grafo;
	private Map<Integer, Team> idMap;
	
	private Simulator sim;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<>();

		this.dao.listAllTeams(idMap);
		
		sim = new Simulator();
	}

	public void creaGrafo() {
		// creo il grafo
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, idMap.values());

		// aggiungo gli archi
		for (Team vertex : grafo.vertexSet()) {
			calcolaPunti(vertex.getTeamID());
			doSquadreMigliori(vertex);
			doSquadrePeggiori(vertex);
		}
		for(Team t1 : idMap.values()) {
			for(Team t2 : idMap.values()) {
				if(!t1.equals(t2)) {
					if (t1.getPoints()>t2.getPoints()) {
						double peso = t1.getPoints()-t2.getPoints();
						Graphs.addEdge(this.grafo, t1, t2, peso);
					}
				}
			}
		}

		System.out.println("Grafo creato!");
		System.out.println(String.format("# Vertici: %d", this.grafo.vertexSet().size()));
		System.out.println(String.format("# Archi: %d", this.grafo.edgeSet().size()));

	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}

	private void calcolaPunti(Integer teamID) {
		int punti = 0;
		List<Match> matches = dao.listAllMatches();
		for (Match m : matches) {
			if (teamID==m.teamHomeID) {
				switch (m.resultOfTeamHome) {
				case 1:
					punti += 3;
					break;
				case -1:
					break;
				case 0:
					punti += 1;
					break;
				}
			}
			else if (teamID==m.teamAwayID) {
				switch (m.resultOfTeamHome) {
				case 1:
					break;
				case -1:
					punti += 3;
					break;
				case 0:
					punti += 1;
					break;
				}
			}
		}
		idMap.get(teamID).setPoints(punti);
	}

	public Map<Integer, Team> getIdMap() {
		return idMap;
	}

	public List<Adiacenza> doSquadreMigliori(Team squadra) {
		List<Adiacenza> squadreMigliori = new ArrayList<Adiacenza>();
		for (Team t : idMap.values()) {
			if (t.getPoints()>squadra.getPoints()) {
				squadreMigliori.add(new Adiacenza(squadra,t,t.getPoints()-squadra.getPoints()));
				squadra.addSquadraMigliore(t);
			}
		}
		Collections.sort(squadreMigliori);
		return squadreMigliori;
	}

	public List<Adiacenza> doSquadrePeggiori(Team squadra) {
		List<Adiacenza> squadrePeggiori = new ArrayList<Adiacenza>();
		for (Team t : idMap.values()) {
			if (t.getPoints()<squadra.getPoints()) {
				squadrePeggiori.add(new Adiacenza(squadra,t,squadra.getPoints()-t.getPoints()));
				squadra.addSquadraPeggiore(t);
			}
		}
		Collections.sort(squadrePeggiori);
		return squadrePeggiori;
	}

	public Statistiche simula (int N, int X) {
		List<Match> matches = dao.listAllMatches();
		sim.init(N,X,idMap,matches);
		sim.run();
		return sim.getStatistiche();
	}

}
