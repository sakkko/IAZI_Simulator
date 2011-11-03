package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreKerl;

public class WAN extends Centro {

	private static int cont = 0;
	private int id;
	private static final double TEMPO_MEDIO_SERVIZIO = 0.003;//secondi
	private Generatore gen_4erlang;
	
	public WAN(long[] seme) throws GeneratoreException {
		super();
		if (seme.length != 4) {
			throw new GeneratoreException("WAN " + cont + ": numero di semi non valido");
		}
		
		this.id = cont;
		cont ++;
		this.gen_4erlang = new GeneratoreKerl(seme, 4, WAN.TEMPO_MEDIO_SERVIZIO);
	}
	
	//aggiunge un job al centro 
	public double aggiungiJob(Job job, double tempoInizioServizio){		
		setJob(job);
		setInizioServizioJob(tempoInizioServizio);
		return gen_4erlang.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	

}
