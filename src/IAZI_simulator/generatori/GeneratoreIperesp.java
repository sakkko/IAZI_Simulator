package IAZI_simulator.generatori;

import IAZI_simulator.exception.GeneratoreException;

public class GeneratoreIperesp extends Generatore {
	
	
	public GeneratoreIperesp(long seme1, long seme2, double probabilita, double Ta){
		super();
		this.Ta = Ta;
		this.p = probabilita;
		this.genUniforme = new GeneratoreUniforme(seme1);
		this.genEsponenziale = new GeneratoreEsponenziale(seme2, 1); // variabile esponenziale con media 1
	}
	
	public GeneratoreIperesp(Generatore gen) throws GeneratoreException{
		if (gen.getClass().equals(GeneratoreIperesp.class)) {
			GeneratoreIperesp tmp = (GeneratoreIperesp)gen;
			this.Ta = tmp.Ta;
			this.p = tmp.p;
			this.genUniforme = new GeneratoreUniforme(tmp.genUniforme);
			this.genEsponenziale = new GeneratoreEsponenziale(tmp.genEsponenziale); // variabile esponenziale con media 1
		} else {
			throw new GeneratoreException("Generatore non valido");
		}
		
	}
	
	
	public double getNext(){		
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

	public GeneratoreUniforme getGenUniforme() {
		return genUniforme;
	}

	public GeneratoreEsponenziale getGenEsponenziale() {
		return genEsponenziale;
	}
	
	public long[] getProssimoSeme() {
		long[] ret = new long[2];
		ret[0] = genUniforme.getProssimoSeme()[0];
		ret[1] = genEsponenziale.getProssimoSeme()[0];
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeneratoreIperesp other = (GeneratoreIperesp) obj;
		if (Double.doubleToLongBits(Ta) != Double.doubleToLongBits(other.Ta))
			return false;
		if (genEsponenziale == null) {
			if (other.genEsponenziale != null)
				return false;
		} else if (!genEsponenziale.equals(other.genEsponenziale))
			return false;
		if (genUniforme == null) {
			if (other.genUniforme != null)
				return false;
		} else if (!genUniforme.equals(other.genUniforme))
			return false;
		if (Double.doubleToLongBits(p) != Double.doubleToLongBits(other.p))
			return false;
		return true;
	}



	private double Ta;
	private double p; //probabilità
	GeneratoreUniforme genUniforme;
	GeneratoreEsponenziale genEsponenziale;

}