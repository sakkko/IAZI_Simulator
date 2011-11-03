package IAZI_simulator.eventi;

import IAZI_simulator.centri.*;
import IAZI_simulator.entita.*;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FineDisk extends Evento {

	public FineDisk(String nomeEvento, double tempo_fine_evento, int idCentro) {
		super(nomeEvento, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		Disk disk = imp.getServerDisk().get(idCentro);
		Job job = disk.rimuoviJob();
		double next_time;
		
		if (job == null) {
			throw new CentroException("Disk " + disk.getId() + ": centro vuoto");
		}
		
		next_time = imp.getServerPC().get(idCentro).aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
		if (next_time == -1) {
			System.out.println("PcHS " + idCentro + ": job inserito nella coda");
			return;
		} else {
			cal.aggiungiEvento(new FinePcHS("fine_pchs", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
		}
		
		job = disk.prelevaJob();
		
		if (job != null) {
			next_time = disk.aggiungiJob(job, cal.getClock().getTempo_di_simulazione());
			if (next_time == -1) {
				throw new CentroException("Disk " + disk.getId() + ": centro occupato");
			} else {
				cal.aggiungiEvento(new FineDisk("fine_disk", cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			}
		}
		
	}

}
