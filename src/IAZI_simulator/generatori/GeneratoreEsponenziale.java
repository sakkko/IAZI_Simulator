package IAZI_simulator.generatori;

import IAZI_simulator.exception.GeneratoreException;

public class GeneratoreEsponenziale extends Generatore {

	public GeneratoreEsponenziale(long seme, long modulo, long moltiplicatore, double Ta){		
		super();
		this.genUniforme = new GeneratoreUniforme(seme, modulo, moltiplicatore);
		this.Ta = Ta;		
	}

	public GeneratoreEsponenziale(long seme, double Ta, long semeIniziale){		
		super();
		this.genUniforme = new GeneratoreUniforme(seme, semeIniziale);
		this.Ta = Ta;
	}

	public GeneratoreUniforme getGeneratore() {
		return genUniforme;
	}

	public double getTa() {		
		return Ta;
	}
	
	
	public double getNext() throws GeneratoreException{		
		return (-1) * Ta * Math.log(genUniforme.getNext());		
	}
	
	@Override
	public double getVarianza() {
		return Math.pow(Ta, 2);
	}
	
	public long[] getProssimoSeme() throws GeneratoreException {
		long[] ret = new long[1];
		ret[0] = genUniforme.getProssimoSeme()[0];
		return ret;
	}
	
	@Override
	public double getMedia() {
		return Ta;
	}
	
	private GeneratoreUniforme genUniforme;
	private double Ta;
		
}