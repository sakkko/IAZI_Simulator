package IAZI_simulator.entita;

import IAZI_simulator.IAZI_Simulator;

public class Job {
	
	private double tempoInizioServizio; //tempo in cui il job entra nel centro
	private String classe; 
	private int id;
	private boolean nuovo;
	private static int cont = 0;
	
	public Job() {
		this.tempoInizioServizio = 0.0;
		this.id = cont;
		cont = (cont + 1) % IAZI_Simulator.N;
		this.classe = "A";
		this.nuovo = true;
	}
	
	public double getTempoInizioServizio() {
		return tempoInizioServizio;
	}
	
	public void setTempoInizioServizio(double tempoInizioServizio) {
		this.tempoInizioServizio = tempoInizioServizio;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public int getId(){
		return id;
	}

	public boolean isNuovo() {
		return nuovo;
	}

	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}
	
	
	
	

}