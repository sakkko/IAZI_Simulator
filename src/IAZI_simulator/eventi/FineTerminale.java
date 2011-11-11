package IAZI_simulator.eventi;

import IAZI_simulator.centri.Terminale;
import IAZI_simulator.entita.Calendario;
import IAZI_simulator.entita.Impianto;
import IAZI_simulator.entita.Job;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FineTerminale extends Evento {

	public FineTerminale(String nomeEvento, double tempo_fine_evento, int id_centro) {
		super(nomeEvento, tempo_fine_evento, id_centro);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		Terminale term = imp.getTerminali().get(idCentro);
		Job job = term.rimuoviJob();
		double next_time;
		
		if (job == null) {
			throw new CentroException("Terminale " + term.getId() + ": centro vuoto");
		}
		
		if (job.getClasse().equals("B")) {			
			job.setClasse("A");
			job.setNuovo(true);
		}
		
		next_time = imp.getClientPC().get(idCentro).aggiungiJob(job);		
		cal.aggiungiEvento(new FinePcHC("fine_pchc", cal.getClock().getTempo_di_simulazione() + (next_time), idCentro));
	}

}
