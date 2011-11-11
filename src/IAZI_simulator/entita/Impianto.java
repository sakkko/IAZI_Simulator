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
			terminali.add(new Terminale(semi[i]));
		}
		
		clientPC = new ArrayList<PcHC>(nClient);
		for (int k = 0; k < nClient; k ++) {
			clientPC.add(new PcHC(semi[i], semi[i + 1]));
			i += 2;
		}		
		
		lan1 = new ArrayList<LAN1>();
		for (int k = 0; k < nClient; k ++) {
			lan1.add(new LAN1(semi[i], semi[i + 1]));
			i += 2;
		}
		FineLAN1.setJobTerminati(0);
		
		gw1 = new GW1(semi[i], semi[i + 1]);
		i += 2;
		
		wan = new ArrayList<WAN>();
		tmpSemi = new long[4];
		for (int k = 0; k < nClient; k ++) {
			for (int j = 0; j < tmpSemi.length; j ++) {
				tmpSemi[j] = semi[i];
				i ++;
			}
			wan.add(new WAN(tmpSemi));
		}
				
		tmpSemi = new long[6];
		for (int j = 0; j < tmpSemi.length; j ++) {
			tmpSemi[j] = semi[i];
			i ++;
		}
		gw2 = new GW2(tmpSemi);
		
		lan2 = new ArrayList<LAN2>();
		for (int j = 0; j < nClient; j ++) {
			lan2.add(new LAN2(semi[i]));
			i ++;
		}
		
		
		serverPC = new ArrayList<PcHS>(nServer);
		for (int j = 0; j < nServer; j ++) {
			serverPC.add(new PcHS(semi[i], semi[i + 1]));
			i += 2;
		}
		 
		serverDisk = new ArrayList<Disk>(nServer);
		tmpSemi = new long[5];
		for (int k = 0; k < nServer; k ++) {
			for (int j = 0; j < tmpSemi.length; j ++) {
				tmpSemi[j] = semi[i];
				i ++;
			}
			serverDisk.add(new Disk(tmpSemi, semi[i]));
			i ++;
		}
			
		gen = new GeneratoreUniforme(semi[i]);
	
	}
	
	public long[] getNuoviSemi() {
		long[] ret = new long[153];
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
			ret[i] = ln2.getNuovoSeme();
			i ++;
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

	public double getProbabilitaDiramazione() {
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
