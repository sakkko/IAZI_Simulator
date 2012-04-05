package IAZI_simulator.code;

import IAZI_simulator.entita.Job;

public class LIFO extends Coda {
	
	public void inserisciJob(Job job){ 
		//inserimento in coda		
		this.coda.add(job); 
	}
	
	public Job prelevaJob(){
		//selezione ultimo elemento della lista
		return this.coda.remove(coda.size()-1); 
	}
	
	public boolean codaVuota(){		
		if(this.coda.isEmpty())
			return true;
		else
			return false;
	}
}