package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class PcHC extends Centro {

	public static final double TEMPO_MEDIO_SERVIZIO= 2.33333; //secondi
	public static final double PROBABILITA = 0.6;
	
	private static int cont = 0;
	private int id;//identificativo dell'i-esimo HOST

	private Generatore gen_iperesp;
		
	public PcHC(long seme1, long seme2, long seme1Iniziale, long seme2Iniziale, int nClient){		
		this.id = cont;
		cont = (cont + 1) % nClient;
		this.gen_iperesp = new GeneratoreIperesp(seme1, seme2, PROBABILITA, PcHC.TEMPO_MEDIO_SERVIZIO, seme1Iniziale, seme2Iniziale);
	}
	
	//aggiunge un job al centro
	public double aggiungiJob(Job job) throws GeneratoreException{
		
		setJob(job);
		return gen_iperesp.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto per un nuovo servizio
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public long[] getNuovoSeme() throws GeneratoreException {
		return gen_iperesp.getProssimoSeme();
	}
	
	public void setNuovoSeme(long seme1, long seme2, long seme1Iniziale, long seme2Iniziale) {
		gen_iperesp = new GeneratoreIperesp(seme1, seme2, 0.6, PcHC.TEMPO_MEDIO_SERVIZIO, seme1Iniziale, seme2Iniziale);
	}
		

}
