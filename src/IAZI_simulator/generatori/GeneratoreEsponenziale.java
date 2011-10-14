package IAZI_simulator.generatori;

public class GeneratoreEsponenziale extends Generatore {
	
	public GeneratoreEsponenziale(long seme, long modulo, long moltiplicatore, double Ta){
		
		super();
		this.genUniforme = new GeneratoreUniforme(seme, modulo, moltiplicatore);
		this.Ta = Ta;		
	}

	public GeneratoreEsponenziale(long seme, double Ta){
		
		super();
		this.genUniforme = new GeneratoreUniforme(seme);
		this.Ta = Ta;
	}

	public GeneratoreUniforme getGeneratore() {
		
		return genUniforme;
	}

	public double getTa() {
		
		return Ta;
	}
	
	public double getNext(){
		
		return (-1) * Ta * Math.log(genUniforme.getNext());
		
	}
	
	
	private GeneratoreUniforme genUniforme;
	private double Ta;
}