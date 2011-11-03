package IAZI_simulator;

import IAZI_simulator.entita.Impianto;
import IAZI_simulator.entita.Job;
import IAZI_simulator.entita.Scheduler;
import IAZI_simulator.eventi.FinePcHC;
import IAZI_simulator.exception.CentroException;
import IAZI_simulator.exception.EventoException;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.*;

public class IAZI_Simulator {

	public static long semi1[] = {
		12213455
	};
	
	/**
	 * @param args
	 */
	public static void main(String args[]){
		
		long semi[] = new long[153];
		int i = 0;
		long num = 12213455;
		Generatore gen = null;
		
		//153 semi per i centri, le code e le probabilità di diramazione dell'impianto
		while (i < 153) {
			gen = new GeneratoreUniforme(num);
			if (TestGeneratoreUniforme.ChiQuadroTest(gen)) {
				semi[i] = num;				
				System.out.println("Seme " + i + ": " + num);
				i ++;
			} 
			num = (long)(gen.getNext() * Integer.MAX_VALUE);
			
			if (num % 2 == 0) {
				//il seme deve essere dispari	
				num ++;
			}
			
		}
		
		
		Impianto imp = null;
		
		try {
			imp = new Impianto(semi, 12, 3);
		} catch (GeneratoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		Scheduler sched = new Scheduler(imp);
		Job job = new Job();
		double next_time = imp.getClientPC().get(0).aggiungiJob(job, 0);

		
		
		try {
			sched.getCalendario().aggiungiEvento(new FinePcHC("fine_pchc", next_time, 0));
			sched.schedule();
		} catch (CentroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (EventoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
	}
}
