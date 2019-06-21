package it.polito.tdp.flight.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.flight.model.Evento.TipoEvento;

public class Simulatore {
	
	private SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	private PriorityQueue<Evento> queue;
	
	//Parametri della simulazione
	private int k;
	private LocalTime oraInizio= LocalTime.of(6,0);
	private LocalTime oraFine= oraInizio.plusHours(48);
	private LocalTime oraInizioVoli= LocalTime.of(2,0);
	private LocalTime oraFineVoli= LocalTime.of(23,0);
	private Duration traVoli= Duration.ofHours(2);
	

	//Valori in output
	private Map<Airport, Integer> map;
	
	public Map<Airport, Integer> getMap() {
		return map;
	}
	public void setMap(Map<Airport, Integer> map) {
		this.map = map;
	}
	//Variabili interne
	private Random rand= new Random();

	public Simulatore() {

		
		this.map = new HashMap<Airport, Integer>();
	} 
	public void init(SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo, int k) {
		this.grafo=grafo;
		this.k=k;
		int totali=k;
		
		for(int i=0; i<k; i++) {
			List<Airport> ae = new LinkedList<Airport>( grafo.vertexSet());
			int posizionati= rand.nextInt(totali);
			int index= rand.nextInt(ae.size());
			if(!map.containsKey(ae.get(index))) {
				map.put(ae.get(index), posizionati);
				totali= totali-posizionati;
			}
		}
		this.queue = new PriorityQueue<Evento>();
		
		//inserisco gli eventi in coda
		for( LocalTime ora= oraInizio; ora.isBefore(oraFine); ora=ora.plus(traVoli) ) {
			if(ora.isAfter(oraInizioVoli) && ora.isBefore(oraFineVoli)){
				for(Airport a: grafo.vertexSet()) {
			
				queue.add(new Evento(TipoEvento.PARTENZA, ora, (map.get(a)), a));
		
		}}}
		}
	public void run() {
		Evento e;
		while((e = queue.poll())!= null) {
			switch(e.getTipo()){
			case PARTENZA:
				int partiti= e.getnPasseggeri();
				map.put(e.getPartenza(), map.get(e.getPartenza())-partiti );
				for(DefaultWeightedEdge c: grafo.edgeSet()) {
					if(grafo.getEdgeSource(c).equals(e.getPartenza()) && grafo.getEdgeWeight(c)<2) {
						queue.add(new Evento(TipoEvento.ARRIVO, e.getOraPartenza().plusHours((long) grafo.getEdgeWeight(c)), partiti,  grafo.getEdgeTarget(c)));
						}
					}
				
				break;
			
			case ARRIVO:
				int arrivati=e.getnPasseggeri();
				map.put(e.getPartenza(), map.get(e.getPartenza())+ arrivati);
				
				break;
		}
	}
		
	
	
	
}}
