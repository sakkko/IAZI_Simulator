package IAZI_simulator.eventi;

import IAZI_simulator.centri.*;
import IAZI_simulator.entita.*;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FineGW2 extends Evento {

	public FineGW2(double tempo_fine_evento, int idCentro) {
		super(Evento.FINE_GW2, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub		
	}
	
	public FineGW2(FineGW2 fineGW2) {
		super(fineGW2);
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		GW2 gw2 = imp.getGw2();
		job = gw2.rimuoviJob();
		double next_time;
		
		if (job == null) {
			throw new CentroException("GW2 : centro vuoto");
		}
		
		if (job.getClasse().equals("A")) {
			next_time = imp.getLan2().get(idCentro).aggiungiJob(job);
			cal.aggiungiEvento(new FineLAN2(cal.getClock().getTempo_di_simulazione() + next_time, idCentro));			
		} else {
			next_time = imp.getWan().get(idCentro).aggiungiJob(job);
			cal.aggiungiEvento(new FineWAN(cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
		}
		
		job = gw2.prelevaJob();
		
		if (job != null) {
			next_time = gw2.aggiungiJob(job);
			if (next_time == -1) {
				throw new CentroException("GW2: centro occupato");
			} else {
				cal.aggiungiEvento(new FineGW2(cal.getClock().getTempo_di_simulazione() + next_time, job.getId()));
			}
		}
		
	}
	
	public String toString() {
		String ret = "fine_gw2 " + super.toString();
		return ret;
	}

}
