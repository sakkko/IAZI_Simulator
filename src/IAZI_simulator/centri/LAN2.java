package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreEsponenziale;

public class LAN2 extends Centro {
	
	private final double tempo_medio_servizio_classeA = 0.00032;//secondi
	private final double tempo_medio_servizio_classeB = 0.00016;//secondi
	private Job job; //identific il job che è in esecuzione nel centro
	private Generatore gen_espA;
	private Generatore gen_espB;
	
	public LAN2(long seme){
		
		this.job = null;
		this.gen_espA = new GeneratoreEsponenziale(seme, this.tempo_medio_servizio_classeA);
		this.gen_espB = new GeneratoreEsponenziale(seme, this.tempo_medio_servizio_classeB);
	}
	
	//aggiunge un job al centro 
	public double aggiungiJob(Job job, double tempoInizioServizio){
		
		this.job = job;
		this.setInizioServizioJob(tempoInizioServizio);
			
		String classe = this.job.getClasse();	
		
		if(classe.equals("A")){
			return this.gen_espA.getNext();
		}
		else
			return this.gen_espB.getNext();
	}
	
	public void setInizioServizioJob(double tempoInizioServizio){
		
		this.job.setTempoInizioServizio(tempoInizioServizio);
	}

	public Job rimuoviJob(){
		
		Job toRemove = this.job;
		this.job = null;
		return toRemove;
	}
	
	
}	