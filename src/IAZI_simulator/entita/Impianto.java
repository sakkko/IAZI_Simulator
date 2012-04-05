package IAZI_simulator.entita;

import java.util.ArrayList;

import IAZI_simulator.centri.*;
import IAZI_simulator.eventi.FineLAN1;
import IAZI_simulator.exception.GeneratoreException;
import IAZI_simulator.generatori.*;


public class Impianto { 
	
	public Impianto(long[] semi, int nClient, int nServer) throws GeneratoreException {
		this.nClient = nClient;
		this.nServer = nServer;
		int i;
		long[] tmpSemi;
		
		terminali = new ArrayList<Terminale>(nClient);
		for (i = 0; i < nClient; i ++) {
			terminali.add(new Terminale(semi[i], semi[i], nClient));
		}
		
		clientPC = new ArrayList<PcHC>(nClient);
		for (int k = 0; k < nClient; k ++) {
			clientPC.add(new PcHC(semi[i], semi[i + 1], semi[i], semi[i + 1], nClient));
			i += 2;
		}		
		
		lan1 = new ArrayList<LAN1>();
		long[] tmp_semi = new long[4];
		for (int k = 0; k < nClient; k ++) {
			tmp_semi[0] = semi[i];
			i ++;
			tmp_semi[1] = semi[i];
			i ++;
			tmp_semi[2] = semi[i];
			i ++;
			tmp_semi[3] = semi[i];
			i ++;
			lan1.add(new LAN1(tmp_semi, tmp_semi, nClient));
		}
		FineLAN1.setJobTerminati(0);
		
		gw1 = new GW1(semi[i], semi[i + 1], semi[i], semi[i + 1]);
		i += 2;
		
		wan = new ArrayList<WAN>();
		tmpSemi = new long[4];
		for (int k = 0; k < nClient; k ++) {
			for (int j = 0; j < tmpSemi.length; j ++) {
				tmpSemi[j] = semi[i];
				i ++;
			}
			wan.add(new WAN(tmpSemi, tmpSemi, nClient));
		}
				
		tmpSemi = new long[6];
		for (int j = 0; j < tmpSemi.length; j ++) {
			tmpSemi[j] = semi[i];
			i ++;
		}
		gw2 = new GW2(tmpSemi, tmpSemi);
		
		lan2 = new ArrayList<LAN2>();
		tmp_semi = new long[2];
		for (int j = 0; j < nClient; j ++) {
			tmp_semi[0] = semi[i];
			i ++;
			tmp_semi[1] = semi[i];
			i ++;
			lan2.add(new LAN2(tmp_semi, tmp_semi, nClient));
		}
		
		
		serverPC = new ArrayList<PcHS>(nServer);
		for (int j = 0; j < nServer; j ++) {
			serverPC.add(new PcHS(semi[i], semi[i + 1], semi[i], semi[i + 1]));
			i += 2;
		}
		 
		serverDisk = new ArrayList<Disk>(nServer);
		tmpSemi = new long[5];
		for (int k = 0; k < nServer; k ++) {
			for (int j = 0; j < tmpSemi.length; j ++) {
				tmpSemi[j] = semi[i];
				i ++;
			}
			serverDisk.add(new Disk(tmpSemi, semi[i], tmpSemi, semi[i]));
			i ++;
		}
			
		gen = new GeneratoreUniforme(semi[i], semi[i]);		
		
	}
	
	public void setNuoviSemi(long[] semi, long[] semiIniziali) throws GeneratoreException {
		int n = 0;
		
		for (int i = 0; i < nClient; i ++) {
			terminali.get(i).setNuovoSeme(semi[n], semiIniziali[n]);
			n ++;
		}
		
		for (int i = 0; i < nClient; i ++) {
			clientPC.get(i).setNuovoSeme(semi[n], semi[n + 1], semiIniziali[n], semiIniziali[n + 1]);
			n += 2;
		}
		
		long[] tmp_semi = new long[4];
		long[] tmp_semi_iniziali = new long[4];
		for (int i = 0; i < nClient; i ++) {
			tmp_semi[0] = semi[n];
			tmp_semi_iniziali[0] = semiIniziali[n];
			n ++;
			tmp_semi[1] = semi[n];
			tmp_semi_iniziali[1] = semiIniziali[n];
			n ++;
			tmp_semi[2] = semi[n];
			tmp_semi_iniziali[2] = semiIniziali[n];
			n ++;
			tmp_semi[3] = semi[n];
			tmp_semi_iniziali[3] = semiIniziali[n];
			n ++;
 			lan1.get(i).setNuovoSeme(tmp_semi, tmp_semi_iniziali);
		}
		
		gw1.setNuovoSeme(semi[n], semi[n + 1], semiIniziali[n], semiIniziali[n + 1]);
		n += 2;
		
		for (int i = 0; i < nClient; i ++) {
			wan.get(i).setNuovoSeme(semi[n], semi[n + 1], semi[n + 2], semi[n + 3],
					semiIniziali[n], semiIniziali[n + 1], semiIniziali[n + 2], semiIniziali[n + 3]);
			n += 4;
		}
		
		gw2.setNuovoSeme(semi[n], semi[n + 1], semi[n + 2], semi[n + 3], semi[n + 4], semi[n + 5],
				semiIniziali[n], semiIniziali[n + 1], semiIniziali[n + 2], semiIniziali[n + 3], semiIniziali[n + 4], semiIniziali[n + 5]);
		n += 6;
		
		tmp_semi = new long[2];
		tmp_semi_iniziali = new long[2];
		for (int i = 0; i < nClient; i ++) {
			tmp_semi[0] = semi[n];
			tmp_semi_iniziali[0] = semiIniziali[n];
			n ++;
			tmp_semi[1] = semi[n];
			tmp_semi_iniziali[1] = semiIniziali[n];
			n ++;
			lan2.get(i).setNuovoSeme(tmp_semi, tmp_semi_iniziali);
		}
		
		for (int i = 0; i < nServer; i ++) {
			serverPC.get(i).setNuovoSeme(semi[n], semi[n + 1], semiIniziali[n], semiIniziali[n + 1]);
			n += 2;
		}
		
		for (int i = 0; i < nServer; i ++) {
			serverDisk.get(i).setNuovoSeme(semi[n], semi[n + 1], semi[n + 2], semi[n + 3], semi[n + 4],
					semiIniziali[n], semiIniziali[n + 1], semiIniziali[n + 2], semiIniziali[n + 3], semiIniziali[n + 4]);
			n += 5;
			serverDisk.get(i).setNuovoSemeCoda(semi[n], semiIniziali[n]);
			n ++;
		}
		
		gen = new GeneratoreUniforme(semi[n], semiIniziali[n]);
		
	}
	
