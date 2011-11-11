package IAZI_simulator.centri;

import IAZI_simulator.code.Coda;
import IAZI_simulator.code.LIFO;
import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class GW1 extends Centro {

	//non uso un id per il centro GW perchè sarà un centro unico per l'impianto, dotato di uan coda
	//che gestisce l'arrivo di piu carichi contemporaneamente
	private static final double TEMPO_MEDIO_SERVIZIO = 0.0005;//secondi
	private Generatore gen_iperesp;
	private Coda LIFO;
	
	public GW1(long seme1, long seme2){	
		this.gen_iperesp = new GeneratoreIperesp(seme1, seme2, 0.5, GW1.TEMPO_MEDIO_SERVIZIO);
		this.LIFO = new LIFO();
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job){		
		if (this.isOccupato()) {
			this.LIFO.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		} else {
			setJob(job);			
			setOccupato(true);			
			return gen_iperesp.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
		}
	}

	public Job prelevaJob(){		
		if(!this.LIFO.codaVuota()){
			return this.LIFO.prelevaJob();
		} else { 
			return null;
		}
	}
	
	public long[] getNuovoSeme() {
		return gen_iperesp.getProssimoSeme();
	}
	

}
