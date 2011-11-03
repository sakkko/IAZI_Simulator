package IAZI_simulator.eventi;

import IAZI_simulator.centri.*;
import IAZI_simulator.entita.*;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FineWAN extends Evento {

	public FineWAN(String nomeEvento, double tempo_fine_evento, int idCentro) {
		super(nomeEvento, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		WAN wan = imp.getWan().get(idCentro);
		Job job = wan.rimuoviJob();
		double next_time;
		
		if (job == null) {
			throw new CentroException("WAN " + wan.getId() + ": centro vuoto");
		}
		
		if (job.getClasse().equals("A")) {
			next_time = imp.getGw2().aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
			if (next_time == -1) {
				System.out.println("GW2: job inserito nella coda");
				return;
			} else {
				cal.aggiungiEvento(new FineGW2("fine_gw2", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			}
		} else {
			next_time = imp.getGw1().aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
			if (next_time == -1) {
				System.out.println("GW1: job inserito nella coda");
				return;
			} else {
				cal.aggiungiEvento(new FineGW1("fine_gw1", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			}
		}
	}

}
