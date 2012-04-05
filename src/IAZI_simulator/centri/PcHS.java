package IAZI_simulator.centri;

import IAZI_simulator.code.Coda;
import IAZI_simulator.code.FIFO;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreIperesp;

public class PcHS extends Centro {
	
	private static int cont = 0;
	private int id;//identificativo dell'i-esimo HOST
	public static final double TEMPO_MEDIO_SERVIZIO = 0.0027;//secondi (N = 12)
	//public static final double TEMPO_MEDIO_SERVIZIO = 0.002565;//secondi (N = 48)
	public static final double PROBABILITA = 0.6;
	private Generatore gen_iperesp;
	private Coda FIFO;
	
	public PcHS(long seme1, long seme2, long seme1Iniziale, long seme2Iniziale){		
		super();
		this.id = cont;
		cont = (cont + 1) % 3;
		this.gen_iperesp = new GeneratoreIperesp(seme1, seme2, PROBABILITA, PcHS.TEMPO_MEDIO_SERVIZIO, seme1Iniziale, seme2Iniziale);
		this.FIFO = new FIFO();
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job) throws GeneratoreException{
		
		if(this.isOccupato()){
			this.FIFO.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		} else {	
			setJob(job);
			this.setOccupato(true);
			
			return gen_iperesp.getNext(); 
		}
	}

	public Job prelevaJob() throws GeneratoreException{
		if(!this.FIFO.codaVuota()){
			return this.FIFO.prelevaJob();
		} else {
			return null;
		}
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public long[] getNuovoSeme() throws GeneratoreException {
		return gen_iperesp.getProssimoSeme();
	}

	public void setNuovoSeme(long seme1, long seme2, long seme1Iniziale, long seme2Iniziale) {
		this.gen_iperesp = new GeneratoreIperesp(seme1, seme2, 0.6, PcHS.TEMPO_MEDIO_SERVIZIO, seme1Iniziale, seme2Iniziale);
	}

}
