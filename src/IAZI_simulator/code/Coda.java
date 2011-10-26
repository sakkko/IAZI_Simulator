package IAZI_simulator.code;

import java.util.ArrayList;

import IAZI_simulator.entita.Job;

public abstract class Coda {
	
	protected ArrayList<Job> coda;
	
	public Coda(){
		
		coda = new ArrayList<Job>();
	}

	public abstract void inserisciJob(Job job);
	
	public abstract Job prelevaJob();
	
	public abstract boolean codaVuota();
}
