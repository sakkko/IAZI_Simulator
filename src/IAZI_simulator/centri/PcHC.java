package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class PcHC extends Centro {
	
	private static int cont = 0;
	private int id;//identificativo dell'i-esimo HOST
	private final double tempo_medio_servizio = 3.0; //secondi
	private Job job; //identific il job che è in esecuzione nel centro
	private Generatore gen_iperesp;
		
	public PcHC(long[] seme){
		
		this.id = cont;
		cont++;
		this.job = null;
		this.gen_iperesp = new GeneratoreIperesp(seme, 0.6, this.tempo_medio_servizio);
	}
	
	//aggiunge un job al centro
	public double aggiungiJob(Job job, double tempoInizioServizio){
		
		this.job = job;
		this.setInizioServizioJob(tempoInizioServizio);
		return gen_iperesp.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto per un nuovo servizio
										//(credo che questo valore serva per aggiornare il calendario degli eventi!)
	}
	
	public void setInizioServizioJob(double tempoInizioServizio){
		
		this.job.setTempoInizioServizio(tempoInizioServizio);
	}

	public Job rimuoviJob(){
		
		Job toRemove = this.job;
		this.job = null;
		return toRemove;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	

}
