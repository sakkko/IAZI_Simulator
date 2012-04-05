package IAZI_simulator.generatori;

import IAZI_simulator.exception.GeneratoreException;


public abstract class Generatore {
	
	public double getMedia() {
		return media;
	}
	
	public void setMedia(double media) {
		this.media = media;
	}
	
	public double getVarianza() {
		return varianza;
	}
	
	public void setVarianza(double varianza) {
		this.varianza = varianza;
	}
	
	public double getTa() {
		return 0;
	}
	
	public abstract double getNext() throws GeneratoreException;
	
	public abstract long[] getProssimoSeme() throws GeneratoreException;


	double media;
	double varianza;

}