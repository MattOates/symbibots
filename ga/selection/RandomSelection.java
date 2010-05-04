package ga.selection;

import java.util.LinkedList;

import sim.Agent;

import ga.GeneticAlgorithm;
import ga.Selection;

/**
 * An implementation of Random Sampling.
 * @author Matt Oates
 */
public class RandomSelection implements Selection {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public RandomSelection(){}
	
	
	
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
		return "Agents are selected completely randomly with no bias so it is pot luck what survives.";
	}

}
