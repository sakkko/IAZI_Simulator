package IAZI_simulator.code;

import java.util.ArrayList;

import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;

public abstract class Coda {
	
	protected ArrayList<Job> coda;
	
	public Coda(){
		
		coda = new ArrayList<Job>();
	}

	public abstract void inserisciJob(Job job);
	
	public abstract Job prelevaJob() throws GeneratoreException;
	
	public abstract boolean codaVuota();
}
