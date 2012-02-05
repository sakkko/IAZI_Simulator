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
	private static final double TEMPO_MEDIO_SERVIZIO = 0.002489;//secondi (N = 48)
	private Generatore gen_iperesp;
	private Coda FIFO;
	
	public PcHS(long seme1, long seme2){		
		super();
		this.id = cont;
		cont = (cont + 1) % 3;
		this.gen_iperesp = new GeneratoreIperesp(seme1, seme2, 0.6, PcHS.TEMPO_MEDIO_SERVIZIO);
		this.FIFO = new FIFO();
	}
	
	public PcHS(PcHS pchs) throws GeneratoreException{			
		super(pchs);
		this.id = pchs.id;
		this.gen_iperesp = new GeneratoreIperesp(pchs.gen_iperesp);
		this.FIFO = new FIFO((FIFO)pchs.FIFO);
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job){
		
		if(this.isOccupato()){
			this.FIFO.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		} else {	
			setJob(job);
			this.setOccupato(true);
			
			return gen_iperesp.getNext(); 
		}
	}

	public Job prelevaJob(){
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
	
	public long[] getNuovoSeme() {
		return gen_iperesp.getProssimoSeme();
	}

	public void setGeneratore(Generatore generatore) throws GeneratoreException {
		if (generatore.getClass().equals(GeneratoreIperesp.class)) {
			this.gen_iperesp = generatore;
		} else {
			throw new GeneratoreException("PCHS: tipo generatore non valido");
		}
	}

	public Generatore getGeneratore() {
		return gen_iperesp;
	}
	

}
