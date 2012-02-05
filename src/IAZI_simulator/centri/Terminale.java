package IAZI_simulator.centri;

import IAZI_simulator.IAZI_Simulator;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreEsponenziale;

public class Terminale extends Centro {
	
	private static int cont = 0;
	private int id;
	private static final double TEMPO_MEDIO_SERVIZIO = 12.5;//secondi
	private Generatore gen_esp;
	
	public Terminale(long seme){
		super();
		this.setId(cont);
		cont = (cont + 1) % IAZI_Simulator.N;
		this.gen_esp = new GeneratoreEsponenziale(seme, Terminale.TEMPO_MEDIO_SERVIZIO);
	}
	
	public Terminale(Terminale term) throws GeneratoreException{
		super(term);
		this.id = term.id;
		this.gen_esp = new GeneratoreEsponenziale(term.gen_esp);
	}
	
	public double aggiungiJob(Job job){
		setJob(job);
		return gen_esp.getNext();//ritorno il prossimo tempo in cui il centro sarà pronto per un nuovo servizio
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public long getNuovoSeme() {
		return gen_esp.getProssimoSeme()[0];
	}
	
	public void setGeneratore(Generatore generatore) throws GeneratoreException {
		if (generatore.getClass().equals(GeneratoreEsponenziale.class)) {
			this.gen_esp = generatore;
		} else {
			throw new GeneratoreException("TERM: tipo generatore non valido");
		}
	}

	public Generatore getGeneratore() {
		return gen_esp;
	}
	
	

}
