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
	
	public void runStatistici(int n0) throws GeneratoreException, EventoException, CentroException {
		int p = 100;
		int[] lunghezza_run = new int[p];
		Generatore gen = null;
		long seme = 777777777L;
		int sup = 100, inf = 50;
		Impianto imp;
		Scheduler sched;
		long[] semiGeneratori;
		int n;
		int count;
		double yj, zj;
		double[] vettore_yj = new double[p];
		double media_yj, media_zj, media_nj;
		double valore_medio;
		double u_alpha_mezzi = 1.96;
		double s11_quadro, s12_quadro, s22_quadro, s_quadro;
		double d;
		double inf_intervallo_confidenza;			
		double sup_intervallo_confidenza;
		Job job = null;
		double next_time;
		
		for (int i = 1; i <= 12; i ++) {
			gen = new GeneratoreUniforme(seme);
			for (int j = 0; j < p; j ++) {
				lunghezza_run[j] = (int)(gen.getNext() * sup);
				if (lunghezza_run[j] < inf) {
					lunghezza_run[j] += inf;
				}
			}
			
			media_yj = 0.0;
			media_zj = 0.0;
			media_nj = 0.0;
			
			for (int j = 0; j < p; j ++) {
				semiGeneratori = getSemiIniziali(seme);
				//TODO controllare
				seme = semiGeneratori[semiGeneratori.length - 1];
				
				imp = new Impianto(semiGeneratori, 12, 3);
				sched = new Scheduler(imp);
				
				for (int k = 0; k < i; k ++) {
					job = new Job();
					next_time = imp.getClientPC().get(job.getId()).aggiungiJob(job);
					sched.getCalendario().aggiungiEvento(new FinePcHC("fine_pchc", next_time, job.getId()));
				}
				
				//STABILIZZAZIONE
				n = 0;				
				while (n < n0) {
					sched.schedule();
					if (n < FineLAN1.getJobTerminati()) {
						n = FineLAN1.getJobTerminati();
					}
				} //IMPIANTO STABILIZZATO
				
				yj = 0.0;
				zj = 0.0;
				count = 0;
				
				while (count < lunghezza_run[j]) {
					sched.schedule();
					if (n < FineLAN1.getJobTerminati()) {
						yj += FineLAN1.getTempoRispostaJob();
						zj += Math.pow(FineLAN1.getTempoRispostaJob(), 2);
						n = FineLAN1.getJobTerminati();
						count ++;
					}
				}
				
				vettore_yj[j] = yj;
				media_yj += yj;
				media_zj += zj;							
			} //FINITI TUTTI I p RUN DI LUNGHEZZA n VARIABILE
			
			media_yj /= p;
			media_zj /= p;
			
			for (int k = 0; k < p; k ++) {
				media_nj += lunghezza_run[k];
			}
			
			media_nj /= p;
			
			//E(x)
			valore_medio = media_yj / media_nj;
			
			s11_quadro = 0.0;
			for (int k = 0; k < p; k ++) {
				s11_quadro += Math.pow(vettore_yj[k] - media_yj, 2);
			}
			
			s11_quadro /= (p - 1);
			
			s22_quadro = 0.0;
			for (int k = 0; k < p; k ++) {
				s22_quadro += Math.pow(lunghezza_run[k] - media_nj, 2);
			}
			
			s22_quadro /= (p - 1);
			
			s12_quadro = 0.0;
			for (int k = 0; k < p; k ++) {
				s12_quadro += (lunghezza_run[k] - media_nj) * (vettore_yj[k] - media_yj);
			}
			
			s12_quadro /= (p - 1);
			
			s_quadro = s11_quadro - 2 * valore_medio * s12_quadro + Math.pow(valore_medio, 2) * s22_quadro;
			
			d = Math.sqrt(s_quadro) / (media_nj * Math.sqrt(p));
			
			inf_intervallo_confidenza = valore_medio - (d * u_alpha_mezzi);			
			sup_intervallo_confidenza = valore_medio + (d * u_alpha_mezzi);
			
			System.out.println("Valor Medio " + valore_medio + " di N = " + i + " job nell'impianto");
			System.out.println("estremo inf intervallo di confidenza al 90%  " + inf_intervallo_confidenza);
			System.out.println("estremo sup intervallo di confidenza al 90%  " + sup_intervallo_confidenza);
			
		}
		
		
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
				caricaJob(imp, sched, imp.getnClient());
				
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
	
	public long[] getSemiIniziali(long num) {
		Generatore gen = null;
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
	
	public void caricaJob(Impianto imp, Scheduler sched, int nJob) throws EventoException {
		Job job;
		double next_time;
		
		for (int j = 0; j < nJob; j ++) {
			job = new Job();
			next_time = imp.getClientPC().get(j).aggiungiJob(job);
			sched.getCalendario().aggiungiEvento(new FinePcHC("fine_pchc", next_time, j));
		}
	}
}
