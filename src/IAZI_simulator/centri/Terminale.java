package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.GeneratoreEsponenziale;

public class Terminale extends Centro {

	private static int cont = 0;
	private int id;
	public static final double TEMPO_MEDIO_SERVIZIO = 12.5;//secondi
	private GeneratoreEsponenziale gen_esp;
	
	public Terminale(long seme, long semeIniziale, int nClient){
		super();
		this.setId(cont);
		cont = (cont + 1) % nClient;
		this.gen_esp = new GeneratoreEsponenziale(seme, Terminale.TEMPO_MEDIO_SERVIZIO, semeIniziale);
	}
	
	public double aggiungiJob(Job job) throws GeneratoreException{
		setJob(job);
		return gen_esp.getNext();//ritorno il prossimo tempo in cui il centro sarà pronto per un nuovo servizio
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public long getNuovoSeme() throws GeneratoreException {
		return gen_esp.getProssimoSeme()[0];
	}
	
	public void setNuovoSeme(long seme, long semeIniziale) {
		gen_esp = new GeneratoreEsponenziale(seme, Terminale.TEMPO_MEDIO_SERVIZIO, semeIniziale);
	}
	
	public GeneratoreEsponenziale getGeneratore() {
		return gen_esp;
	}

	@Override
	public String toString() {
		return "Terminale [id=" + id + ", seme_gen=" + gen_esp.getGeneratore().getSeme() + "]";
	}

	
}
