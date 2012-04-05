package IAZI_simulator.generatori;

import IAZI_simulator.exception.GeneratoreException;

public class GeneratoreIperesp extends Generatore {
	
	public GeneratoreIperesp(long seme1, long seme2, double probabilita, double Ta, long seme1Iniziale, long seme2Iniziale){
		super();
		this.Ta = Ta;
		this.p = probabilita;
		this.genUniforme = new GeneratoreUniforme(seme1, seme1Iniziale);
		this.genEsponenziale = new GeneratoreEsponenziale(seme2,1, seme2Iniziale); // variabile esponenziale con media 1
	}
	
	public double getNext() throws GeneratoreException{		
		double r = genUniforme.getNext();
		double X = genEsponenziale.getNext();
		
		if(r <= p) 
			return (Ta / (2*p) ) * X;
		else
			return (Ta / (2*(1-p)) ) * X;
	}
	
	public double getTa() {
		return Ta;
	}

	public double getP() {
		return p;
	}

	@Override
	public double getMedia() {
		return Ta;
	}

	@Override
	public double getVarianza() {
		double alpha = (1 / (2 * p * (1 - p))) - 1;
		return alpha * Math.pow(Ta, 2);
	}
	
	public GeneratoreUniforme getGenUniforme() {
		return genUniforme;
	}

	public GeneratoreEsponenziale getGenEsponenziale() {
		return genEsponenziale;
	}
	
	public long[] getProssimoSeme() throws GeneratoreException {
		long[] ret = new long[2];
		ret[0] = genUniforme.getProssimoSeme()[0];
		ret[1] = genEsponenziale.getProssimoSeme()[0];
		return ret;
	}
	
	public long getSeme1() {
		return genUniforme.getSeme();
	}
	
	public long getSeme2() {
		return genEsponenziale.getGeneratore().getSeme();
	}

	private double Ta;
	private double p; //probabilità
	GeneratoreUniforme genUniforme;
	GeneratoreEsponenziale genEsponenziale;

}