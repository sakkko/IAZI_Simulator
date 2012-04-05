package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreKerl;

public class WAN extends Centro {

	private static int cont = 0;
	private int id;
	public static final double TEMPO_MEDIO_SERVIZIO = 0.003;//secondi
	public static final int K = 4;
	
	private Generatore gen_4erlang;
	
	public WAN(long[] seme, long[] semiIniziali, int nClient) throws GeneratoreException {
		super();
		if (seme.length != K) {
			throw new GeneratoreException("WAN " + cont + ": numero di semi non valido");
		}
		
		this.id = cont;
		cont = (cont + 1) % nClient;
		this.gen_4erlang = new GeneratoreKerl(seme, K, WAN.TEMPO_MEDIO_SERVIZIO, semiIniziali);
	}
	
	//aggiunge un job al centro 
	public double aggiungiJob(Job job) throws GeneratoreException{		
		setJob(job);
		return gen_4erlang.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public long[] getNuovoSeme() throws GeneratoreException {
		return gen_4erlang.getProssimoSeme();
	}
	
	public void setNuovoSeme(long seme1, long seme2, long seme3, long seme4,
			long seme1Iniziale, long seme2Iniziale, long seme3Iniziale, long seme4Iniziale) {
		long seme[] = new long[4];
		long semiIniziali[] = new long[4];
		seme[0] = seme1;
		seme[1] = seme2;
		seme[2] = seme3;
		seme[3] = seme4;
		semiIniziali[0] = seme1Iniziale;
		semiIniziali[1] = seme2Iniziale;
		semiIniziali[2] = seme3Iniziale;
		semiIniziali[3] = seme4Iniziale;
		this.gen_4erlang = new GeneratoreKerl(seme, 4, WAN.TEMPO_MEDIO_SERVIZIO, semiIniziali);
	}

}
