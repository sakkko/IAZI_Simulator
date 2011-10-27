package IAZI_simulator.centri;

import IAZI_simulator.code.Coda;
import IAZI_simulator.code.FIFO;
import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class PcHS extends Centro {
	
	private static int cont = 0;
	private int id;//identificativo dell'i-esimo HOST
	private final double tempo_medio_servizio = 0.00274;//secondi
	private boolean occupato;
	private Job job; //identific il job che è in esecuzione nel centro
	private Generatore gen_iperesp;
	private Coda FIFO;
	
	public PcHS(long[] seme){
		
		this.id = cont;
		cont++;
		this.occupato = false;
		this.job = null;
		this.gen_iperesp = new GeneratoreIperesp(seme, 0.6, this.tempo_medio_servizio);
		this.FIFO = new FIFO();
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job, double tempoInizioServizio){
		
		if(!this.isOccupato()){
			this.FIFO.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		}
		
		else	
			this.job = job;
			this.setInizioServizioJob(tempoInizioServizio);
			this.setOccupato(true);
			
			return gen_iperesp.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
										//(credo che questo valore serva per aggiornare il calendario degli eventi!)
	}
	
	public void setInizioServizioJob(double tempoInizioServizio){
		
		this.job.setTempoInizioServizio(tempoInizioServizio);
	}

	public Job prelevaJob(){
		
		if(!this.FIFO.codaVuota()){
			return this.FIFO.prelevaJob();
		}
		
		else return null;
	}
	
	public Job rimuoviJob(){
		
		Job toRemove = this.job;
		this.job = null;
		this.setOccupato(false);
		
		return toRemove;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}
	

}
