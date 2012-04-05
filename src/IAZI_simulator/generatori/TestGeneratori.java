package IAZI_simulator.generatori;

import IAZI_simulator.exception.GeneratoreException;

public class TestGeneratori {
	
	public void testGeneratoreLAN1() throws GeneratoreException {
		double Ta_A = 0.00016;
		double Ta_B = 0.00008;
		
		GeneratoreIperesp gen_A = new GeneratoreIperesp(12213455, 741629665, 0.3, Ta_A, 12213455, 741629665);
		GeneratoreIperesp gen_B = new GeneratoreIperesp(6598121, 994311221, 0.3, Ta_B, 6598121, 994311221);
		
		System.out.println("\nCentro LAN1");
		System.out.println("Tempo medio di servizio job classe A: " + Ta_A + " sec");
		System.out.println("Tempo medio di servizio job classe B: " + Ta_B + " sec\n");
		System.out.println("Classe A");
		testGeneratoreIperesponenziale(gen_A, 25, 0.0027);
		System.out.println("\nClasse B");
		testGeneratoreIperesponenziale(gen_B, 25, 0.0013);
		
		
	}
	
	public void testGeneratoreLAN2() throws GeneratoreException {
		double Ta_A = 0.00032;
		double Ta_B = 0.00016;
		
		GeneratoreEsponenziale gen_A = new GeneratoreEsponenziale(310931, Ta_A, 310931);
		GeneratoreEsponenziale gen_B = new GeneratoreEsponenziale(9834115, Ta_B, 9834115);
		
		System.out.println("\nCentro LAN2");
		System.out.println("Tempo medio di servizio job classe A: " + Ta_A + " sec");
		System.out.println("Tempo medio di servizio job classe B: " + Ta_B + " sec\n");
		System.out.println("Classe A");
		testGeneratoreEsponenziale(gen_A, 25, 0.004);
		System.out.println("\nClasse B");
		testGeneratoreEsponenziale(gen_B, 25, 0.00184);
			
	}
	
	public void testGeneratorePcHC() throws GeneratoreException {
		double Ta = 2.3333;
		GeneratoreIperesp gen = new GeneratoreIperesp(7129565, 9163999, 0.6, Ta, 7129565, 9163999);
		System.out.println("\nCentro PcHC");
		System.out.println("Tempo medio di servizio: " + Ta + " sec\n");		
		testGeneratoreIperesponenziale(gen, 25, 30);
		
	}
	
	public void testGeneratorePcHS() throws GeneratoreException {
		double Ta = 0.002565;
		GeneratoreIperesp gen = new GeneratoreIperesp(241629807, 1241630643, 0.6, Ta, 241629807, 1241630643);
		System.out.println("\nCentro PcHS");
		System.out.println("Tempo medio di servizio: " + Ta + " sec\n");		
		testGeneratoreIperesponenziale(gen, 25, 0.0315);		
	}
	
	public void testGeneratoreTerm() throws GeneratoreException {
		double Ta = 12.5;
		GeneratoreEsponenziale gen = new GeneratoreEsponenziale(999799999, Ta, 999799999);
		System.out.println("\nCentro Terminale");
		System.out.println("Tempo medio di servizio: " + Ta + " sec\n");		
		testGeneratoreEsponenziale(gen, 25, 192);		
	}
	
	public void testGeneratoreGW1() throws GeneratoreException {
		double Ta = 0.0005;
		GeneratoreIperesp gen = new GeneratoreIperesp(961755033, 712337791, 0.5, Ta, 961755033, 712337791);
		System.out.println("\nCentro GW1");
		System.out.println("Tempo medio di servizio: " + Ta + " sec\n");		
		testGeneratoreIperesponenziale(gen, 25, 0.007);
		
	}
	
	public void testGeneratoreWAN() throws GeneratoreException {
		double Ta = 0.003;
		int K = 4;
		long[] semi = new long[K];
		
		semi[0] = 23805707;
		semi[1] = 71554389;
		semi[2] = 123520113;
		semi[3] = 48727805;
		
		GeneratoreKerl gen = new GeneratoreKerl(semi, K, Ta, semi);
		System.out.println("\nCentro WAN");
		System.out.println("Tempo medio di servizio: " + Ta + " sec\n");		
		testGeneratoreKerlang(gen, 25, 0.0145);
		
	}
	
