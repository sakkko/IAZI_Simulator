package IAZI_simulator.eventi;

import IAZI_simulator.centri.LAN1;
import IAZI_simulator.entita.Calendario;
import IAZI_simulator.entita.Impianto;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FineLAN1 extends Evento {

	public FineLAN1(String nomeEvento, double tempo_fine_evento, int idCentro) {
		super(nomeEvento, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		LAN1 lan1 = imp.getLan1().get(idCentro);
		Job job = lan1.rimuoviJob();
		double next_time;
		
		if (job == null) {
			throw new CentroException("LAN1: centro vuoto");
		}
		
		if (job.getClasse().equals("A")) {
			next_time = imp.getGw1().aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
			if (next_time == -1) {
				System.out.println("GW1: job inserito nella coda");
				return;
			} else {
				cal.aggiungiEvento(new FineGW1("fine_gw1", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			}
		
		} else {
			next_time = imp.getClientPC().get(idCentro).aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
			cal.aggiungiEvento(new FinePcHC("fine_pchc", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
		}
		
	}

}
