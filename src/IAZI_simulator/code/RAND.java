package IAZI_simulator.code;
import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.GeneratoreUniforme;

public class RAND extends Coda{

	private GeneratoreUniforme generatore_uniforme;
	
	public RAND(long seme){
		
		super();
		this.generatore_uniforme = new GeneratoreUniforme(seme);
	}
	
	public void inserisciJob(Job job){  //inserimento in coda
		
		this.coda.add(job);
	}
	
	public Job prelevaJob(){ //selezione elemento con indice casuale
		
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
	
}