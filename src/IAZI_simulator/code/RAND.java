package IAZI_simulator.code;
import java.util.ArrayList;

import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.GeneratoreUniforme;

public class RAND {

	private ArrayList<Job> coda_rand;
	private GeneratoreUniforme generatore_uniforme;
	
	public RAND(long seme){
		
		this.generatore_uniforme = new GeneratoreUniforme(seme);
		this.coda_rand = new ArrayList<Job>();
	}
	
	public void inserisciJob(Job job){  //inserimento in coda
		
		this.coda_rand.add(job);
	}
	
	public Job prelevaJob(){ //selezione elemento con indice casuale
		
		int posizione = (int) (this.generatore_uniforme.getNext()* this.coda_rand.size());
		return this.coda_rand.remove(posizione);
	}
	
	public boolean codaVuota()
	{
		
		if(this.coda_rand.isEmpty())
			return true;
		else
			return false;
	}
	
}