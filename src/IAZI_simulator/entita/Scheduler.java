package IAZI_simulator.entita;

import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;


public class Scheduler {

	public Scheduler(Impianto impianto) {
		this.impianto = impianto;
		calendario = new Calendario();
	}
	
	public Calendario getCalendario() {
		return calendario;
	}
	
	public Impianto getImpianto() {
		return impianto;
	}
	
	//TEST - CONTINUO A SCHEDULARE EVENTI, SARA' LA ROUTINE EVENTO A TERMINARE IL PROGRAMMA
	public void schedule() throws CentroException, EventoException {
		
		for (; ;) {
			calendario.stampaCalendario();
			calendario.getNextEvent().routineFineEvento(calendario, impianto);
		}
	}

	private Calendario calendario;
	private Impianto impianto;
}