	public long[] getNuoviSemi(int size) throws GeneratoreException {
		long[] ret = new long[size];
		long[] tmp_semi;
		
		int i;
		
		for (i = 0; i < nClient; i ++) {
			ret[i] = terminali.get(i).getNuovoSeme();
		}
		
		for (PcHC cpc : clientPC) {
			tmp_semi = cpc.getNuovoSeme();
			for (int j = 0; j < tmp_semi.length; j ++) {
				ret[i] = tmp_semi[j];
				i ++;
			}		
		}			
		
		for (LAN1 ln1 : lan1) {
			tmp_semi = ln1.getNuovoSeme();
			for (int j = 0; j < tmp_semi.length; j ++) {
				ret[i] = tmp_semi[j];
				i ++;
			}	
		}
		
		tmp_semi = gw1.getNuovoSeme();
		for (int j = 0; j < tmp_semi.length; j ++) {
			ret[i] = tmp_semi[j];
			i ++;
		}	
		
		for (WAN wn : wan) {
			tmp_semi = wn.getNuovoSeme();
			for (int j = 0; j < tmp_semi.length; j ++) {
				ret[i] = tmp_semi[j];
				i ++;
			}
		}
		
		tmp_semi = gw2.getNuovoSeme();
		for (int j = 0; j < tmp_semi.length; j ++) {
			ret[i] = tmp_semi[j];
			i ++;
		}	
		
		for (LAN2 ln2 : lan2) {
			tmp_semi = ln2.getNuovoSeme();
			for (int j = 0; j < tmp_semi.length; j ++) {
				ret[i] = tmp_semi[j];
				i ++;
			}
			
		}
		
		for (PcHS spc : serverPC) {
			tmp_semi = spc.getNuovoSeme();
			for (int j = 0; j < tmp_semi.length; j ++) {
				ret[i] = tmp_semi[j];
				i ++;
			}		
		}
		
		for (Disk sdk : serverDisk) {
			tmp_semi = sdk.getNuovoSeme();
			for (int j = 0; j < tmp_semi.length; j ++) {
				ret[i] = tmp_semi[j];
				i ++;
			}
			ret[i] = sdk.getNuovoSemeCoda()[0];
			i ++;
		}
		
		ret[i] = gen.getProssimoSeme()[0];
		
		
		return ret;
	}
	
	
	public int getnClient() {
		return nClient;
	}
	
	public ArrayList<PcHC> getClientPC() {
		return clientPC;
	}
	
	public ArrayList<Terminale> getTerminali() {
		return terminali;
	}
	
	public ArrayList<WAN> getWan() {
		return wan;
	}

	public int getnServer() {
		return nServer;
	}

	public GW1 getGw1() {
		return gw1;
	}

	public GW2 getGw2() {
		return gw2;
	}

	public ArrayList<PcHS> getServerPC() {
		return serverPC;
	}

	public ArrayList<Disk> getServerDisk() {
		return serverDisk;
	}

	public double getProbabilitaDiramazione() throws GeneratoreException {
		return gen.getNext();
	}

	public ArrayList<LAN1> getLan1() {
		return lan1;
	}

	public ArrayList<LAN2> getLan2() {
		return lan2;
	}


	int nClient;
	int nServer;
	
	private Generatore gen;
	
	private ArrayList<PcHC> clientPC;
	private ArrayList<Terminale> terminali;
	
	private ArrayList<LAN1> lan1;
	private ArrayList<LAN2> lan2;
	
	private GW1 gw1;
	private GW2 gw2;
	
	private ArrayList<WAN> wan;
	
	private ArrayList<PcHS> serverPC;
	private ArrayList<Disk> serverDisk;
}
