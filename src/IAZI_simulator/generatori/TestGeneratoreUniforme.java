package IAZI_simulator.generatori;

public class TestGeneratoreUniforme {
	
	public static boolean ChiQuadroTest(Generatore gen) {
		int nrun = 10000;
		int numeroClassi = 20;
		int[] classi = new int[numeroClassi];
		double numeroGenerato;
		int k=0;
		int sospetti = 0;
		double V;
		
		for (int i = 0; i < 3; i ++) {
			k = 0;
			while (k < numeroClassi) {
				classi[k] = 0;
				k ++;
			}
			
			for (int j = 0; j < nrun; j ++) {
				numeroGenerato = gen.getNext();
				classi[(int)(numeroGenerato * numeroClassi)] ++;
			}
			V = 0;
			for (int j = 0; j < numeroClassi; j ++) {
				V += Math.pow(classi[j] - (nrun / numeroClassi), 2) / (1.0/numeroClassi);
			}
			
			V /= nrun;
			
			if (V < 7.63 || V > 36.19) {
				//RIGETTO
				return false;
			}
			
			if (V < 11.7 || V > 27.2) {
				//SOSPETTO
				sospetti ++;
			}
		}
		
		if (sospetti < 2) {
			return true;
		} else {
			return false;
		}
		
		
	}
}