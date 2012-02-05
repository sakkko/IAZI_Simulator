package IAZI_simulator.centri;

import IAZI_simulator.IAZI_Simulator;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class PcHC extends Centro {

	private static final double TEMPO_MEDIO_SERVIZIO= 2.33; //secondi
	
	private static int cont = 0;
	private int id;//identificativo dell'i-esimo HOST

	private Generatore gen_iperesp;
		
	public PcHC(long seme1, long seme2){		
		this.id = cont;
		cont = (cont + 1) % IAZI_Simulator.N;
		this.gen_iperesp = new GeneratoreIperesp(seme1, seme2, 0.6, PcHC.TEMPO_MEDIO_SERVIZIO);
	}
	
	public PcHC(PcHC pchc) throws GeneratoreException{	
		super(pchc);
		this.id = pchc.id;
		this.gen_iperesp = new GeneratoreIperesp(pchc.gen_iperesp);
	}
	
	//aggiunge un job al centro
	public double aggiungiJob(Job job){
		
		setJob(job);
		return gen_iperesp.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto per un nuovo servizio
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public long[] getNuovoSeme() {
		return gen_iperesp.getProssimoSeme();
	}
	
	public void setGeneratore(Generatore generatore) throws GeneratoreException {
		if (generatore.getClass().equals(GeneratoreIperesp.class)) {
			this.gen_iperesp = generatore;
		} else {
			throw new GeneratoreException("PCHC: tipo generatore non valido");
		}
	}

	public Generatore getGeneratore() {
		return gen_iperesp;
	}
	
	

}
