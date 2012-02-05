package IAZI_simulator.centri;

import IAZI_simulator.IAZI_Simulator;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreEsponenziale;

public class LAN2 extends Centro {
	private static final double TEMPO_MEDIO_SERVIZIO_A = 0.00032;//secondi
	private static final double TEMPO_MEDIO_SERVIZIO_B = 0.00016;//secondi
	
	private static int cont = 0;
	private int id;

	private Generatore gen_espA;
	private Generatore gen_espB;
	
	public LAN2(long seme){
		this.id = cont;
		cont = (cont + 1) % IAZI_Simulator.N;
		this.gen_espA = new GeneratoreEsponenziale(seme, LAN2.TEMPO_MEDIO_SERVIZIO_A);
		this.gen_espB = new GeneratoreEsponenziale(seme, LAN2.TEMPO_MEDIO_SERVIZIO_B);
	}
	
	public LAN2(LAN2 lan2) throws GeneratoreException{
		super(lan2);
		this.id = lan2.id;
		this.gen_espA = new GeneratoreEsponenziale(lan2.gen_espA);
		this.gen_espB = new GeneratoreEsponenziale(lan2.gen_espB);
	}
	
	//aggiunge un job al centro 
	public double aggiungiJob(Job job){
		String classe = job.getClasse();		
		setJob(job);
	
		if(classe.equals("A")){
			return this.gen_espA.getNext();
		} else {
			return this.gen_espB.getNext();
		}
	}
	
	public int getId() {
		return id;
	}
	
	public long getNuovoSeme() {
		return gen_espA.getProssimoSeme()[0];
	}
	
	public void setGeneratoreA(Generatore generatore) throws GeneratoreException {
		if (generatore.getClass().equals(GeneratoreEsponenziale.class)) {
			this.gen_espA = generatore;
		} else {
			throw new GeneratoreException("LAN2(A): tipo generatore non valido");
		}
	}

	public Generatore getGeneratoreA() {
		return gen_espA;
	}
	
	public void setGeneratoreB(Generatore generatore) throws GeneratoreException {
		if (generatore.getClass().equals(GeneratoreEsponenziale.class)) {
			this.gen_espB = generatore;
		} else {
			throw new GeneratoreException("LAN2(B): tipo generatore non valido");
		}
	}

	public Generatore getGeneratoreB() {
		return gen_espB;
	}
	
}	