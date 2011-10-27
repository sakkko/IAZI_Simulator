package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreKerl;

public class WAN extends Centro {

	private static int cont = 0;
	private int id;
	private final double tempo_medio_servizio = 0.003;//secondi
	private Job job; //identifica il job che è in esecuzione nel centro
	private Generatore gen_4erlang;
	
	public WAN(long[] seme){
		
		this.setId(cont);
		cont++;
		this.job = null;
		this.gen_4erlang = new GeneratoreKerl(seme, 4, this.tempo_medio_servizio);
	}
	
	//aggiunge un job al centro 
	public double aggiungiJob(Job job, double tempoInizioServizio){
		
			this.job = job;
			this.setInizioServizioJob(tempoInizioServizio);
			return gen_4erlang.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
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
