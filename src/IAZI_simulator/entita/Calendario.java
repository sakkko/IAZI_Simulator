package IAZI_simulator.entita;

import java.util.ArrayList;

import IAZI_simulator.eventi.*;
import IAZI_simulator.exception.EventoException;

/* La classe Calendario è implementata come un ArrayList di Eventi. Per ogni tipo di evento
 * il calendario salva il nome e il tempo successivo in cui l'evento avrà luogo. 
 * Viene mantenuta la lista ordinata in modo che il primo elemento della lista sarà sempre
 * quello con tempo minore di tutti e che sarà quidni selezionato dallo scheduler
 */
public class Calendario {
	
	ArrayList<Evento> calendario;
	Clock clock;

	public Calendario(){
		calendario = new ArrayList<Evento>();
		clock = new Clock();
	}
	
	public Calendario(Calendario calendario) {
		this.calendario = new ArrayList<Evento>();
		for (int i = 0; i < calendario.calendario.size(); i ++) {
			if (calendario.calendario.get(i).getClass().equals(FineDisk.class)) {
				this.calendario.add(new FineDisk((FineDisk)calendario.calendario.get(i)));
			} else if (calendario.calendario.get(i).getClass().equals(FineGW1.class)) {
				this.calendario.add(new FineGW1((FineGW1)calendario.calendario.get(i)));
			} else if (calendario.calendario.get(i).getClass().equals(FineGW2.class)) {
				this.calendario.add(new FineGW2((FineGW2)calendario.calendario.get(i)));
			} else if (calendario.calendario.get(i).getClass().equals(FineLAN1.class)) {
				this.calendario.add(new FineLAN1((FineLAN1)calendario.calendario.get(i)));
			} else if (calendario.calendario.get(i).getClass().equals(FineLAN2.class)) {
				this.calendario.add(new FineLAN2((FineLAN2)calendario.calendario.get(i)));
			} else if (calendario.calendario.get(i).getClass().equals(FinePcHC.class)) {
				this.calendario.add(new FinePcHC((FinePcHC)calendario.calendario.get(i)));
			} else if (calendario.calendario.get(i).getClass().equals(FinePcHS.class)) {
				this.calendario.add(new FinePcHS((FinePcHS)calendario.calendario.get(i)));
			} else if (calendario.calendario.get(i).getClass().equals(FineTerminale.class)) {
				this.calendario.add(new FineTerminale((FineTerminale)calendario.calendario.get(i)));
			} else if (calendario.calendario.get(i).getClass().equals(FineWAN.class)) {
				this.calendario.add(new FineWAN((FineWAN)calendario.calendario.get(i)));
			} 			
		}
		this.clock = new Clock(calendario.clock);
	}
	
	public void aggiungiEvento(Evento evento) throws EventoException{		
		boolean aggiunto = false;
		
		if (evento.getTempo_fine_evento() >= clock.getTempo_di_simulazione()) {
			for (int i = 0; i < calendario.size(); i ++){
				if (evento.getTempo_fine_evento() < calendario.get(i).getTempo_fine_evento()) {
					calendario.add(i, evento);
					aggiunto = true;
					break;
				}
			}
			if (!aggiunto) {
				calendario.add(evento);	
			}
		} else{
			throw new EventoException("Tempo dell'evento minore dell'attuale tempo di simulazione");
		}
	}
	
	public Evento getNextEvent(){		
		Evento prossimo_evento = null;
		double tempo_fine_evento;
		
		if (calendario.size() != 0) {
			prossimo_evento = calendario.remove(0);
			tempo_fine_evento = prossimo_evento.getTempo_fine_evento(); 
			this.clock.setTempo_di_simulazione(tempo_fine_evento); 
			return prossimo_evento;
		} else {
			return null;
		}
	}
	
	public void stampaCalendario(){
		
		String s;
		Double t;
		System.out.println("NOME EVENTO" + " -------- " + "TEMPO FINE EVENTO \n" );
		System.out.println();
		
		if(calendario.size()!=0){
			for(int i=0; i<calendario.size(); i++){
				s=calendario.get(i).getNomeEvento();
				t=calendario.get(i).getTempo_fine_evento();
				System.out.println( s + " -------- " + t + "\n");
			}
		} else System.out.println("Al momento non ci sono eventi nel calendario");
	}

	public Clock getClock() {
		return clock;
	}
	
	
	
}