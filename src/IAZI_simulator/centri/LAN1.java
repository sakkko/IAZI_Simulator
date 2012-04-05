package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class LAN1 extends Centro {
	
	public static final double TEMPO_MEDIO_SERVIZIO_A  = 0.00016;//secondi
	public static final double TEMPO_MEDIO_SERVIZIO_B = 0.00008;//secondi
	
	public static final double PROBABILITA = 0.3;
	private static int cont = 0;
	private int id;

	private Generatore gen_iperespA;
	private Generatore gen_iperespB;
	
	public LAN1(long[] semi, long[] semiIniziali, int nClient) throws GeneratoreException{
		if (semi.length != 4) {
			throw new GeneratoreException("LAN1: Numero di semi non valido");
		}
		
		this.id = cont;
		cont = (cont + 1) % nClient;
		this.gen_iperespA = new GeneratoreIperesp(semi[0], semi[1], PROBABILITA, LAN1.TEMPO_MEDIO_SERVIZIO_A, semiIniziali[0], semiIniziali[1]);
		this.gen_iperespB = new GeneratoreIperesp(semi[2], semi[3], PROBABILITA, LAN1.TEMPO_MEDIO_SERVIZIO_B, semiIniziali[2], semiIniziali[3]);
	}
	
	//aggiunge un job al centro
	public double aggiungiJob(Job job) throws GeneratoreException{
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
	
	public long[] getNuovoSeme() throws GeneratoreException {
		long[] ret = new long[4];
		long[] tmp = gen_iperespA.getProssimoSeme();
		ret[0] = tmp[0];
		ret[1] = tmp[1];
		tmp = gen_iperespB.getProssimoSeme();
		ret[2] = tmp[0];
		ret[3] = tmp[1];
		
		return ret;
	}
	
	public void setNuovoSeme(long[] semi, long[] semiIniziali) throws GeneratoreException {
		if (semi.length != 4) {
			throw new GeneratoreException("LAN1: Numero di semi non valido");
		}
		
		this.gen_iperespA = new GeneratoreIperesp(semi[0], semi[1], 0.3, LAN1.TEMPO_MEDIO_SERVIZIO_A, semiIniziali[0], semiIniziali[1]);
		this.gen_iperespB = new GeneratoreIperesp(semi[2], semi[3], 0.3, LAN1.TEMPO_MEDIO_SERVIZIO_B, semiIniziali[2], semiIniziali[3]);
	}
	
	
}	