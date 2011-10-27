package IAZI_simulator.centri;

import IAZI_simulator.code.Coda;
import IAZI_simulator.code.RAND;
import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreKerl;

public class Disk extends Centro{
	
	private static int cont = 0;
	private int id;
	private final double tempo_medio_servizio = 0.0033; //(non se se è corretto questo valore!!)
	private boolean occupato;
	private Job job;
	private Generatore gen_5erlang;
	private Coda RAND;
	
	public Disk(long[] semeGeneratore, long semeCoda){
		
		this.id = cont;
		cont++;
		this.occupato = false;
		this.job = null;
		this.gen_5erlang = new GeneratoreKerl(semeGeneratore, 5, this.tempo_medio_servizio);
		this.RAND = new RAND(semeCoda);
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job, double tempoInizioServizio){
		
		if(!this.isOccupato()){
			this.RAND.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		}
		
		else	
			this.job = job;
			this.setInizioServizioJob(tempoInizioServizio);
			this.setOccupato(true);
			
			return gen_5erlang.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
										
	}
	
	public void setInizioServizioJob(double tempoInizioServizio){
		
		this.job.setTempoInizioServizio(tempoInizioServizio);
	}

	public Job prelevaJob(){
		
		if(!this.RAND.codaVuota()){
			return this.RAND.prelevaJob();
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

	

