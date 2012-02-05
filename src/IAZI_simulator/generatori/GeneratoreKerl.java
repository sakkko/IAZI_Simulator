package IAZI_simulator.generatori;

import java.util.Arrays;

import IAZI_simulator.exception.GeneratoreException;

public class GeneratoreKerl extends Generatore {

	
	public GeneratoreKerl(long[] seme, int stadi, double Ta){
		super();
		if(seme.length==stadi){ // un valore di seme per ogni gen. esp. del centro erlangiano
			this.Ta = Ta;
			this.k = stadi;
			this.gen = new GeneratoreEsponenziale[stadi];
			for(int i=0; i<stadi; i++){
				gen[i] = new GeneratoreEsponenziale(seme[i],Ta/stadi);
			}
		}
		
		else System.out.println("Numero semi diverso dal numero degli stadi");
	}
	
	public GeneratoreKerl(Generatore gen) throws GeneratoreException{
		if (gen.getClass().equals(GeneratoreKerl.class)) {
			GeneratoreKerl tmp = (GeneratoreKerl)gen;
			this.Ta = tmp.Ta;
			this.k = tmp.k;
			this.gen = new GeneratoreEsponenziale[tmp.gen.length];
			for(int i = 0; i < tmp.gen.length; i ++){
				this.gen[i] = new GeneratoreEsponenziale(tmp.gen[i]);
			}
		} else {
			throw new GeneratoreException("Generatore non valido");
		}
		
		
		
	}
	
	public double getNext(){
		double t = 0;
		for (int i = 0; i < gen.length; i++) {
			t += gen[i].getNext();
		}
		
		return t;
	}
	
	public double getTa() {
		return Ta;
	}
	
	public int getK() {
		return k;
	}
	
	public GeneratoreEsponenziale[] getGen() {
		return gen;
	}
	
	public long[] getProssimoSeme() {
		long[] ret = new long[k];
		for (int i = 0; i < k; i ++) {
			ret[i] = gen[i].getProssimoSeme()[0];
		}
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
		GeneratoreKerl other = (GeneratoreKerl) obj;
		if (Double.doubleToLongBits(Ta) != Double.doubleToLongBits(other.Ta))
			return false;
		if (!Arrays.equals(gen, other.gen))
			return false;
		if (k != other.k)
			return false;
		return true;
	}



	private double Ta;
	private int k;  //numero degli stadi
	private GeneratoreEsponenziale[] gen;

}