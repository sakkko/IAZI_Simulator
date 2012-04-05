package IAZI_simulator.centri;

import IAZI_simulator.code.Coda;
import IAZI_simulator.code.LIFO;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class GW1 extends Centro {

	public static final double TEMPO_MEDIO_SERVIZIO = 0.0005;//secondi
	public static final double PROBABILITA = 0.5;
	
	private Generatore gen_iperesp;
	private Coda LIFO;
	
	public GW1(long seme1, long seme2, long seme1Iniziale, long seme2Iniziale){	
		this.gen_iperesp = new GeneratoreIperesp(seme1, seme2, PROBABILITA, GW1.TEMPO_MEDIO_SERVIZIO, seme1Iniziale, seme2Iniziale);
		this.LIFO = new LIFO();
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job) throws GeneratoreException{		
		if (this.isOccupato()) {
			//CENTRO OCCUPATO
			this.LIFO.inserisciJob(job);
			return -1;
		} else {
			//CENTRO LIBERO
			setJob(job);			
			setOccupato(true);			
			return gen_iperesp.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
		}
	}

	public Job prelevaJob() throws GeneratoreException{		
		if(!this.LIFO.codaVuota()){
			return this.LIFO.prelevaJob();
		} else { 
			return null;
		}
	}
	
	public long[] getNuovoSeme() throws GeneratoreException {
		return gen_iperesp.getProssimoSeme();
	}
	
	public void setNuovoSeme(long seme1, long seme2, long seme1Iniziale, long seme2Iniziale) {
		this.gen_iperesp = new GeneratoreIperesp(seme1, seme2, 0.5, GW1.TEMPO_MEDIO_SERVIZIO, seme1Iniziale, seme2Iniziale);
	}

}
