package IAZI_simulator.centri;

import IAZI_simulator.code.Coda;
import IAZI_simulator.code.LIFO;
import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class GW1 extends Centro {

	//non uso un id per il centro GW perchè sarà un centro unico per l'impianto, dotato di uan coda
	//che gestisce l'arrivo di piu carichi contemporaneamente
	private final double tempo_medio_servizio = 0.0005;//secondi
	private boolean occupato;
	private Job job; //identific il job che è in esecuzione nel centro
	private Generatore gen_iperesp;
	private Coda LIFO;
	
	public GW1(long[] seme){
		
		this.occupato = false;
		this.job = null;
		this.gen_iperesp = new GeneratoreIperesp(seme, 0.5, this.tempo_medio_servizio);
		this.LIFO = new LIFO();
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job, double tempoInizioServizio){
		
		if(!this.isOccupato()){
			this.LIFO.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		}
		
		else	
			this.job = job;
			this.setInizioServizioJob(tempoInizioServizio);
			this.setOccupato(true);
			
			return gen_iperesp.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
	}
	
	public void setInizioServizioJob(double tempoInizioServizio){
		
		this.job.setTempoInizioServizio(tempoInizioServizio);
	}

	public Job prelevaJob(){
		
		if(!this.LIFO.codaVuota()){
			return this.LIFO.prelevaJob();
		}
		
		else return null;
	}
	
	public Job rimuoviJob(){
		
		Job toRemove = this.job;
		this.job = null;
		this.setOccupato(false);
		
		return toRemove;
	}
	
	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}
	

}
