package IAZI_simulator.generatori;

public class TestGeneratori {

	public static void main(String[] args) {
		//Generatore gen = new GeneratoreUniforme(12213455);
		//testGenratoreUniforme();
		//TestGeneratori.testGenratoreEsponenziale();
		//TestGeneratori.testGenratoreIperesponenziale();
		TestGeneratori.testGenratoreKerlang();
	}
	
	public static void testGenratoreKerlang() {
		int stadi = 4;
		long semi[] = new long[stadi];
		semi[0] = 8971773;
		semi[1] = 6743115;
		semi[2] = 12213455;
		semi[3] = 777777777;
		
		Generatore gen = new GeneratoreKerl(semi, stadi, 0.05);
		//Generatore gen = new GeneratoreEsponenziale(777777777L, 0.03);
		int nclassi = 50;
		int[] classi = new int[nclassi + 10];
		double media = 0, varianza = 0; 
		int n = 100000;
		double val[] = new double[n];
		double step = 0.005;
		double max = 0;
		
		for (int i = 0; i < n; i ++) {
			val[i] = gen.getNext();
			media += val[i];
			if (val[i] > max) {
				max = val[i];
			}
			
			if (val[i] / step >= nclassi + 10) {
				classi[nclassi + 9] ++;
			} else {
				classi[(int)(val[i] / step)] ++;
			}
		}
		
		media /= n;
		
		for (int i = 0; i < n; i ++) {
			varianza += Math.pow(val[i] - media, 2);
		}
		
		varianza /= n;
		
		for (int i = 0; i < nclassi + 10; i ++) {
			//System.out.println((i * 0.1) + " - " + (double)((i + 1) * 0.01) + ": " + classi[i]);
			System.out.println(classi[i]);				
		}
		
		System.out.println("Media " + media);
		System.out.println("Varianza " + varianza);
		System.out.println("Max " + max);
	}
	
	public static void testGenratoreIperesponenziale() {
		//Generatore gen = new GeneratoreIperesp(12213455, 777777777L, 0.3, 0.03);
		Generatore gen = new GeneratoreEsponenziale(777777777L, 0.03);
		int nclassi = 100;
		int[] classi = new int[nclassi + 5];
		double media = 0, varianza = 0; 
		int n = 100000;
		double val[] = new double[n];
		double step = 0.002;
		double max = 0;
		
		for (int i = 0; i < n; i ++) {
			val[i] = gen.getNext();
			media += val[i];
			if (val[i] > max) {
				max = val[i];
			}

			if (val[i] / step >= nclassi + 5) {
				classi[nclassi + 4] ++;
			} else {
				classi[(int)(val[i] / step)] ++;
			}

		}
		
		media /= n;
		
		for (int i = 0; i < n; i ++) {
			varianza += Math.pow(val[i] - media, 2);
		}
		
		varianza /= n;
		
		for (int i = 0; i < nclassi + 1; i ++) {
			//System.out.println((i * 0.1) + " - " + (double)((i + 1) * 0.01) + ": " + classi[i]);
			System.out.println(classi[i]);				
		}
		
		System.out.println("Media " + media);
		System.out.println("Varianza " + varianza);
		System.out.println("Max " + max);
	}
	
	public static void testGenratoreEsponenziale() {
		Generatore gen = new GeneratoreEsponenziale(1128375, 0.00032);
		
		int[] classi = new int[101];
		double media = 0, varianza = 0; 
		int n = 100000;
		double val[] = new double[n];
		double step = 0.00005;
		
		for (int i = 0; i < n; i ++) {
			val[i] = gen.getNext();
			media += val[i];
			if (val[i] >= 50 * step) {
				classi[50] ++;
			} else {
				classi[(int)(val[i] / step)] ++;
			}
		}
		
		media /= n;
		
		for (int i = 0; i < n; i ++) {
			varianza += Math.pow(val[i] - media, 2);
		}
		
		varianza /= n;
		
		for (int i = 0; i < 101; i ++) {
			//System.out.println((i * 0.1) + " - " + (double)((i + 1) * 0.01) + ": " + classi[i]);
			System.out.println(classi[i]);				
		}
		
		System.out.println("Media " + media);
		System.out.println("Varianza " + varianza);
	}
	
	public static void testGenratoreUniforme() {
		Generatore gen = new GeneratoreUniforme(1073357);
		

		int[] classi = new int[20];
		double media = 0, varianza = 0; 
		int n = 100000;
		double val[] = new double[n];
		double step = 1 / (double)20;
		
		for (int i = 0; i < n; i ++) {
			val[i] = gen.getNext();
			media += val[i];
			classi[(int)(val[i] / step)] ++;
		}
		
		media /= n;
		
		for (int i = 0; i < n; i ++) {
			varianza += Math.pow(val[i] - media, 2);
		}
		
		varianza /= n;
		
		for (int i = 0; i < 20; i ++) {
			//System.out.println((i * 0.1) + " - " + (double)((i + 1) * 0.01) + ": " + classi[i]);
			System.out.println(classi[i]);				
		}
		
		System.out.println("Media " + media);
		System.out.println("Varianza " + varianza);
	}
}
