package IAZI_simulator.generatori;

import IAZI_simulator.exception.GeneratoreException;

public class GeneratoreUniforme extends Generatore {
	
	public GeneratoreUniforme(long seme, long modulo, long moltiplicatore) {
		super();
		this.seme = seme;
		this.modulo = modulo;
		this.moltiplicatore = moltiplicatore;
		this.current = seme;
		this.nGenerati = 0;
	}
	
	public GeneratoreUniforme(long seme) {
		super();
		this.seme = seme;
		this.modulo = 2147483648L;
		this.moltiplicatore = 1220703125L;
		this.current = seme;
	}
	
	public GeneratoreUniforme(Generatore gen) throws GeneratoreException {
		if (gen.getClass().equals(GeneratoreUniforme.class)) {
			GeneratoreUniforme tmp = (GeneratoreUniforme)gen;
			this.seme = tmp.getSeme();
			this.modulo = tmp.getModulo();
			this.moltiplicatore = tmp.getMoltiplicatore();
			this.current = tmp.current;
			this.nGenerati = tmp.nGenerati;
		} else {
			throw new GeneratoreException("Generatore non valido");
		}
		
	}
	
	public double getNext() //valore compreso tra 0 e 1
	{
		return (double)getLong()/(double)modulo;
	}
	
	public long getSeme() {
		return seme;
	}

	public void setSeme(long seme) {
		this.seme = seme;
	}

	public long getModulo() {
		return modulo;
	}

	public void setModulo(long modulo) {
		this.modulo = modulo;
	}

	public long getMoltiplicatore() {
		return moltiplicatore;
	}

	public void setMoltiplicatore(long moltiplicatore) {
		this.moltiplicatore = moltiplicatore;
	}
	
	private long getLong(){		
		if (nGenerati == 0) {
			nGenerati ++;
			return seme;
		} else {
			current = (moltiplicatore * current) % modulo;
			return current;
		}
	}
	
	public long[] getProssimoSeme() {
		long[] ret = new long[1];
		ret[0] = current;
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
		GeneratoreUniforme other = (GeneratoreUniforme) obj;
		if (current != other.current)
			return false;
		if (modulo != other.modulo)
			return false;
		if (moltiplicatore != other.moltiplicatore)
			return false;
		if (nGenerati != other.nGenerati)
			return false;
		if (prossimaBase != other.prossimaBase)
			return false;
		if (seme != other.seme)
			return false;
		return true;
	}



	int nGenerati;
	long seme;
	long modulo;
	long moltiplicatore;
	long current;
	long prossimaBase;
}



