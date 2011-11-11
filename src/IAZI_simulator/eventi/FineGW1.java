package IAZI_simulator.eventi;

import IAZI_simulator.centri.*;
import IAZI_simulator.entita.Calendario;
import IAZI_simulator.entita.Impianto;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FineGW1 extends Evento {

	public FineGW1(String nomeEvento, double tempo_fine_evento, int idCentro) {
		super(nomeEvento, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		GW1 gw1 = imp.getGw1();
		Job job = gw1.rimuoviJob();
		double next_time;
		
		if (job == null) {
			throw new CentroException("GW1 : centro vuoto");			
		}
		
		if (job.getClasse().equals("A")) {			
			next_time = imp.getWan().get(idCentro).aggiungiJob(job);
			cal.aggiungiEvento(new FineWAN("fine_wan", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
		} else {
			next_time = imp.getLan1().get(idCentro).aggiungiJob(job);
			cal.aggiungiEvento(new FineLAN1("fine_lan1", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));			
		}
		
		if ((job = gw1.prelevaJob()) != null) {
			next_time = gw1.aggiungiJob(job);
			if (next_time == -1) {
				throw new CentroException("GW1 : centro occupato");
			} else {
				cal.aggiungiEvento(new FineGW1("fine_gw1", cal.getClock().getTempo_di_simulazione() + next_time, job.getId()));
			}
		}
		
	}

}
