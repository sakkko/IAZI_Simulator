package IAZI_simulator;

import java.util.ArrayList;

import IAZI_simulator.entita.Impianto;
import IAZI_simulator.entita.Job;
import IAZI_simulator.entita.Scheduler;
import IAZI_simulator.eventi.FineLAN1;
import IAZI_simulator.eventi.FinePcHC;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.*;

public class IAZI_Simulator {
	
	/**
	 * @param args
	 */
	public static void main(String args[]){		
		IAZI_Simulator iazi = new IAZI_Simulator();
		int n0 = 0;
		
		try {
			n0 = iazi.runStabilizzazione();
			iazi.runStatistici(n0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void runStatistici(int n0) {
		
	}
	
	public int runStabilizzazione() throws EventoException, GeneratoreException, CentroException {
		Scheduler sched = null;
		Impianto imp = null;
		int ripetizioni = 100;		
		int osservazioni = 1, p = 30;
		boolean go = true;
		long semiGeneratori[];
		
		double mediaCampionaria = 0.0;
		double varianzaCampionaria = 0.0;
		ArrayList<Double> curvaMedieCampionarie = new ArrayList<Double>();
		double valoreMedioCurvaMedieCampionarie = 0.0;
		
		int jobTerminati;
		double tempoMedioRisposta;		
		double[] tempiRispostaRun = new double[p];
		
		//153 semi per i centri, le code e le probabilità di diramazione dell'impianto
		semiGeneratori = getSemiIniziali();
				
		while (go) {			
			for (int k = 0; k < p; k ++) {
				imp = new Impianto(semiGeneratori, 12, 3);
				sched = new Scheduler(imp);						
				caricaJob(imp, sched);
				
				jobTerminati = 0;
				tempoMedioRisposta = 0.0;
				
				while (jobTerminati < osservazioni) {
					sched.schedule();
					if (jobTerminati < FineLAN1.getJobTerminati()) {
						tempoMedioRisposta += FineLAN1.getTempoRispostaJob();
						jobTerminati = FineLAN1.getJobTerminati();
					}
				}
				
				tempoMedioRisposta /= osservazioni;
				tempiRispostaRun[k] = tempoMedioRisposta;
				semiGeneratori = imp.getNuoviSemi();
			}
			
			//Stimatore Gordon, e(n)
			mediaCampionaria = 0.0;
			for (int k = 0; k < p; k ++) {
				mediaCampionaria += tempiRispostaRun[k];
			}					
			mediaCampionaria /= p;											
			
			curvaMedieCampionarie.add(mediaCampionaria);
			
			//Stimatore Gordon s^2(~X(n))
			varianzaCampionaria = 0.0;
			for (int k = 0; k < p; k ++) {
				varianzaCampionaria += Math.pow(tempiRispostaRun[k] - mediaCampionaria, 2);
			}
			varianzaCampionaria /= (p - 1);
			
			valoreMedioCurvaMedieCampionarie = 0.0;
			for (int k = 0; k < osservazioni; k ++) {
				valoreMedioCurvaMedieCampionarie += curvaMedieCampionarie.get(k);
			}						
			valoreMedioCurvaMedieCampionarie /= osservazioni;
			
			System.out.println("Osservazioni: " + osservazioni + " - " + "Run: " + p + " - " + "Ripetizioni: " + ripetizioni);			
			System.out.println("Media Campionaria: " + mediaCampionaria + " - " + "Varianza Campionaria: " + varianzaCampionaria);
			System.out.println("Valore medio curva medie campionarie: " + valoreMedioCurvaMedieCampionarie);
			System.out.println("Distanza tra la media e il valore medio della curva: " + Math.abs(valoreMedioCurvaMedieCampionarie - mediaCampionaria));
			System.out.println("Massima distanza consentita: " + 0.1 * valoreMedioCurvaMedieCampionarie);			
			System.out.println();
			
			if (Math.abs(valoreMedioCurvaMedieCampionarie - mediaCampionaria) < 0.1 * valoreMedioCurvaMedieCampionarie) {
				ripetizioni --;
			} else {
				ripetizioni = 100;
			}
			
			if (ripetizioni == 0) {
				go = false;
			} else {
				osservazioni ++;
			}
		}
		
		return osservazioni;
	}
	
	public long[] getSemiIniziali() {
		Generatore gen = null;
		long num = 12213455;
		long []semi = new long[153];
		int i = 0;
		
		while (i < 153) {
			gen = new GeneratoreUniforme(num);
			if (TestGeneratoreUniforme.ChiQuadroTest(gen)) {
				semi[i] = num;				
				//System.out.println("Seme " + i + ": " + num);
				i ++;
			} 
			num = (long)(gen.getNext() * Integer.MAX_VALUE);
			
			if (num % 2 == 0) {
				//il seme deve essere dispari	
				num ++;
			}			
		}
		
		return semi;
	}
	
	public void caricaJob(Impianto imp, Scheduler sched) throws EventoException {
		Job job;
		double next_time;
		
		for (int j = 0; j < 12; j ++) {
			job = new Job();
			next_time = imp.getClientPC().get(j).aggiungiJob(job);
			sched.getCalendario().aggiungiEvento(new FinePcHC("fine_pchc", next_time, j));
		}
	}
}
