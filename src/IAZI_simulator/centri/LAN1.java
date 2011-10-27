package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class LAN1 extends Centro {
	
	private final double tempo_medio_servizio_classeA = 0.00016;//secondi
	private final double tempo_medio_servizio_classeB = 0.0008;//secondi
	private Job job; //identific il job che è in esecuzione nel centro
	private Generatore gen_iperespA;
	private Generatore gen_iperespB;
	
	public LAN1(long[] seme){
		
		this.job = null;
		this.gen_iperespA = new GeneratoreIperesp(seme, 0.3, this.tempo_medio_servizio_classeA);
		this.gen_iperespB = new GeneratoreIperesp(seme, 0.3, this.tempo_medio_servizio_classeB);
	}
	
	//aggiunge un job al centro
	public double aggiungiJob(Job job, double tempoInizioServizio){
		
		this.job = job;
		this.setInizioServizioJob(tempoInizioServizio);
			
		String classe = this.job.getClasse();	
		
		if(classe.equals("A")){
			return this.gen_iperespA.getNext();
		}
		else
			return this.gen_iperespB.getNext();
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