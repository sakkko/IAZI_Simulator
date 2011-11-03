package IAZI_simulator.entita;

import java.util.ArrayList;

import IAZI_simulator.eventi.Evento;
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
	
	public void aggiungiEvento(Evento evento) throws EventoException{		
		boolean aggiunto = false;
		
		if (evento.getTempo_fine_evento() > clock.getTempo_di_simulazione()) {
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