package IAZI_simulator.centri;

import IAZI_simulator.code.Coda;
import IAZI_simulator.code.RAND;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreKerl;

public class Disk extends Centro{
	
	private static final double TEMPO_MEDIO_SERVIZIO = 0.0033; //(non se se è corretto questo valore!!)
	private static int cont = 0;	

	private int id;
	private Generatore gen_5erlang;
	private Coda RAND;
	
	public Disk(long[] semiGeneratore, long semeCoda) throws GeneratoreException {
		if (semiGeneratore.length != 5) {
			throw new GeneratoreException("Disk " + cont + ": numero di semi non valido");
		}
		
		this.id = cont;
		cont ++;			
		this.gen_5erlang = new GeneratoreKerl(semiGeneratore, semiGeneratore.length, TEMPO_MEDIO_SERVIZIO);
		this.RAND = new RAND(semeCoda);
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job, double tempoInizioServizio){		
		if(this.isOccupato()){
			this.RAND.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		} else {	
			setJob(job);
			this.setInizioServizioJob(tempoInizioServizio);
			setOccupato(true);
			
			return gen_5erlang.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
		}
										
	}

	public Job prelevaJob(){		
		if (!this.RAND.codaVuota()) {
			return this.RAND.prelevaJob();
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
	

}

	

