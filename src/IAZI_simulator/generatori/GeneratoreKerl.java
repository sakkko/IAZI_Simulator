package IAZI_simulator.generatori;

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
	
	public double getNext(){
		double t=0;
		for(int i=0; i<gen.length; i++){
			t+=gen[i].getNext();
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
	
	private double Ta;
	private int k;  //numero degli stadi
	private GeneratoreEsponenziale[] gen;

}