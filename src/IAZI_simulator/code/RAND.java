package IAZI_simulator.code;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreUniforme;

public class RAND extends Coda{

	private Generatore generatore_uniforme;
	
	public RAND(long seme){
		
		super();
		this.generatore_uniforme = new GeneratoreUniforme(seme);
	}
	
	public RAND(RAND rand) throws GeneratoreException{		
		super();		
		this.generatore_uniforme = new GeneratoreUniforme(rand.generatore_uniforme);
		for (int i = 0; i < rand.coda.size(); i ++) {
			this.coda.add(rand.coda.get(i));
		}
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
	
	public long getNuovoSeme() {
		return generatore_uniforme.getProssimoSeme()[0];
	}

	public Generatore getGeneratore() {
		return generatore_uniforme;
	}
	
	public void setGeneratore(Generatore generatore) throws GeneratoreException {
		if (generatore.getClass().equals(GeneratoreUniforme.class)) {
			this.generatore_uniforme = generatore;
		} else {
			throw new GeneratoreException("RAND: generatore non valido");
		}
	}
	
	
}