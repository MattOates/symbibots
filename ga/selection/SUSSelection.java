package ga.selection;

import java.util.LinkedList;

import sim.Agent;

import ga.GeneticAlgorithm;
import ga.Selection;

/**
 * An implementation of Stochastic Universal Sampling.
 * @author Matt Oates
 */
public class SUSSelection implements Selection {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public SUSSelection(){}
	
	
	
	/**
	 * @see ga.Selection#select(ga.GeneticAlgorithm)
	 */
	public LinkedList<Agent> select(GeneticAlgorithm ga) {
		LinkedList<Agent> population = ga.getPopulation();
		LinkedList<Agent> selected = new LinkedList<Agent>();
				
		while (selected.size() < GeneticAlgorithm.populationSize) {
			//Select here
		}
		
		return selected;
	}

	/**
	 * @see ga.Selection#toInfoString()
	 */
	public String toInfoString() {
		return "An implementation of Stochastic Universal Sampling. Agents are selected proportional to fitness with no bias towards a single agent. Similar to Roulette apart from their is a better chance of getting less fit agents selected.";
	}

}
