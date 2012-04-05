package IAZI_simulator.entita;

public class Clock {
	
	private double tempo_di_simulazione;
	
	public Clock(){
		
		this.tempo_di_simulazione = 0.0;
	}
	
	public void incrementa(double deltaT){
		
		this.tempo_di_simulazione += deltaT;
	}

	public double getTempo_di_simulazione() {
		return tempo_di_simulazione;
	}

	public void setTempo_di_simulazione(double tempoDiSimulazione) {
		tempo_di_simulazione = tempoDiSimulazione;
	}
	
}