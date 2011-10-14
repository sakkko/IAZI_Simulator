package IAZI_simulator.code;

import java.util.ArrayList;

import IAZI_simulator.entita.Job;


public class FIFO {
	

	private ArrayList<Job> coda_fifo;
	
	public FIFO(){
		
		coda_fifo = new ArrayList<Job>();
	}
	
	public void inserisciJob(Job job){
		
		this.coda_fifo.add(job); 
	}
	
	public Job prelevaJob(){
		
		return this.coda_fifo.remove(0);
	}
	
	public boolean codaVuota()	{
		
		if(this.coda_fifo.isEmpty())
			return true;
		else
			return false;
	}

}