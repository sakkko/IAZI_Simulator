package IAZI_simulator;

import java.util.*;
import java.io.*;

import IAZI_simulator.entita.*;
import IAZI_simulator.eventi.*;
import IAZI_simulator.exception.*;
import IAZI_simulator.generatori.*;


public class IAZI_Simulator {
	
	public final static int INF = 50;
	public final static int SUP = 100;
	
	public final static double U_ALPHA_MEZZI = 1.96;
	
	public final static double LUNGHEZZA_FINESTRA = 10;
	
	public final static int STAB_AND_STAT = 0;
	public final static int ONLY_STAT = 1;
	public final static int ONLY_STAB = 2;
	public final static int TEST_GEN = 3;
	
	public final static int PCHC = 0;
	public final static int TERM = 1;
	public final static int LAN1 = 2;
	public final static int GW1 = 3;
	public final static int WAN = 4;
	public final static int GW2 = 5;
	public final static int LAN2 = 6;
	public final static int PCHS = 7;
	public final static int DISK = 8;
	
	public static BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
	
	private int nClient;
	private int nClientThroughput;
	private int osservazioni_stab;
	private int run_stab;
	private int run_stat;
	private int type;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws GeneratoreException 
	 */

	public static void main(String args[]){
		String buf;
		int type;
		
		System.out.println("Tipo di simulazione");
		System.out.println("0. Stabilizzazione e run statistici");
		System.out.println("1. Solo run statistici");
		System.out.println("2. Solo run di stabilizzazione");
		System.out.println("3. Test generatori");
		System.out.print("Inserire scelta: ");
		
		try {
			buf = is.readLine();
			type = Integer.parseInt(buf);
			
			if (type < STAB_AND_STAT || type > TEST_GEN) {
				System.err.println("Tipo di simulazione non valido");
				System.exit(1);
			}
			
			if (type == TEST_GEN) {
				TestGeneratori();
			} else {
				IAZI_Simulator sim = CreaSimulazione(type);
				sim.run();
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.err.println("Formato non valido, inserire un numero");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} 
		
	}
	
	public static void TestGeneratori() throws IOException, GeneratoreException {
		int gen_type = -1;
		String buf;
		System.out.println("Generatori");
		System.out.println(PCHC + ". Generatore centro PcHC");
		System.out.println(TERM + ". Generatore centro Terminale");
		System.out.println(LAN1 + ". Generatore centro LAN1");
		System.out.println(GW1 + ". Generatore centro GW1");
		System.out.println(WAN + ". Generatore centro WAN");
		System.out.println(GW2 + ". Generatore centro GW2");
		System.out.println(LAN2 + ". Generatore centro LAN2");
		System.out.println(PCHS + ". Generatore centro PcHS");
		System.out.println(DISK + ". Generatore centro Disk");
		
		System.out.print("Scegliere il generatore da testare: ");
		
		buf = is.readLine();
		gen_type = Integer.parseInt(buf);
		if (gen_type < 0 || gen_type > 8) {
			System.err.println("Scelta non valida");
			System.exit(1);
		}
		
		TestGeneratori test = new TestGeneratori();
		
		switch (gen_type) {
			case PCHC:
				test.testGeneratorePcHC();
				break;
			case TERM:
				test.testGeneratoreTerm();
				break;
			case LAN1:
				test.testGeneratoreLAN1();
				break;
			case GW1:
				test.testGeneratoreGW1();
				break;
			case WAN:
				test.testGeneratoreWAN();
				break;
			case GW2:
				test.testGeneratoreGW2();
				break;
			case LAN2:
				test.testGeneratoreLAN2();
				break;
			case PCHS:
				test.testGeneratorePcHS();
				break;
			case DISK:
				test.testGeneratoreDisk();
				break;
			default:
				System.err.println("Scelta non valida");
				System.exit(1);
				break;
		}
	}
	
	public static IAZI_Simulator CreaSimulazione(int type) throws IOException, SimulatorException {
		int n0 = 124, p = 1000;		
		String buf;
		int p0 = 30;
		int N = 12, N_TH = 12;
			
		System.out.print("Scegliere numero N di client: ");
		buf = is.readLine();

		N = Integer.parseInt(buf);
		if (N <= 0) {
			System.err.println("Scelta non valida");
			System.exit(1);
		}
						
		if (type < ONLY_STAB) {
			System.out.print("Scegliere il numero p dei run (run statistici - default 1000): ");
			buf = is.readLine();
			if (!buf.equals("")) {				
				p = Integer.parseInt(buf);
				if (p <= 0) {
					System.err.println("Scelta non valida");
					System.exit(1);
				}				
			}
			
			if (type == ONLY_STAT) {
				System.out.print("Scegliere il numero n0 di osservazioni dopo cui il sistema è considerato stabile (defualt 124 (ottenuto per 12 client)): ");
				buf = is.readLine();
				n0 = Integer.parseInt(buf);
				if (n0 < 0) {
					System.err.println("Scelta non valida");
					System.exit(1);
				}
				
				System.out.print("Scegliere numero N di client per cui calcolare il throughput di LAN2 (default " + N + "): ");
				buf = is.readLine();
				if (!buf.equals("")) {					
					N_TH = Integer.parseInt(buf);
					if (N_TH <= 0) {
						System.err.println("Scelta non valida");
						System.exit(1);
					}
					if (N_TH > N) {
						System.err.println("Scelta non valida: valore massimo consentito: " + N);
						System.exit(1);
					}
				}					
			} else {
				System.out.print("Scegliere il numero p dei run (run stabilizzazione - default 30): ");
				buf = is.readLine();
				if (!buf.equals("")) {
					p0 = Integer.parseInt(buf);
					if (p0 <= 0) {
						System.err.println("Scelta non valida");
						System.exit(1);
					}
				}
				System.out.print("Scegliere numero N di client per cui calcolare il throughput di LAN2 (default " + N + "): ");
				buf = is.readLine();
				if (!buf.equals("")) {
					
					N_TH = Integer.parseInt(buf);
					if (N_TH <= 0) {
						System.err.println("Scelta non valida");
						System.exit(1);
					}
					if (N_TH > N) {
						System.err.println("Scelta non valida: valore massimo consentito: " + N);
						System.exit(1);
					}				
				}
			}
		} else {
			p = 30;
			System.out.print("Scegliere il numero p dei run (run stabilizzazione - default 30): ");
			buf = is.readLine();
			if (!buf.equals("")) {
				p0 = Integer.parseInt(buf);
				if (p0 <= 0) {
					System.err.println("Scelta non valida");
					System.exit(1);
				}				
			}
		}	
		
		return new IAZI_Simulator(N, N_TH, n0, p0, p, type);
	}
	
	public IAZI_Simulator() {
		
	}
	
	public void run() throws IOException, EventoException, GeneratoreException, CentroException {
		if (type == STAB_AND_STAT) {
			osservazioni_stab = runStabilizzazione(run_stab);
			runStatistici(osservazioni_stab, run_stat);
		} else if (type == ONLY_STAB) {
			osservazioni_stab = runStabilizzazione(run_stab);
		} else if (type == ONLY_STAT) {
			runStatistici(osservazioni_stab, run_stat);
		}
	}
	
	public IAZI_Simulator(int nClient, int nClientThroughput, int osservazioni_stab,
			int run_stab, int run_stat, int type) throws SimulatorException {
		
		if (nClient <= 0 || nClient > 48) {
			throw new SimulatorException("Numero di client non valido - min:1 max:48");
		}
		
		if (type == STAB_AND_STAT) {
			if (run_stab <= 0 || run_stat <= 0) {
				throw new SimulatorException("Numero di run non valido - min:1");
			}
		} else if (type == ONLY_STAB) {
			if (run_stab <= 0) {
				throw new SimulatorException("Numero di run non valido - min:1");
			}
		} else if (type == ONLY_STAT) {
			if (run_stat <= 0) {
				throw new SimulatorException("Numero di run non valido - min:1");
			}
			if (osservazioni_stab < 0) {
				throw new SimulatorException("Numero di osservazioni dopo cui il sistema è considerato stabile non valido - min:0");
			}
		} else {
			throw new SimulatorException("Tipo di simulazione non valido");
		}
		
		this.nClient = nClient;
		this.nClientThroughput = nClientThroughput;
		this.osservazioni_stab = osservazioni_stab;
		this.run_stab = run_stab;
		this.run_stat = run_stat;
		this.type = type;
	}

	public void runStatistici(int n0, int p) throws GeneratoreException, EventoException, CentroException {
		System.out.println("Run statistici (n0=" + n0 + " p=" + p + ")");
		int[] lunghezza_run = new int[p];
		long seme_lung = 12213455;
		Impianto imp;
		Scheduler sched;
		long[] semiGeneratori, semiIniziali;
		int n;
		double[] vettore_yj = new double[p];
		double media_yj, media_nj;
		double valore_medio, s_quadro, d;
		double inf_intervallo_confidenza, sup_intervallo_confidenza;
		int[] valori_throughput = new int[nClient];
		double[] misure;
		double media_throughput = 0.0;
		ArrayList<Double> tempiLAN2 = new ArrayList<Double>();
		
		semiIniziali = getSemiIniziali();
		
		for (int i = 1; i <= nClient; i ++) {			
			media_yj = 0.0;
			media_nj = 0.0;
			
			seme_lung += 1806;
			lunghezza_run = getLunghezzeRun(p, seme_lung);
			semiGeneratori = null;
			
			for (int j = 0; j < p; j ++) {				
				imp = new Impianto(semiIniziali, nClient, 3);
				sched = new Scheduler(imp);				
				caricaJob(imp, sched, i);								
				
				//STABILIZZAZIONE
				n = stabilizzaImpianto(sched, n0);
				//IMPIANTO STABILIZZATO
			
				//CARICO GENERATORI
				if (semiGeneratori != null) {
					imp.setNuoviSemi(semiGeneratori, semiIniziali);
				}
				
				//OTTENGO MISURE DALL'IMPIANTO
				misure = getMisure(sched, i, lunghezza_run[j], n, tempiLAN2);
								
				if (misure[2] >= 0) {
					valori_throughput[(int)misure[2]] ++;
					media_throughput += (misure[2] / (double)IAZI_Simulator.LUNGHEZZA_FINESTRA);
				}				
				
				vettore_yj[j] = misure[0];
				media_yj += misure[0];			
				media_nj += lunghezza_run[j];
				
				//SALVO GENERATORI
				semiGeneratori = imp.getNuoviSemi(semiIniziali.length);									
				
			} //FINITI TUTTI I p RUN DI LUNGHEZZA n VARIABILE
			
			media_yj /= p;
			media_nj /= p;
			
			//E(x)
			valore_medio = media_yj / media_nj;
			
			s_quadro = getSQuadro(vettore_yj, media_yj, lunghezza_run, media_nj, valore_medio, p);
			d = Math.sqrt(s_quadro) / (media_nj * Math.sqrt(p));
			
			inf_intervallo_confidenza = valore_medio - (d * U_ALPHA_MEZZI);			
			sup_intervallo_confidenza = valore_medio + (d * U_ALPHA_MEZZI);
			
			System.out.format(new Locale("en"), "%02d Tempo medio %.17f inf %.17f sup %.17f\n", i, valore_medio, inf_intervallo_confidenza, sup_intervallo_confidenza);
			
		}
		
		System.out.println("Throughput LAN2");
		for (int i = 0; i < valori_throughput.length; i ++) {
			System.out.println(i + ": " + valori_throughput[i]);
		}
		
		System.out.println("Media throughput (N = " + nClientThroughput + "): " + media_throughput / p);						
	}
	
	public double[] getMisure(Scheduler sched, int client, int lunghezza_run, int job_terminati, ArrayList<Double> tempiLAN2) throws CentroException, EventoException, GeneratoreException {
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
			//stimo il throughput per N_TH job
			if (client == nClientThroughput && count == osserv_iniziale && !window_open) {
				start_window = clock_value;
				end_window = clock_value + LUNGHEZZA_FINESTRA;
				ret[2] = 0;
				window_open = true;
			}
			
			event = sched.schedule();
			
			if (start_window <= clock_value && clock_value <= end_window) {
				//sono all'interno della finestra di osservazione
				//conto i job di classe A che escono da LAN2
				if (window_open && event.getClass().equals(FineLAN2.class)) {
					if (event.getJob().getClasse().equals("A")) {
						ret[2] ++;
					}
				}
			}
								
			if (job_terminati < FineLAN1.getJobTerminati()) {
				ret[0] += FineLAN1.getTempoRispostaJob();
				ret[1] += Math.pow(FineLAN1.getTempoRispostaJob(), 2);
				job_terminati = FineLAN1.getJobTerminati();
				if (client == nClientThroughput) {
					tempiLAN2.add(event.getJob().getTempoFineLAN2b() - event.getJob().getTempoFineLAN2a());
				}
				count ++;
			}
		}
		
		return ret;
	}
	
