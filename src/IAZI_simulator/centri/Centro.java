package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;

/* Per ogni centro specifico andrà poi creato come attributo un identificativo unico,
 * un rispettivo generatore per la generazione dei tempi di servizio, e un rispettivo oggetto
 * di tipo coda che sarà assegnato al centro che ne richiede una.  
 */

public abstract class Centro {
	
	protected String nomeCentro;
	protected boolean occupato;
	//protected Generatore generatore;
	//protected Code coda;
	
	public Centro(String nomeCentro){
		
		this.nomeCentro = nomeCentro;		
	}
	
	public String getNomeCentro() {
		return nomeCentro;
	}

	public void setNomeCentro(String nomeCentro) {
		this.nomeCentro = nomeCentro;
	}

	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}

	public abstract void getTempoDiServizio();
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda
	public abstract double aggiungiJob(Job job, double tempoInizioServizio);
	
	//preleva il job dalla coda
	public abstract double prelevaJob(Job job);
	
	
	//imposta il tempo di inizio servizio del job che si è deciso di iniziare a servire
	public void setInizioServizio(Job job, double tempoInizioServizio){
		
		job.setTempoInizioServizio(tempoInizioServizio);
	}
	
	//rimuove il job dal centro, dopo che è stato servito
	public abstract Job rimuoviJob(Job job);
	
}