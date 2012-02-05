package IAZI_simulator.centri;

import IAZI_simulator.IAZI_Simulator;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.Generatore;
import IAZI_simulator.generatori.GeneratoreKerl;

public class WAN extends Centro {

	private static int cont = 0;
	private int id;
	private static final double TEMPO_MEDIO_SERVIZIO = 0.003;//secondi
	private static final int N_STADI = 4;
	private Generatore gen_4erlang;
	
	public WAN(long[] seme) throws GeneratoreException {
		super();
		if (seme.length != N_STADI) {
			throw new GeneratoreException("WAN " + cont + ": numero di semi non valido");
		}
		
		this.id = cont;
		cont = (cont + 1) % IAZI_Simulator.N;
		this.gen_4erlang = new GeneratoreKerl(seme, 4, WAN.TEMPO_MEDIO_SERVIZIO);
	}
	
	public WAN(WAN wan) throws GeneratoreException {		
		super(wan);
		this.id = wan.id;
		this.gen_4erlang = new GeneratoreKerl(wan.gen_4erlang);
	}
	
	//aggiunge un job al centro 
	public double aggiungiJob(Job job){		
		setJob(job);
		return gen_4erlang.getNext(); //ritorno il prossimo tempo in cui il centro sarà pronto peer un nuovo servizio
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public long[] getNuovoSeme() {
		return gen_4erlang.getProssimoSeme();
	}
	
	public Generatore getGeneratore() {
		return gen_4erlang;
	}
	
	public void setGeneratore(Generatore generatore) throws GeneratoreException {
		if (generatore.getClass().equals(GeneratoreKerl.class)) {
			if (((GeneratoreKerl)generatore).getK() != N_STADI) {
				throw new GeneratoreException("WAN: numero di stadi del generatore non valido");
			}
			this.gen_4erlang = generatore;
		} else {
			throw new GeneratoreException("WAN: tipo generatore non valido");
		}
	}
	

}
