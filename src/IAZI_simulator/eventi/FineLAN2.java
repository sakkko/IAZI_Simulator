package IAZI_simulator.eventi;

import IAZI_simulator.centri.*;
import IAZI_simulator.entita.*;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;
import IAZI_simulator.exception.GeneratoreException;

public class FineLAN2 extends Evento{

	public FineLAN2(double tempo_fine_evento, int idCentro) {
		super(Evento.FINE_LAN2, tempo_fine_evento, idCentro);
		// TODO Auto-generated constructor stub
		
	}

	public FineLAN2(FineLAN2 fineLAN2) {
		super(fineLAN2);
	}
	
	@Override
	public void routineFineEvento(Calendario cal, Impianto imp) throws CentroException, EventoException, GeneratoreException {
		// TODO Auto-generated method stub
		LAN2 lan2 = imp.getLan2().get(idCentro);
		job = lan2.rimuoviJob();
		double next_time;
		double prob;
		
		if (job == null) {
			throw new CentroException("LAN2 " + lan2.getId() + ": centro vuoto");
		}
		
		if (job.getClasse().equals("A")) {
			//con probabilità uniforme scelgo il server a cui passare il job
			prob = imp.getProbabilitaDiramazione();
			job.setTempoFineLAN2a(cal.getClock().getTempo_di_simulazione());
			next_time = imp.getServerPC().get((int)(prob * 3)).aggiungiJob(job);
			if (next_time == -1) {
				//System.out.println("PcHS " + (int)(prob * 3) + ": job inserito nella coda");
				return;
			} else {
				cal.aggiungiEvento(new FinePcHS(cal.getClock().getTempo_di_simulazione() + next_time, (int)(prob * 3)));
			}
		} else {
			job.setTempoFineLAN2b(cal.getClock().getTempo_di_simulazione());
			next_time = imp.getGw2().aggiungiJob(job);
			if (next_time == -1) {
				//System.out.println("GW2: job inserito nella coda");
				return;
			} else {
				cal.aggiungiEvento(new FineGW2(cal.getClock().getTempo_di_simulazione() + next_time, idCentro));
			}
		}
	}
	
	public String toString() {
		String ret = "fine_lan2 " + super.toString();
		return ret;
	}

}
