package IAZI_simulator.code;

import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.GeneratoreUniforme;

public class RAND extends Coda{

	private GeneratoreUniforme generatore_uniforme;
	
	public RAND(long seme, long semeIniziale){
		this.generatore_uniforme = new GeneratoreUniforme(seme, semeIniziale);
	}
	
	public void inserisciJob(Job job){  
		//inserimento in coda
		this.coda.add(job);
	}
	
	public Job prelevaJob() throws GeneratoreException { 
		//selezione elemento con indice casuale
		int posizione = (int) (this.generatore_uniforme.getNext()* this.coda.size());
		return this.coda.remove(posizione);
	}
	
	public boolean codaVuota()
	{
		if(this.coda.isEmpty())
			return true;
		else
			return false;
	}
	
	public long getNuovoSeme() throws GeneratoreException {
		return generatore_uniforme.getProssimoSeme()[0];
	}
	
	public void setNuovoSeme(long seme, long semeIniziale) {
		this.generatore_uniforme = new GeneratoreUniforme(seme, semeIniziale);
	}
	
	
}