package IAZI_simulator.centri;

import IAZI_simulator.code.Coda;
import IAZI_simulator.code.LIFO;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreKerl;

public class GW2 extends Centro {

	public static final double TEMPO_MEDIO_SERVIZIO = 0.0005;//secondi
	public static final int K = 6;
	
	private Generatore gen_6erlang;
	private Coda LIFO;
	
	public GW2(long[] semi, long[] semiIniziali) throws GeneratoreException {			
		if (semi.length != K) {
			throw new GeneratoreException("GW2: numero di semi non valido");
		}
		
		this.gen_6erlang = new GeneratoreKerl(semi, semi.length, GW2.TEMPO_MEDIO_SERVIZIO, semiIniziali);
		this.LIFO = new LIFO();
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job) throws GeneratoreException{
		
		if (this.isOccupato()){
			this.LIFO.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		} else	{
			setJob(job);
			setOccupato(true);			
			return gen_6erlang.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
		}
	}


	public Job prelevaJob() throws GeneratoreException{		
		if (!this.LIFO.codaVuota()) {
			return this.LIFO.prelevaJob();
		} else {
			return null;
		}
	}
	
	public long[] getNuovoSeme() throws GeneratoreException {
		return gen_6erlang.getProssimoSeme();
	}
	
	public void setNuovoSeme(long seme1, long seme2, long seme3, long seme4, long seme5, long seme6,
			long seme1Iniziale, long seme2Iniziale, long seme3Iniziale, long seme4Iniziale, long seme5Iniziale, long seme6Iniziale) {
		long semi[] = new long[6];
		long semiIniziali[] = new long[6];
		semi[0] = seme1;
		semi[1] = seme2;
		semi[2] = seme3;
		semi[3] = seme4;
		semi[4] = seme5;
		semi[5] = seme6;
		semiIniziali[0] = seme1Iniziale;
		semiIniziali[1] = seme2Iniziale;
		semiIniziali[2] = seme3Iniziale;
		semiIniziali[3] = seme4Iniziale;
		semiIniziali[4] = seme5Iniziale;
		semiIniziali[5] = seme6Iniziale;
		this.gen_6erlang = new GeneratoreKerl(semi, semi.length, GW2.TEMPO_MEDIO_SERVIZIO, semiIniziali);
	}

}
