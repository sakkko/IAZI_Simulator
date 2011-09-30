package IAZI_simulator.generatori;

public class GeneratoreUniforme extends Generatore {
	
	public GeneratoreUniforme(long seme, long modulo, long moltiplicatore) {
		super();
		this.seme = seme;
		this.modulo = modulo;
		this.moltiplicatore = moltiplicatore;
		this.current = seme;
	}
	
	public GeneratoreUniforme(long seme) {
		super();
		this.seme = seme;
		this.modulo = 2147483648L;
		this.moltiplicatore = 1220703125L;
		this.current = seme;
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
		
		current = (moltiplicatore*current)%modulo;
		return current;
	}


	//main di prova
	public static void main(String args[]){
		
		Generatore gen = new GeneratoreEsponenziale(83609, 0.003); //il seme deve essere dispari
		double sum = 0;
		int nrun = 0;
		double app = 0;
		
		for(int i=0; i<1000; i++){
			
			app = gen.getNext();
			System.out.println(app + "\n");
			sum+=app;
			nrun++;
		}
		
		System.out.println("***********");
		System.out.println(" somma = " + sum + ";" + " media = " + (sum/nrun) );
		
		
		
		
	}

	long seme;
	long modulo;
	long moltiplicatore;
	long current;
}




