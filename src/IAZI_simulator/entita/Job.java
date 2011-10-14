package IAZI_simulator.entita;

public class Job {
	
	private double tempoInizioServizio; //tempo in cui il job entra nel centro
	private String classe; 
	private int id;
	private static int cont;
	
	public Job() {
		
		this.tempoInizioServizio = 0.0;
		this.id = cont;
		cont++;
		this.classe = "A";
	}
	
	public double getTempoInizioServizio() {
		return tempoInizioServizio;
	}
	
	public void setTempoInizioServizio(double tempoInizioServizio) {
		this.tempoInizioServizio = tempoInizioServizio;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public int getId(){
		return id;
	}

}