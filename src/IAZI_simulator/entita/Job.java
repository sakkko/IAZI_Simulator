package IAZI_simulator.entita;

public class Job {
	
	private double tempoInizioServizio; //tempo in cui il job entra nel centro
	private double tempoFineLAN2a;
	private double tempoFineLAN2b;
	private String classe; 
	private int id;
	private boolean nuovo;
	private static int cont = 0;
	
	public Job(int nClient) {
		this.tempoInizioServizio = 0.0;
		this.id = cont;
		cont = (cont + 1) % nClient;
		this.classe = "A";
		this.nuovo = true;
		tempoFineLAN2a = 0;
		tempoFineLAN2b = 0;
	}
	
	public Job(Job job) {
		this.tempoInizioServizio = job.tempoInizioServizio;
		this.id = job.id;
		this.classe = job.classe;
		this.nuovo = job.nuovo;
		tempoFineLAN2a = job.tempoFineLAN2a;
		tempoFineLAN2b = job.tempoFineLAN2b;
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

	public boolean isNuovo() {
		return nuovo;
	}

	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}

	public double getTempoFineLAN2a() {
		return tempoFineLAN2a;
	}

	public double getTempoFineLAN2b() {
		return tempoFineLAN2b;
	}

	public void setTempoFineLAN2a(double tempoFineLAN2a) {
		this.tempoFineLAN2a = tempoFineLAN2a;
	}

	public void setTempoFineLAN2b(double tempoFineLAN2b) {
		this.tempoFineLAN2b = tempoFineLAN2b;
	}
	
	
	
	

}