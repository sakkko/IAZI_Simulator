package IAZI_simulator.eventi;

import IAZI_simulator.centri.*;
import IAZI_simulator.entita.*;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FineLAN2 extends Evento{

	public FineLAN2(String nomeEvento, double tempo_fine_evento, int idCentro) {
		super(nomeEvento, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		LAN2 lan2 = imp.getLan2().get(idCentro);
		Job job = lan2.rimuoviJob();
		double next_time;
		double prob;
		
		if (job == null) {
			throw new CentroException("LAN2 " + lan2.getId() + ": centro vuoto");
		}
		
		if (job.getClasse().equals("A")) {
			//con probabilità uniforme scelgo il server a cui passare il job
			prob = imp.getProbabilitaDiramazione();
			next_time = imp.getServerPC().get((int)(prob * 3)).aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
			if (next_time == -1) {
				System.out.println("PcHS " + (int)(prob * 3) + ": job inserito nella coda");
				return;
			} else {
				cal.aggiungiEvento(new FinePcHS("fine_pchs", cal.getClock().getTempo_di_simulazione() + next_time, (int)(prob * 3)));
			}
		} else {
			next_time = imp.getGw2().aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
			if (next_time == -1) {
				System.out.println("GW2: job inserito nella coda");
				return;
			} else {
				cal.aggiungiEvento(new FineGW2("fine_gw2", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			}
		}
	}

}
