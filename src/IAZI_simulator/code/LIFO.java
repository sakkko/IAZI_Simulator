package IAZI_simulator.code;

import java.util.ArrayList;

import IAZI_simulator.entita.Job;

public class LIFO {


	private ArrayList<Job> coda_lifo;
	
	public LIFO(){
		
		coda_lifo = new ArrayList<Job>();
	}
	
	public void inserisciJob(Job job){
		
		this.coda_lifo.add(job); 
	}
	
	public Job prelevaJob(){
		
		return this.coda_lifo.remove(coda_lifo.size()-1);
	}
	
	public boolean codaVuota(){
		
		if(this.coda_lifo.isEmpty())
			return true;
		else
			return false;
	}
}