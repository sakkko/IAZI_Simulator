package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class LAN1 extends Centro {
	private static final double TEMPO_MEDIO_SERVIZIO_A  = 0.00016;//secondi
	private static final double TEMPO_MEDIO_SERVIZIO_B = 0.0008;//secondi
	
	private static int cont = 0;
	private int id;

	private Generatore gen_iperespA;
	private Generatore gen_iperespB;
	
	public LAN1(long seme1, long seme2){
		this.id = cont;
		cont = (cont + 1) % 12;
		this.gen_iperespA = new GeneratoreIperesp(seme1, seme2, 0.3, LAN1.TEMPO_MEDIO_SERVIZIO_A);
		this.gen_iperespB = new GeneratoreIperesp(seme1, seme2, 0.3, LAN1.TEMPO_MEDIO_SERVIZIO_B);
	}
	
	//aggiunge un job al centro
	public double aggiungiJob(Job job){
		String classe = job.getClasse();
		setJob(job);
			
		if(classe.equals("A")){
			return this.gen_iperespA.getNext();
		} else {
			return this.gen_iperespB.getNext();
		}
	}


	public int getId() {
		return id;
	}
	
	public long[] getNuovoSeme() {
		return gen_iperespA.getProssimoSeme();
	}
	
	
	
}	