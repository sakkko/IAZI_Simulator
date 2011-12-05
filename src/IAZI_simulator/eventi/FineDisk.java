package IAZI_simulator.eventi;

import IAZI_simulator.centri.*;
import IAZI_simulator.entita.*;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;

public class FineDisk extends Evento {

	public FineDisk(double tempo_fine_evento, int idCentro) {
		super(Evento.FINE_DISK, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException {
		// TODO Auto-generated method stub
		Disk disk = imp.getServerDisk().get(idCentro);
		job = disk.rimuoviJob();
		double next_time;
		
		if (job == null) {
			throw new CentroException("Disk " + disk.getId() + ": centro vuoto");
		}
		
		next_time = imp.getServerPC().get(idCentro).aggiungiJob(job);
		if (next_time == -1) {
			//System.out.println("PcHS " + idCentro + ": job inserito nella coda");
			return;
		} else {
			cal.aggiungiEvento(new FinePcHS(cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
		}
		
		job = disk.prelevaJob();
		
		if (job != null) {
			next_time = disk.aggiungiJob(job);
			if (next_time == -1) {
				throw new CentroException("Disk " + disk.getId() + ": centro occupato");
			} else {
				cal.aggiungiEvento(new FineDisk(cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			}
		}
		
	}
	
	public String toString() {
		String ret = "fine_disk " + super.toString();
		return ret;
	}

}
