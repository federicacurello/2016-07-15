package it.polito.tdp.flight.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento> {

	private LocalTime oraPartenza;
	private int nPasseggeri;
	private Airport partenza;
	private Airport destinazione;
	private Connessione volo;
	private TipoEvento tipo;
	
	public enum TipoEvento{
		PARTENZA,
		ARRIVO
	}
	
	
	
	public Evento(TipoEvento tipo, LocalTime oraPartenza, int nPasseggeri, Airport partenza) { //lo stato di partenza è lo stato di arrivo se l'evento è di tipo arrivo
		this.tipo= tipo;
		this.oraPartenza = oraPartenza;
		this.nPasseggeri = nPasseggeri;
		this.partenza=partenza;
	}


	@Override
	public int compareTo(Evento altro) {
		return this.oraPartenza.compareTo(altro.oraPartenza);
	}


	public LocalTime getOraPartenza() {
		return oraPartenza;
	}


	public void setOraPartenza(LocalTime oraPartenza) {
		this.oraPartenza = oraPartenza;
	}


	public int getnPasseggeri() {
		return nPasseggeri;
	}


	public void setnPasseggeri(int nPasseggeri) {
		this.nPasseggeri = nPasseggeri;
	}


	public Connessione getVolo() {
		return volo;
	}


	public void setVolo(Connessione volo) {
		this.volo = volo;
	}


	public Airport getPartenza() {
		return partenza;
	}


	public void setPartenza(Airport partenza) {
		this.partenza = partenza;
	}


	public Airport getDestinazione() {
		return destinazione;
	}


	public void setDestinazione(Airport destinazione) {
		this.destinazione = destinazione;
	}


	public TipoEvento getTipo() {
		return tipo;
	}


	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}
	
	

}
