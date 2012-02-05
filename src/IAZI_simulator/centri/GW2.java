package IAZI_simulator.centri;

import IAZI_simulator.code.Coda;
import IAZI_simulator.code.LIFO;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreKerl;

public class GW2 extends Centro {

	//non uso un id per il centro GW perchè sarà un centro unico per l'impianto, dotato di uan coda
	//che gestisce l'arrivo di piu carichi contemporaneamente
	private static final double TEMPO_MEDIO_SERVIZIO = 0.0005;//secondi
	private static final int N_STADI = 6;
	
	private Generatore gen_6erlang;
	private Coda LIFO;
	
	public GW2(long[] semi) throws GeneratoreException {			
		if (semi.length != N_STADI) {
			throw new GeneratoreException("GW2: numero di semi non valido");
		}
		
		this.gen_6erlang = new GeneratoreKerl(semi, semi.length, GW2.TEMPO_MEDIO_SERVIZIO);
		this.LIFO = new LIFO();
	}
	
	public GW2(GW2 gw2) throws GeneratoreException {			
		super(gw2);
		this.gen_6erlang = new GeneratoreKerl(gw2.gen_6erlang);
		this.LIFO = new LIFO((LIFO)gw2.LIFO);
	}
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public double aggiungiJob(Job job){
		
		if (this.isOccupato()){
			this.LIFO.inserisciJob(job);
			return -1; //il valore -1 afferma che il centro è occupato
		} else	{
			setJob(job);
			setOccupato(true);			
			return gen_6erlang.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
		}
	}


	public Job prelevaJob(){		
		if (!this.LIFO.codaVuota()) {
			return this.LIFO.prelevaJob();
		} else {
			return null;
		}
	}
	
	public long[] getNuovoSeme() {
		return gen_6erlang.getProssimoSeme();
	}
	
	public void setGeneratore(Generatore generatore) throws GeneratoreException {
		if (generatore.getClass().equals(GeneratoreKerl.class)) {
			if (((GeneratoreKerl)generatore).getK() != N_STADI) {
				throw new GeneratoreException("GW2: numero di stadi del generatore non valido");
			}
			this.gen_6erlang = generatore;
		} else {
			throw new GeneratoreException("GW2: tipo generatore non valido");
		}
	}
	
	public Generatore getGeneratore() {
		return gen_6erlang;
	}

}
