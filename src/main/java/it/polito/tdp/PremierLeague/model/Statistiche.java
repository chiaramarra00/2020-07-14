package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;

public class Statistiche {
	
	private List<Integer> reporterPerPartita;
	private int numPartiteCritiche;
	
	public Statistiche() {
		this.reporterPerPartita = new ArrayList<Integer>();
		this.numPartiteCritiche = 0;
	}

	public void addNumReporter(int n) {
		reporterPerPartita.add(n);
	}

	public void incrementaPartiteCritiche() {
		numPartiteCritiche = getNumPartiteCritiche() + 1;
	}

	public int getNumPartiteCritiche() {
		return numPartiteCritiche;
	}

	public double getMediaReporter() {
		double somma=0;
		for (Integer i : reporterPerPartita) {
			somma += i;
		}
		return somma/reporterPerPartita.size();
	}
	
}
