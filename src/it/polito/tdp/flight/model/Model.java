package it.polito.tdp.flight.model;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;


import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	FlightDAO dao;
	Map<Integer, Airport> air;
	List<Connessione> connessioni;
	Simulatore sim;

	public Model() {
		this.grafo = new SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		dao= new FlightDAO();
		air= new HashMap<Integer, Airport>();
		sim= new Simulatore();
	}

	public void creaGrafo(double distanzaMax) {
		Graphs.addAllVertices(grafo, dao.getAllAirports(air));
		connessioni=dao.trovaArchi(distanzaMax, air);
		for(Connessione c: dao.trovaArchi(distanzaMax, air)) {
			Graphs.addEdge(grafo, c.getPartenza(), c.getArrivo(), c.getDurata());
		}
		System.out.println(grafo.vertexSet().size()+" vertici e "+ grafo.edgeSet().size()+
				" archi");
	}

	public boolean testRaggiungibili() {
		List<Airport> raggiunti= new LinkedList<Airport>();
		List<Airport> daRaggiungere= new LinkedList<Airport>();
		for(Airport a: grafo.vertexSet()) {
			if(Graphs.neighborListOf(grafo, a).size()!=0) {
				daRaggiungere.add(a);
				GraphIterator<Airport, DefaultWeightedEdge> it= new BreadthFirstIterator<>(grafo, a);
				while(it.hasNext()) {
					raggiunti.add(it.next());
				}
			if(raggiunti.size()!= daRaggiungere.size())
				return false;
		}
		}
	
		
			return true;
		
		
			
}

public Airport piùLontanoDaFiumicino() {
	List<Airport> raggiunti= new LinkedList<Airport>();
	double distanzaMax=0;
	Airport a= null;

	GraphIterator<Airport, DefaultWeightedEdge> it= new BreadthFirstIterator<>(grafo, this.getFiumicino());
			while(it.hasNext()) {
				Airport next= it.next();
				double distanza= LatLngTool.distance(this.getFiumicino().getCoords(), next.getCoords(), LengthUnit.KILOMETER );
				raggiunti.add(next);
				if(distanza>distanzaMax) {
					distanzaMax=distanza;
					a= next;
				}
			}
		
	
	return a;
	
}
public Airport getFiumicino() {
	Airport res= null;
	for(Airport a: grafo.vertexSet()) {
		if (a.getName().equals("Fiumicino"))
			res= a;
	}
	return res;
}
public void simula(int k) {
	sim.init(grafo, k);
	sim.run();
}

public String getStanziali() {
	String s="\n";
	for(Airport a: sim.getMap().keySet()) {
		if(sim.getMap().get(a)!=0)
		s+= a.getName()+ " numero di persone: "+ sim.getMap().get(a)+"\n";
	}
	return s;
}
}
