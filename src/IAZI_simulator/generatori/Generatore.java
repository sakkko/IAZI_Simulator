package IAZI_simulator.generatori;

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
	
	public abstract double getNext();
	
	double media;
	double varianza;

}