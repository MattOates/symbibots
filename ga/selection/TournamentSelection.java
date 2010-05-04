package ga.selection;

import java.util.LinkedList;

import ga.GeneticAlgorithm;
import ga.Selection;
import game.Agent;

/**
 * An implementation of Tournament selection. A random Tour of agents is selected from the population.
 * Tour size is set to a quarter of the population, this could be altered at a later date. Each agent in the tour is compared for fitness.
 * The most fit in a Tour group go through to the next generation to reproduce. This is repeated until enough Agents have been selected.
 * @author Matt Oates
 */
public class TournamentSelection implements Selection {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public TournamentSelection(){}
	
	/**
	 * Tour size used in selection
	 */
	public static int tourSize = (int)(GeneticAlgorithm.populationSize /4);
	
	/**
	 * @see ga.Selection#select(ga.GeneticAlgorithm)
	 */
	public LinkedList<Agent> select(GeneticAlgorithm ga) {
		LinkedList<Agent> population = ga.getPopulation();
		LinkedList<Agent> selected = new LinkedList<Agent>();
		LinkedList<Agent> tour = new LinkedList<Agent>();
				
		while (selected.size() < GeneticAlgorithm.populationSize) {
			
			//Get a Tour of size tourSize from a random sample of the population
			for (int i=0; i < tourSize; i++) {
				int index = (int)Math.round(GeneticAlgorithm.random() * (population.size()-1));
				tour.add(population.get(index));
			}
			
			Agent winner = tour.getFirst();
			
			//Carry out a tournament on the tour
			for (Agent candidate : tour) {
				if (candidate.getFitness() > winner.getFitness()) {
					winner = candidate;
				}
			}
			
			selected.add(winner);
			tour.clear();
			
		}
		
		return selected;
	}

	/**
	 * @see ga.Selection#toInfoString()
	 */
	public String toInfoString() {
		return "A Tour a quarter of the size of the population is chosen randomly. The best individual from this group is selected by a tournament comparisson and goes on to reproduce in the next generation.";
	}

}
