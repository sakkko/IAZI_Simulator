package IAZI_simulator;

import java.util.ArrayList;

import IAZI_simulator.entita.*;
import IAZI_simulator.eventi.*;
import IAZI_simulator.exception.*;
import IAZI_simulator.generatori.*;

public class IAZI_Simulator {

	public final static int N = 48;
	
	public final static int INF = 50;
	public final static int SUP = 100;
	
	public final static double U_ALPHA_MEZZI = 1.96;
	
	public final static int LUNGHEZZA_FINESTRA = 10;
	
	/**
	 * @param args
	 */
	public static void main(String args[]){		
		IAZI_Simulator iazi = new IAZI_Simulator();
		int n0 = 2558, p = 100;		
		
		try {
			//n0 = iazi.runStabilizzazione();
			iazi.runStatistici(n0, p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void runStatistici(int n0, int p) throws GeneratoreException, EventoException, CentroException {
		int[] lunghezza_run = new int[p];
		long seme = 777777777L;
		Impianto imp, impStabile = null;
		Scheduler sched, schedStabile = null;
		long[] semiIniziali;
		int n = 0;
		double[] vettore_yj = new double[p];
		double media_yj, media_zj, media_nj;
		double valore_medio, s_quadro, d;
		double inf_intervallo_confidenza, sup_intervallo_confidenza;
		int[] valori_throughput = new int[N];
		double[] misure;
		double media_throughput = 0.0;
		ArrayList<Double> tempiLAN2 = new ArrayList<Double>();
		ArrayList<Generatore> generatori = null;
		Generatore genRun = new GeneratoreUniforme(seme);
		
		semiIniziali = getSemiIniziali();
		
		for (int i = 1; i <= N; i ++) {			
			media_yj = 0.0;
			media_zj = 0.0;
			media_nj = 0.0;		
			
			lunghezza_run = getLunghezzeRun(p, genRun);									
			for (int j = 0; j < p; j ++) {
			
				imp = new Impianto(semiIniziali, N, 3);
				sched = new Scheduler(imp);				
				caricaJob(imp, sched, i);
								
				//STABILIZZAZIONE
				n = stabilizzaImpianto(sched, n0);
				//IMPIANTO STABILIZZATO						
	
				if (j > 0) {
					//EREDITO GENERATORI RUN PRECEDENTE
					imp.setGeneratori(generatori);
				}
				//OTTENGO MISURE DALL'IMPIANTO
				misure = getMisure(sched, i, lunghezza_run[j], n, tempiLAN2);
				
				//SALVO ATTUALI GENERATORI
				generatori = imp.getGeneratori();
				
				if (misure[2] >= 0) {
					valori_throughput[(int)misure[2]] ++;
					media_throughput += (misure[2] / (double)IAZI_Simulator.LUNGHEZZA_FINESTRA);
				}				
				
				vettore_yj[j] = misure[0];
				media_yj += misure[0];
				media_zj += misure[1];		
				media_nj += lunghezza_run[j];
				
			} //FINITI TUTTI I p RUN DI LUNGHEZZA n VARIABILE
			
			media_yj /= p;
			media_zj /= p;			
			media_nj /= p;
			
			//E(x)
			valore_medio = media_yj / media_nj;
			
			s_quadro = getSQuadro(vettore_yj, media_yj, lunghezza_run, media_nj, valore_medio, p);
			d = Math.sqrt(s_quadro) / (media_nj * Math.sqrt(p));
			
			inf_intervallo_confidenza = valore_medio - (d * U_ALPHA_MEZZI);			
			sup_intervallo_confidenza = valore_medio + (d * U_ALPHA_MEZZI);
			
			System.out.println("Valor Medio " + valore_medio + " di N = " + i + " job nell'impianto");
			System.out.println("estremo inf intervallo di confidenza al 95%  " + inf_intervallo_confidenza);
			System.out.println("estremo sup intervallo di confidenza al 95%  " + sup_intervallo_confidenza);
			
		}

		double media_tempi_lan2 = 0.0;
		
		System.out.println("Tempi LAN2:");
		for (int i = 0; i < tempiLAN2.size(); i ++) {
			System.out.println(tempiLAN2.get(i));
			media_tempi_lan2 += tempiLAN2.get(i);
		}
		
		media_tempi_lan2 /= tempiLAN2.size();
		
		System.out.println("Media tempi lan2: " + media_tempi_lan2);
		
		System.out.println("Throughput LAN2");
		for (int i = 0; i < valori_throughput.length; i ++) {
			System.out.println(i + ": " + valori_throughput[i]);
		}
		
		System.out.println("Media throughput (N = 48): " + media_throughput / p);
		
		
		
	}
	
	public double[] getMisure(Scheduler sched, int client, int lunghezza_run, int job_terminati, ArrayList<Double> tempiLAN2) throws CentroException, EventoException {
		int count = 0;
		Evento event;
		double clock_value = 0.0;
		boolean window_open = false;
		double start_window = 0.0, end_window = 0.0;
		double[] ret = new double[3]; //0: yj, 1:zj, 3:job_count (maggiore di 0 sono nel caso di calcolo del throughput)
		int osserv_iniziale;
		
		ret[0] = 0;
		ret[1] = 0;
		ret[2] = -1;
		
		//apro la finestra al centro del run
		osserv_iniziale = lunghezza_run / 2;
		
		while (count < lunghezza_run) {
			clock_value = sched.getCalendario().getClock().getTempo_di_simulazione();
			//stimo il throughput per 48 job
			if (client == N && count == osserv_iniziale && !window_open) {
				start_window = clock_value;
				end_window = clock_value + LUNGHEZZA_FINESTRA;
				ret[2] = 0;
				window_open = true;
			}
			
			event = sched.schedule();
			
			if (start_window <= clock_value && clock_value <= end_window) {
				//sono all'interno della finestra di osservazione
				//conto i job di classe A che escono da LAN2
				if (window_open && event.getNomeEvento().equals(Evento.FINE_LAN2)) {
					if (event.getJob().getClasse().equals("A")) {
						ret[2] ++;
					}
				}
			}
								
			if (job_terminati < FineLAN1.getJobTerminati()) {
				ret[0] += FineLAN1.getTempoRispostaJob();
				ret[1] += Math.pow(FineLAN1.getTempoRispostaJob(), 2);
				job_terminati = FineLAN1.getJobTerminati();
				if (client == N) {
					tempiLAN2.add(event.getJob().getTempoFineLAN2b() - event.getJob().getTempoFineLAN2a());
				}
				count ++;
			}
		}
		
		return ret;
	}
	
	public int stabilizzaImpianto(Scheduler sched, int n0) throws CentroException, EventoException {
		int n = 0;				
		while (n < n0) {
			sched.schedule();
			if (n < FineLAN1.getJobTerminati()) {
				n = FineLAN1.getJobTerminati();
			}
		}
		
		return n;
	}
	
	public int[] getLunghezzeRun(int p, Generatore gen) {				
		int[] lunghezza_run = new int[p];
		
		for (int j = 0; j < p; j ++) {
			lunghezza_run[j] = (int)(gen.getNext() * SUP);
			if (lunghezza_run[j] < INF) {
				lunghezza_run[j] += INF;
			}
		}
		
		return lunghezza_run;
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
		
		semiGeneratori = getSemiIniziali();
				
		while (go) {			
			for (int k = 0; k < p; k ++) {
				imp = new Impianto(semiGeneratori, N, 3);
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
				semiGeneratori = imp.getNuoviSemi(semiGeneratori.length);
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
			
			System.out.println("N0: " + osservazioni + " - Media Campionaria: " + mediaCampionaria + " - Varianza Campionaria: " + varianzaCampionaria + " - Valore medio curva: " + valoreMedioCurvaMedieCampionarie + " - Distanza: " + Math.abs(valoreMedioCurvaMedieCampionarie - mediaCampionaria) + " - Ripetizioni: " + ripetizioni);			
			
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
	
	public double getSQuadro(double[] vettore_yj, double media_yj, 
			int[] lunghezza_run, double media_nj, double valore_medio, int p) {
		double s11Quadro, s12Quadro, s22Quadro;
		
		s11Quadro = 0.0;
		for (int k = 0; k < p; k ++) {
			s11Quadro += Math.pow(vettore_yj[k] - media_yj, 2);
		}		
		s11Quadro /= (p - 1);
		
		s12Quadro = 0.0;
		for (int k = 0; k < p; k ++) {
			s12Quadro += (lunghezza_run[k] - media_nj) * (vettore_yj[k] - media_yj);
		}		
		s12Quadro /= (p - 1);
		
		s22Quadro = 0.0;
		for (int k = 0; k < p; k ++) {
			s22Quadro += Math.pow(lunghezza_run[k] - media_nj, 2);
		}		
		s22Quadro /= (p - 1);
				
		return  s11Quadro - 2 * valore_medio * s12Quadro + Math.pow(valore_medio, 2) * s22Quadro;
	}
	
	public long[] getSemiIniziali() {
		Generatore gen = null;
		long num = 12213455;
		long []semi = new long[getNumeroSemi()];
		int i = 0;
		
		while (i < semi.length) {
			gen = new GeneratoreUniforme(num);
			if (TestGeneratoreUniforme.ChiQuadroTest(gen)) {
				semi[i] = num;				
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
		long []semi = new long[getNumeroSemi()];
		int i = 0;
		
		while (i < semi.length) {
			gen = new GeneratoreUniforme(num);
			if (TestGeneratoreUniforme.ChiQuadroTest(gen)) {
				semi[i] = num;				
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
	
	public int getNumeroSemi() {
		return 34 + N * 10;		
	}
	
	public void caricaJob(Impianto imp, Scheduler sched, int nJob) throws EventoException {
		Job job;
		double next_time;
		
		for (int i = 0; i < nJob; i ++) {
			job = new Job();
			next_time = imp.getClientPC().get(job.getId()).aggiungiJob(job);
			sched.getCalendario().aggiungiEvento(new FinePcHC(next_time, job.getId()));
		}
	}
}
