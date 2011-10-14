package IAZI_simulator.generatori;

public class GeneratoreIperesp extends Generatore {
	
	
	public GeneratoreIperesp(long[] seme, double probabilità, double Ta){
		
		super();
		if(seme.length==2){
			this.Ta = Ta;
			this.p = probabilità;
			this.genUniforme = new GeneratoreUniforme(seme[0]);
			this.genEsponenziale = new GeneratoreEsponenziale(seme[1],1); // variabile esponenziale con media 1
		}
		else System.out.println("Numero dei semi inseriti errato");
	}
	
	public double getNext(){
		
		double r = genUniforme.getNext();
		double X = genEsponenziale.getNext();
		
		if(r <= p) 
			return (Ta / (2*p) ) * X;
		else
			return (Ta / (2*(1-p)) ) * X;
	}
	
	public double getTa() {
		return Ta;
	}

	public double getP() {
		return p;
	}

	public GeneratoreUniforme getGenUniforme() {
		return genUniforme;
	}

	public GeneratoreEsponenziale getGenEsponenziale() {
		return genEsponenziale;
	}

	private double Ta;
	private double p; //probabilità
	GeneratoreUniforme genUniforme;
	GeneratoreEsponenziale genEsponenziale;

}