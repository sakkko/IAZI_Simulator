package IAZI_simulator.centri;

import IAZI_simulator.entita.Job;

/* Per ogni centro specifico andrà poi creato come attributo un identificativo unico,
 * un rispettivo generatore per la generazione dei tempi di servizio, e un rispettivo oggetto
 * di tipo coda che sarà assegnato al centro che ne richiede una.  
 */

public abstract class Centro {
	
	//aggiunge un job al centro se è libero, altrimenti lo mette in coda(dove prevista)
	public abstract double aggiungiJob(Job job, double tempoInizioServizio);
	
	//imposta il tempo di inizio servizio del job che si è deciso di iniziare a servire
	public abstract void setInizioServizioJob(double tempoInizioServizio);
	
	//rimuove il job dal centro, dopo che è stato servito
	public abstract Job rimuoviJob();
	
}