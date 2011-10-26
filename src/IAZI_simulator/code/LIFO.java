package IAZI_simulator.code;

import IAZI_simulator.entita.Job;

public class LIFO extends Coda {

	public LIFO(){
		
		super();
	}
	
	public void inserisciJob(Job job){ //inserimento in coda
		
		this.coda.add(job); 
	}
	
	public Job prelevaJob(){
		
		return this.coda.remove(coda.size()-1); //selezione ultimo elemento della lista
	}
	
	public boolean codaVuota(){
		
		if(this.coda.isEmpty())
			return true;
		else
			return false;
	}
}