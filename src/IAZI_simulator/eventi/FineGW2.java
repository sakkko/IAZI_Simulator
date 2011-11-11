package IAZI_simulator.eventi;

import IAZI_simulator.centri.*;
import IAZI_simulator.entita.*;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FineGW2 extends Evento {

	public FineGW2(String nomeEvento, double tempo_fine_evento, int idCentro) {
		super(nomeEvento, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		GW2 gw2 = imp.getGw2();
		Job job = gw2.rimuoviJob();
		double next_time;
		
		if (job == null) {
			throw new CentroException("GW2 : centro vuoto");
		}
		
		if (job.getClasse().equals("A")) {
			next_time = imp.getLan2().get(idCentro).aggiungiJob(job);
			cal.aggiungiEvento(new FineLAN2("fine_lan2", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));			
		} else {
			next_time = imp.getWan().get(idCentro).aggiungiJob(job);
			cal.aggiungiEvento(new FineWAN("fine_wan", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
		}
		
		job = gw2.prelevaJob();
		
		if (job != null) {
			next_time = gw2.aggiungiJob(job);
			if (next_time == -1) {
				throw new CentroException("GW2: centro occupato");
			} else {
				cal.aggiungiEvento(new FineGW2("fine_gw2", cal.getClock().getTempo_di_simulazione() + next_time, job.getId()));
			}
		}
		
	}

}
