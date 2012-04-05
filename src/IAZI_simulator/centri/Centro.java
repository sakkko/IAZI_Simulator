package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;

/* Per ogni centro specifico andrà poi creato come attributo un identificativo unico,
 * un rispettivo generatore per la generazione dei tempi di servizio, e un rispettivo oggetto
 * di tipo coda che sarà assegnato al centro che ne richiede una.  
 */

public abstract class Centro {
	
	private boolean occupato;
	private Job job;
	
	public Centro() {
		occupato = false;
		job = null;
	}
	
	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	
	public Job rimuoviJob(){
		Job toRemove = job;
		job = null;
		occupato = false;
		return toRemove;
	}
	
	public void setInizioServizioJob(double tempoInizioServizio){		
		this.job.setTempoInizioServizio(tempoInizioServizio);
	}

	//aggiunge un job al centro se è libero, altrimenti lo mette in coda(dove prevista)
	public abstract double aggiungiJob(Job job) throws GeneratoreException;
	
	
}