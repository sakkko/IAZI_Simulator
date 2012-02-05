package IAZI_simulator.eventi;

import IAZI_simulator.entita.Calendario;
import IAZI_simulator.entita.Impianto;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

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
	
	public final static String FINE_DISK = "fine_disk";
	public final static String FINE_GW1 = "fine_gw1";
	public final static String FINE_GW2 = "fine_gw2";
	public final static String FINE_LAN1 = "fine_lan1";
	public final static String FINE_LAN2 = "fine_lan2";
	public final static String FINE_PCHC = "fine_pchc";
	public final static String FINE_PCHS = "fine_pchs";
	public final static String FINE_TERMINALE = "fine_terminale";
	public final static String FINE_WAN = "fine_wan";
	
	protected String nomeEvento; //identificativo di un evento
	protected double tempo_fine_evento;
	protected int idCentro;
	protected Job job;
	
	
	public abstract void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException;
	
	public Evento(String nomeEvento, double tempo_fine_evento, int idCentro) {		
		this.nomeEvento = nomeEvento;
		this.tempo_fine_evento = tempo_fine_evento;
		this.idCentro = idCentro;
	}
	
	public Evento(Evento evento) {
		this.nomeEvento = evento.nomeEvento;
		this.tempo_fine_evento = evento.tempo_fine_evento;
		this.idCentro = evento.idCentro;
		if (evento.job != null) {
			this.job = new Job(evento.job);
		} else {
			this.job = null;
		}
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
	
	public int getIdCentro() {
		return idCentro;
	}

	public Job getJob() {
		return job;
	}	
	
	public String toString() {
		String ret = " - " + tempo_fine_evento + " - centro: " + idCentro;
		return ret;
	}
	

}