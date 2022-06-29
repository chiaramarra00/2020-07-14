package it.polito.tdp.PremierLeague.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private Team t1;
	private Team t2;
	private int peso;
	
	public Adiacenza(Team t1, Team t2, int peso) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.peso = peso;
	}

	public Team getT1() {
		return t1;
	}

	public Team getT2() {
		return t2;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public int compareTo(Adiacenza o) {
		return peso-o.peso;
	}
	

}
