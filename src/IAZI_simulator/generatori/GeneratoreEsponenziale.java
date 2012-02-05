package IAZI_simulator.generatori;

import IAZI_simulator.exception.GeneratoreException;

public class GeneratoreEsponenziale extends Generatore {

	public GeneratoreEsponenziale(long seme, long modulo, long moltiplicatore, double Ta){		
		super();
		this.genUniforme = new GeneratoreUniforme(seme, modulo, moltiplicatore);
		this.Ta = Ta;		
	}

	public GeneratoreEsponenziale(long seme, double Ta){		
		super();
		this.genUniforme = new GeneratoreUniforme(seme);
		this.Ta = Ta;
	}
	
	public GeneratoreEsponenziale(Generatore gen) throws GeneratoreException {
		if (gen.getClass().equals(GeneratoreEsponenziale.class)) {
			GeneratoreEsponenziale tmp = (GeneratoreEsponenziale)gen;
			this.genUniforme = new GeneratoreUniforme(tmp.genUniforme);
			this.Ta = tmp.getTa();
		} else {
			throw new GeneratoreException("Generatore non valido");
		}
		
	}
	
	public double getTa() {		
		return Ta;
	}
	
	public double getNext(){		
		return (-1) * Ta * Math.log(genUniforme.getNext());		
	} 
	
	public long[] getProssimoSeme() {
		long[] ret = new long[1];
		ret[0] = genUniforme.getProssimoSeme()[0];
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
		GeneratoreEsponenziale other = (GeneratoreEsponenziale) obj;
		if (Double.doubleToLongBits(Ta) != Double.doubleToLongBits(other.Ta))
			return false;
		if (genUniforme == null) {
			if (other.genUniforme != null)
				return false;
		} else if (!genUniforme.equals(other.genUniforme))
			return false;
		return true;
	}



	private GeneratoreUniforme genUniforme;
	private double Ta;
	
}