package IAZI_simulator.generatori;

import IAZI_simulator.exception.GeneratoreException;

public class GeneratoreKerl extends Generatore {
	
	public GeneratoreKerl(long[] seme, int stadi, double Ta, long[] semiIniziali){
		super();
		if(seme.length==stadi){ // un valore di seme per ogni gen. esp. del centro erlangiano
			this.Ta = Ta;
			this.k = stadi;
			this.gen = new GeneratoreEsponenziale[stadi];
			for(int i=0; i<stadi; i++){
				gen[i] = new GeneratoreEsponenziale(seme[i],Ta/stadi, semiIniziali[i]);
			}
		}
		
		else System.out.println("Numero semi diverso dal numero degli stadi");
	}
	
	public double getNext() throws GeneratoreException{
		double t=0;
		for(int i=0; i<gen.length; i++){
			t+=gen[i].getNext();
		}
		
		return t;
	}
	
	public double getTa() {
		return Ta;
	}
	
	@Override
	public double getMedia() {
		return Ta;
	}
	
	public double getVarianza() {
		return Math.pow(Ta, 2) / k;
	}
	
	public int getK() {
		return k;
	}
	
	public GeneratoreEsponenziale[] getGen() {
		return gen;
	}
	
	public long[] getProssimoSeme() throws GeneratoreException {
		long[] ret = new long[k];
		for (int i = 0; i < k; i ++) {
			ret[i] = gen[i].getProssimoSeme()[0];
		}
		return ret;
	}
	
	public long[] getSemi() {
		long[] ret = new long[k];
		
		for (int i = 0; i < k; i ++) {
			ret[i] = gen[i].getGeneratore().getSeme();
		}
		
		return ret;
	}
	
	private double Ta;
	private int k;  //numero degli stadi
	private GeneratoreEsponenziale[] gen;

}