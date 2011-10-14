package IAZI_simulator.generatori;

public class TestGeneratoreUniforme {
	
	public static void main(String[] args){  // chiQuadro Test
		
		int nrun = 1000000; //num. osservazioni, deve essere minore del periodo e maggiore di 5volte del numero delle classi
		
		int numero_classi = 20;
		int[] classi = new int[numero_classi];
		
		int k=0;
		
		while( k < numero_classi){
			classi[k]=0;
			k++;
		}
		
		double prob_attesa = (double)nrun/numero_classi; //valore atteso dei numeri in ciascun intervallo
		long[] semi = new long[3];
		semi[0]=555555557;
		semi[1]=12213455;
		semi[2]=1073357;
		
		//Un test chiQuadro è fatto almeno 3 volte su differenti sequenze di osservazioni e se almeno due
		//dei tre risultati sono sospetti, i numeri non sono considerati sufficientemente random
		
		for(int z=0; z<3; z++){
			
			System.out.println( " ******** SEQUENZA NUMERO " + z + ": ********* \n");

			
			Generatore g = null;
			g = new GeneratoreUniforme(semi[z]);

			for(int i=0; i<nrun; i++){
			
				double numgenerato= g.getNext();
				classi[(int)(numero_classi*numgenerato)]++; //proprietà di distribuzione degli interi tra 0 e 29
			}
		
			double V = 0; //variabile di distribuzione
		
			for(int j=0; j<numero_classi; j++){
			
				V += Math.pow(classi[j] - (prob_attesa), 2);		
		   
			System.out.println("-----"+((double)j/20)+"-"+((double)(j+1)/20)+"-----");
			System.out.println("contatore-->"+classi[j]);
			System.out.println(" V = " + (double)(V/prob_attesa) + "\n");
			}


		}
	}
}