package IAZI_simulator.eventi;

import IAZI_simulator.centri.*;
import IAZI_simulator.entita.*;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FinePcHS extends Evento {

	public FinePcHS(double tempo_fine_evento, int idCentro) {
		super(Evento.FINE_PCHS, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub
	}
	
	public FinePcHS(FinePcHS finePcHS) {
		super(finePcHS);
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		PcHS pchs = imp.getServerPC().get(idCentro);
		job = pchs.rimuoviJob();
		double next_time;
		double prob;
		
		if (job == null) {
			throw new CentroException("PcHS " + pchs.getId() + ": centro vuoto");
		}
		
		prob = imp.getProbabilitaDiramazione();
		//prob di diramazione, 1/195 vado verso LAN2, 194/195 torno al disk
		if (prob < 1.0/195.0) {
			job.setClasse("B");
			next_time = imp.getLan2().get(job.getId()).aggiungiJob(job);
			cal.aggiungiEvento(new FineLAN2(cal.getClock().getTempo_di_simulazione() + next_time, job.getId()));
		} else {
			next_time = imp.getServerDisk().get(idCentro).aggiungiJob(job);
			if (next_time == -1) {
				//System.out.println("Disk " + idCentro + ": job inserito nella coda");
				return;
			} else {
				cal.aggiungiEvento(new FineDisk(cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			}
		}
		
		job = pchs.prelevaJob();
		
		if (job != null) {
			next_time = pchs.aggiungiJob(job);
			if (next_time == -1) {
				throw new CentroException("PcHS " + pchs.getId() + ": centro occupato");
			} else {
				cal.aggiungiEvento(new FinePcHS(cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			}
		}
		
	}
	
	public String toString() {
		String ret = "fine_pchs " + super.toString();
		return ret;
	}

}