	public void testGeneratoreGW2() throws GeneratoreException {
		double Ta = 0.0005;
		int K = 6;
		long[] semi = new long[K];
		
		semi[0] = 89686119;
		semi[1] = 66488937;
		semi[2] = 997492585;
		semi[3] = 49696903;
		semi[4] = 596512335;
		semi[5] = 8965761;
		
		GeneratoreKerl gen = new GeneratoreKerl(semi, K, Ta, semi);
		System.out.println("\nCentro GW2");
		System.out.println("Tempo medio di servizio: " + Ta + " sec\n");		
		testGeneratoreKerlang(gen, 25, 0.002);
		
	}
	
	public void testGeneratoreDisk() throws GeneratoreException {
		double Ta = 0.03333;
		int K = 5;
		long[] semi = new long[K];
		
		semi[0] = 87632169;
		semi[1] = 90488217;
		semi[2] = 947411147;
		semi[3] = 49737903;
		semi[4] = 598712289;
		
		GeneratoreKerl gen = new GeneratoreKerl(semi, K, Ta, semi);
		System.out.println("\nCentro Disk");
		System.out.println("Tempo medio di servizio: " + Ta + " sec\n");		
		testGeneratoreKerlang(gen, 25, 0.146);
		
		System.out.println("\nCoda con disciplina RAND");
		testGeneratoreUniforme(new GeneratoreUniforme(1073357, 1073357), 20, 1);
		
	}
	
	private void testGeneratoreUniforme(GeneratoreUniforme gen, int nClassi, double intervallo) throws GeneratoreException {
		System.out.println("Generatore Uniforme");
		System.out.println("Seme: " + gen.getSeme());
		test(gen, nClassi, intervallo);
	}
	
	private void testGeneratoreIperesponenziale(GeneratoreIperesp gen, int nClassi, double intervallo) throws GeneratoreException {
		System.out.println("Generatore Iperesponenziale (p:" + gen.getP() + ")");
		System.out.println("Seme1: " + gen.getSeme1());
		System.out.println("Seme2: " + gen.getSeme2());
		test(gen, nClassi, intervallo);
	}
	
	private void testGeneratoreKerlang(GeneratoreKerl gen, int nClassi, double intervallo) throws GeneratoreException {
		long semi[] = gen.getSemi();
		
		System.out.println("Generatore K-Erlang (K:" + gen.getK() + ")");		
		
		for (int i = 0; i < gen.getK(); i ++) {
			System.out.println("Seme" + (i + 1) + ": " + semi[i]);
		}
		
		test(gen, nClassi, intervallo);
	}
	
	private void testGeneratoreEsponenziale(GeneratoreEsponenziale gen, int nClassi, double intervallo) throws GeneratoreException {
		System.out.println("Generatore Esponenziale");
		System.out.println("Seme: " + gen.getGeneratore().getSeme());
		test(gen, nClassi, intervallo);
	}
	
	private void test(Generatore gen, int nClassi, double intervallo) throws GeneratoreException {
		int[] classi = new int[nClassi];
		double media = 0, varianza = 0; 
		int n = 100000;
		double val[] = new double[n];
		double step = (double)intervallo / nClassi;
		
		for (int i = 0; i < nClassi; i ++) {
			classi[i] = 0;
		}
		
		for (int i = 0; i < n; i ++) {
			val[i] = gen.getNext();
			classi[(int)(val[i] / step)] ++;
			media += val[i];
		}
		
		media /= n;
		
		for (int i = 0; i < n; i ++) {
			varianza += Math.pow(val[i] - media, 2);
		}
		
		varianza /= (n - 1);
		
		System.out.println("\nMedia teorica: " + gen.getMedia() + " sec");
		System.out.println("Varianza teorica: " + gen.getVarianza() + " sec");
		System.out.println("Media campionaria: " + media + " sec");
		System.out.println("Varianza campionaria: " + varianza + " sec");
		
		System.out.println("\nOsservazioni: " + n);
		System.out.println("Intervallo: " + intervallo + " sec");
		System.out.println("Numero classi: " + nClassi);
		System.out.println("Step: " + step + " sec\n");
		for (int i = 0; i < nClassi; i ++) {
			System.out.println((i + 1) + ": " + classi[i]);
		}
		
	}
}
