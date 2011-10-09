package IAZI_simulator;

import IAZI_simulator.generatori.*;

public class IAZI_Simulator {

	/**
	 * @param args
	 */
	public static void main(String args[]){
		
		//il seme deve essere dispari
		long[] semi = new long[2];
		semi[0]=83609;
		semi[1]=12301;
				
		
		Generatore gen = new GeneratoreIperesp(semi, 0.3, 0.003); 
		double sum = 0;
		int nrun = 0;
		double app = 0;
		
		for (int i=0; i<1000; i++) {
			
			app = gen.getNext();
			System.out.println(app + "\n");
			sum+=app;
			nrun++;
		}
		
		System.out.println("***********");
		System.out.println(" somma = " + sum + ";" + " media = " + (sum/nrun) );
		
	}
}
