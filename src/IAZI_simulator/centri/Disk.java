package IAZI_simulator.centri;

import IAZI_simulator.code.RAND;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreKerl;

public class Disk extends Centro{
	
	public static final double TEMPO_MEDIO_SERVIZIO = 0.033333;
	public static final int K = 5;
	private static int cont = 0;	

	private int id;
	private Generatore gen_5erlang;
	private RAND RAND;
	
	public Disk(long[] semiGeneratore, long semeCoda, long[] semiIniziali, long semeCodaIniziale) throws GeneratoreException {
		if (semiGeneratore.length != K) {
			throw new GeneratoreException("Disk " + cont + ": numero di semi non valido");
		}
		
		this.id = cont;
		cont = (cont + 1) % 3;			
		this.gen_5erlang = new GeneratoreKerl(semiGeneratore, semiGeneratore.length, TEMPO_MEDIO_SERVIZIO, semiIniziali);
		this.RAND = new RAND(semeCoda, semeCodaIniziale);
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job) throws GeneratoreException{		
		if(this.isOccupato()){
			this.RAND.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		} else {	
			setJob(job);
			setOccupato(true);
			
			return gen_5erlang.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
		}
										
	}

	public Job prelevaJob() throws GeneratoreException{		
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
	
	public long[] getNuovoSeme() throws GeneratoreException {
		return gen_5erlang.getProssimoSeme();
	}
	
	public long[] getNuovoSemeCoda() throws GeneratoreException {
		long[] ret = new long[1];
		ret[0] = RAND.getNuovoSeme();
		return ret;
	}

	public void setNuovoSeme(long seme1, long seme2, long seme3, long seme4, long seme5,
			long seme1Iniziale, long seme2Iniziale, long seme3Iniziale, long seme4Iniziale, long seme5Iniziale) {
		long semi[] = new long[5];
		long semiIniziali[] = new long[5];
		semi[0] = seme1;
		semi[1] = seme2;
		semi[2] = seme3;
		semi[3] = seme4;
		semi[4] = seme5;
		semiIniziali[0] = seme1Iniziale;
		semiIniziali[1] = seme2Iniziale;
		semiIniziali[2] = seme3Iniziale;
		semiIniziali[3] = seme4Iniziale;
		semiIniziali[4] = seme5Iniziale;
		this.gen_5erlang = new GeneratoreKerl(semi, semi.length, Disk.TEMPO_MEDIO_SERVIZIO, semiIniziali);
	}
	
	public void setNuovoSemeCoda(long seme, long semeCodaIniziale) {
		RAND.setNuovoSeme(seme, semeCodaIniziale);
	}

}

	

