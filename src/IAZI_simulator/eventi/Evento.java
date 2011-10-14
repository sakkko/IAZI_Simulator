package IAZI_simulator.eventi;

/* Gli eventi che vanno presi in considerazione sono:
 * 
 * - FineTerminali (arrivo di un utente);
 * - FinePcClient i-esimo;
 * - FinePcServer i-esimo;
 * - FineAccessoMemoriaServer i-esimo;
 * - FineLan1, FineLan2, FineGateway1, FineGateway2, FineWan;
 * 
 * Ad ogni evento è quindi associato un tempo di fine servizio ed una routine di azione
 */

public abstract class Evento {
	
	protected String nomeEvento; //identificativo di un evento
	protected double tempo_fine_evento;
	
	
	public Evento(String nomeEvento, double tempo_fine_evento){
		
		this.nomeEvento = nomeEvento;
		this.tempo_fine_evento = tempo_fine_evento;
	}
	
	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public double getTempo_fine_evento() {
		return tempo_fine_evento;
	}

	public void setTempo_fine_evento(double tempoFineEvento) {
		tempo_fine_evento = tempoFineEvento;
	}
	
	public abstract void routine();


}