	public int stabilizzaImpianto(Scheduler sched, int n0) throws CentroException, EventoException, GeneratoreException {
		int n = 0;				
		while (n < n0) {
			sched.schedule();
			if (n < FineLAN1.getJobTerminati()) {
				n = FineLAN1.getJobTerminati();
			}
		}
		
		return n;
	}
	
	public int[] getLunghezzeRun(int p, long seme) throws GeneratoreException {		
		Generatore gen = new GeneratoreUniforme(seme, seme);
		int[] lunghezza_run = new int[p];
		
		for (int j = 0; j < p; j ++) {
			lunghezza_run[j] = (int)(gen.getNext() * SUP);
			if (lunghezza_run[j] < INF) {
				lunghezza_run[j] += INF;
			}
		}
		
		return lunghezza_run;
	}
	
	public int runStabilizzazione(int p0) throws EventoException, GeneratoreException, CentroException, FileNotFoundException, IOException {
		System.out.println("Run di stabilizzazione (N=" + nClient + " p=" + p0 + ")");
		Scheduler sched = null;
		Impianto imp = null;
		int ripetizioni = 100;		
		int osservazioni = 1, p = p0;
		boolean go = true;
		long semiGeneratori[], semiIniziali[];
		
		double mediaCampionaria = 0.0;
		double varianzaCampionaria = 0.0;
		ArrayList<Double> curvaMedieCampionarie = new ArrayList<Double>();
		double valoreMedioCurvaMedieCampionarie = 0.0;
		
		PrintWriter os = new PrintWriter(new FileWriter("stabilizzazione.txt"));
		
		int jobTerminati;
		double tempoMedioRisposta;		
		double[] tempiRispostaRun = new double[p];
		double delta;
		String tmp;
		
		semiIniziali = getSemiIniziali();
		
		while (go) {
			semiGeneratori = new long[semiIniziali.length];
			for (int i = 0; i < semiGeneratori.length; i ++) {
				semiGeneratori[i] = semiIniziali[i];
			}
			
			for (int k = 0; k < p; k ++) {
				imp = new Impianto(semiGeneratori, nClient, 3);
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
			
			delta = Math.abs(valoreMedioCurvaMedieCampionarie - mediaCampionaria);
			tmp = String.format(new Locale("en"), "N0: %04d - Media Campionaria: %.17f - Varianza Campionaria: %.17f - Valore Medio: %.17f - Delta: %.17f - Ripetizioni: %03d", osservazioni, mediaCampionaria, varianzaCampionaria, valoreMedioCurvaMedieCampionarie, delta, ripetizioni);
			
			os.println(tmp);
			System.out.println(tmp);
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
		
		System.out.println("Fine run stabilizzazione: n0=" + osservazioni);
				
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
	
	public long[] getSemiIniziali() throws GeneratoreException {
		return getSemiIniziali(777777777L);
	}
	
	public long[] getSemiIniziali(long num) throws GeneratoreException {
		Generatore gen = null;
		long []semi = new long[getNumeroSemi()];
		int i = 0;
		
		if (num % 2 == 0) {
			//il seme deve essere dispari	
			num ++;
		}
		
		while (i < semi.length) {
			gen = new GeneratoreUniforme(num, num);
			if (TestGeneratoreUniforme.ChiQuadroTest(gen)) {
				semi[i] = num;				
				i ++;
			} 
			num -= 313958;									
		}
		
		return semi;
	}
	
	public int getNumeroSemi() {
		return 33 + nClient * 13;		
	}
	
	public void caricaJob(Impianto imp, Scheduler sched, int nJob) throws EventoException, GeneratoreException {
		Job job;
		double next_time;
		
		for (int i = 0; i < nJob; i ++) {
			job = new Job(nClient);
			next_time = imp.getClientPC().get(job.getId()).aggiungiJob(job);
			sched.getCalendario().aggiungiEvento(new FinePcHC(next_time, job.getId()));
		}
	}
}
