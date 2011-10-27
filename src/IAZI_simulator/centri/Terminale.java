package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreEsponenziale;

public class Terminale extends Centro {
	
	private static int cont = 0;
	private int id;
	private final double tempo_medio_servizio = 25.0;//secondi
	private Job job;
	private Generatore gen_esp;
	
	public Terminale(long seme){
		
		this.setId(cont);
		cont++;
		this.job = null;
		this.gen_esp = new GeneratoreEsponenziale(seme, tempo_medio_servizio);
	}
	
	public double aggiungiJob(Job job, double tempoInizioServizio){
		
		this.job = job;
		this.setInizioServizioJob(tempoInizioServizio);
		return gen_esp.getNext();//ritorno il prossimo tempo in cui il centro sarà pronto per un nuovo servizio
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
