package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreEsponenziale;

public class Terminale extends Centro {
	
	private static int cont = 0;
	private int id;
	private static final double TEMPO_MEDIO_SERVIZIO = 25.0;//secondi
	private Generatore gen_esp;
	
	public Terminale(long seme){
		super();
		this.setId(cont);
		cont ++;
		this.gen_esp = new GeneratoreEsponenziale(seme, Terminale.TEMPO_MEDIO_SERVIZIO);
	}
	
	public double aggiungiJob(Job job, double tempoInizioServizio){
		setJob(job);
		setInizioServizioJob(tempoInizioServizio);
		return gen_esp.getNext();//ritorno il prossimo tempo in cui il centro sarà pronto per un nuovo servizio
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
