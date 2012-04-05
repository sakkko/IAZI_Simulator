package IAZI_simulator.code;

import IAZI_simulator.entita.Job;


public class FIFO extends Coda {
	

	public FIFO(){
		
		super();
	}
	
	public FIFO(FIFO fifo){		
		super();
		for (int i = 0; i < fifo.coda.size(); i ++) {
			this.coda.add(fifo.coda.get(i));
		}
	}
	
	public void inserisciJob(Job job){ //inserimento in coda
		
		this.coda.add(job); 
	}
	
	public Job prelevaJob(){ // selezione primo elemento della lista
		
		return this.coda.remove(0);
	}
	
	public boolean codaVuota()	{
		
		if(this.coda.isEmpty())
			return true;
		else
			return false;
	}

	
}