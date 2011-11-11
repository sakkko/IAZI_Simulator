package IAZI_simulator.entita;

import IAZI_simulator.eventi.Evento;
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
	
	public void schedule() throws CentroException, EventoException {
		Evento evento = calendario.getNextEvent();
		if (evento == null) {
			throw new EventoException("Nessun evento in calendario");
		}
		evento.routineFineEvento(calendario, impianto);
	}

	private Calendario calendario;
	private Impianto impianto;
}
