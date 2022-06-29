package it.polito.tdp.PremierLeague.model;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Simulator {

	// Modello
	private Map<Integer, Team> idMap;
	private List<Match> matches;

	// Parametri della simulazione
	private int N;
	private int X;

	// Coda degli eventi
	private PriorityQueue<Match> queue;

	// Statistiche
	private Statistiche statistiche;

	public void init(int n, int x, Map<Integer, Team> idMap,
			List<Match> matches) {
		this.N = n;
		this.X = x;
		this.idMap = idMap;
		this.matches = matches;
		this.queue = new PriorityQueue<Match>();
		this.statistiche = new Statistiche();
		assegnaReporter();
		creaEventi();
	}

	private void creaEventi() {
		for (Match m : matches) {
			this.queue.add(m);
		}
	}

	private void assegnaReporter() {
		for (Team t : idMap.values()) {
			t.setNumReporter(N);
		}
	}
	
	private void aggiornaStatistiche(int nuovoNum) {
		statistiche.addNumReporter(nuovoNum);
		if (nuovoNum<X) {
			statistiche.incrementaPartiteCritiche();
		}
	}

	public void run() {
		while (!queue.isEmpty()) {
			Match m = queue.poll();
			processaEvento(m);
		}
	}

	private void processaEvento(Match m) {
		switch (m.resultOfTeamHome) {
		case 1:
			int numReporter=0;
			Team vincitore = idMap.get(m.teamHomeID);
			numReporter += vincitore.getNumReporter();
			if (Math.random()<0.5) {
				if (vincitore.getNumReporter()>0) {
					if (vincitore.getSquadreMigliori().size()>0) {
						vincitore.setNumReporter(vincitore.getNumReporter()-1);
						Team scelta = vincitore.getSquadreMigliori().get((int)Math.random()*vincitore.getSquadreMigliori().size());
						scelta.setNumReporter(scelta.getNumReporter()+1);
					}
				}
			}
			Team perdente = idMap.get(m.teamAwayID);
			numReporter += perdente.getNumReporter();
			if (Math.random()<0.2) {
				if (perdente.getNumReporter()>0) {
					if (perdente.getSquadrePeggiori().size()>0) {
						int numReporterBocciati = (int)Math.random()*perdente.getNumReporter();
						perdente.setNumReporter(perdente.getNumReporter()-numReporterBocciati);
						Team scelta = perdente.getSquadrePeggiori().get((int)Math.random()*perdente.getSquadrePeggiori().size());
						scelta.setNumReporter(scelta.getNumReporter()+numReporterBocciati);
					}
				}
			}
			aggiornaStatistiche(numReporter);
			break;
		case -1:
			int numReporter1=0;
			Team vincitore1 = idMap.get(m.teamAwayID);
			numReporter1 += vincitore1.getNumReporter();
			if (Math.random()<0.5) {
				if (vincitore1.getNumReporter()>0) {
					if (vincitore1.getSquadreMigliori().size()>0) {
						vincitore1.setNumReporter(vincitore1.getNumReporter()-1);
						Team scelta = vincitore1.getSquadreMigliori().get((int)Math.random()*vincitore1.getSquadreMigliori().size());
						scelta.setNumReporter(scelta.getNumReporter()+1);
					}
				}
			}
			Team perdente1 = idMap.get(m.teamHomeID);
			numReporter1 += perdente1.getNumReporter();
			if (Math.random()<0.2) {
				if (perdente1.getNumReporter()>0) {
					if (perdente1.getSquadrePeggiori().size()>0) {
						int numReporterBocciati = (int)Math.random()*perdente1.getNumReporter();
						perdente1.setNumReporter(perdente1.getNumReporter()-numReporterBocciati);
						Team scelta = perdente1.getSquadrePeggiori().get((int)Math.random()*perdente1.getSquadrePeggiori().size());
						scelta.setNumReporter(scelta.getNumReporter()+numReporterBocciati);
					}
				}
			}
			aggiornaStatistiche(numReporter1);
			break;
		case 0:
			aggiornaStatistiche(idMap.get(m.teamHomeID).getNumReporter()+idMap.get(m.teamAwayID).getNumReporter());
			break;
		}
	}

	public Statistiche getStatistiche() {
		return statistiche;
	}

}
