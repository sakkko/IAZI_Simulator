package IAZI_simulator.eventi;

import IAZI_simulator.entita.*;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;
import IAZI_simulator.centri.*;

public class FinePcHC extends Evento {

	
	public FinePcHC(String nomeEvento, double tempo_fine_evento, int idCentro) {
		super(nomeEvento, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub		
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		PcHC pchc = imp.getClientPC().get(idCentro);
		Job job = pchc.rimuoviJob();
		double next_time;
		double prob;
		
		if (job == null) {
			throw new CentroException("PcHC " + pchc.getId() + ": centro vuoto");
		}
		
		if (job.getClasse().equals("A")) {
			if (job.isNuovo()) {
				//è la prima volta che il job entra nel centro
				job.setNuovo(false);
				next_time = imp.getTerminali().get(idCentro).aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
				cal.aggiungiEvento(new FineTerminale("fine_terminale", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			} else {
				prob = imp.getProbabilitaDiramazione();
				//prob di diramazione, 1/3 vado varso la LAN1, 2/3 torno al terminale
				if (prob > 2.0/3.0) {
					next_time = imp.getLan1().get(idCentro).aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
					cal.aggiungiEvento(new FineLAN1("fine_lan1", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
				} else {
					next_time = imp.getTerminali().get(idCentro).aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
					cal.aggiungiEvento(new FineTerminale("fine_terminale", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
				}
			}
		} else {
			next_time = imp.getTerminali().get(idCentro).aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
			cal.aggiungiEvento(new FineTerminale("fine_terminale", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
		}						
	}

}

