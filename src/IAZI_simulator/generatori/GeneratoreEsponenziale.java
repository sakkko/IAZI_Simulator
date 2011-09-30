package IAZI_simulator.generatori;

public class GeneratoreEsponenziale extends Generatore {
	
	public GeneratoreEsponenziale(long seme, long modulo, long moltiplicatore, double Ta){
		
		super();
		this.generatore = new GeneratoreUniforme(seme, modulo, moltiplicatore);
		this.Ta = Ta;		
	}

	public GeneratoreEsponenziale(long seme, double Ta){
		
		super();
		this.generatore = new GeneratoreUniforme(seme);
		this.Ta = Ta;
	}

	public GeneratoreUniforme getGeneratore() {
		
		return generatore;
	}

	public double getTa() {
		
		return Ta;
	}
	
	public double getNext(){
		
		return (-1) * Ta * Math.log(generatore.getNext());
		
	}
	
	
	private GeneratoreUniforme generatore;
	private double Ta;



}
