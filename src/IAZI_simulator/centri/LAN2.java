package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreEsponenziale;

public class LAN2 extends Centro {
	
	public static final double TEMPO_MEDIO_SERVIZIO_A = 0.00032;//secondi
	public static final double TEMPO_MEDIO_SERVIZIO_B = 0.00016;//secondi
	
	private static int cont = 0;
	private int id;

	private Generatore gen_espA;
	private Generatore gen_espB;
	
	public LAN2(long[] semi, long[] semiIniziali, int nClient) throws GeneratoreException{
		if (semi.length != 2) {
			throw new GeneratoreException("LAN2: Numero di semi non valido");
		}
		
		this.id = cont;
		cont = (cont + 1) % nClient;
		this.gen_espA = new GeneratoreEsponenziale(semi[0], LAN2.TEMPO_MEDIO_SERVIZIO_A, semiIniziali[0]);
		this.gen_espB = new GeneratoreEsponenziale(semi[1], LAN2.TEMPO_MEDIO_SERVIZIO_B, semiIniziali[1]);
	}
	
	//aggiunge un job al centro 
	public double aggiungiJob(Job job) throws GeneratoreException{
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
	
	public long[] getNuovoSeme() throws GeneratoreException {
		long[] ret = new long[2];
		ret[0] = gen_espA.getProssimoSeme()[0];
		ret[1] = gen_espB.getProssimoSeme()[0];
	
		return ret;
	}
	
	public void setNuovoSeme(long[] semi, long[] semiIniziali) throws GeneratoreException {
		if (semi.length != 2) {
			throw new GeneratoreException("LAN2: Numero di semi non valido");
		}
		
		this.gen_espA = new GeneratoreEsponenziale(semi[0], LAN2.TEMPO_MEDIO_SERVIZIO_A, semiIniziali[0]);
		this.gen_espB = new GeneratoreEsponenziale(semi[1], LAN2.TEMPO_MEDIO_SERVIZIO_B, semiIniziali[1]);
	}
	
	
}	