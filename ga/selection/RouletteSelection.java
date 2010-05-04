package ga.selection;

import java.util.Iterator;
import java.util.LinkedList;

import ga.GeneticAlgorithm;
import ga.Selection;
import game.Agent;
import game.World;

/**
 * An implementation of Fitness proportionate selection, total fitness in the population is calculated
 * and then a random fraction of this is used to select a specific individual to go through to the next
 * generation.
 * @author Matt Oates
 */
public class RouletteSelection implements Selection {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public RouletteSelection(){}
	
	/**
	 * The implementing selection method, the population is returned unaltered.
	 * @see ga.Selection#select(ga.GeneticAlgorithm)
	 */
	public LinkedList<Agent> select(GeneticAlgorithm ga) {
		LinkedList<Agent> population = ga.getPopulation();
		LinkedList<Agent> selected = new LinkedList<Agent>();
		
		double totFitness = 0.0;
		for (Agent agent : population) totFitness += agent.getFitness();
		if(World.IS_TEST) System.out.println("Total Fitness: " + totFitness);
		//For the amount of agents we want to select
		for (int i = 0; i < GeneticAlgorithm.populationSize; i++ ) {
			//Work out a random target on the roulette wheel
			int goal = (int) Math.round(totFitness * GeneticAlgorithm.random());
			if(World.IS_TEST)System.out.println("Selecting: #" + i + " With target at: " + goal);
			//Find the target we want within the population
			Iterator<Agent> iter = population.iterator();
			Agent agent = null;
			
			while (goal >= 0 && iter.hasNext()) {
				agent = iter.next();
				goal -= agent.getFitness();
				if(World.IS_TEST)System.out.println("Target amount left: " + goal);
			}
			selected.add(agent);
		}
		return selected;
	}

	/**
	 * A description of this selection method.
	 * @see ga.Selection#toInfoString()
	 */
	public String toInfoString() {
		return "A basic Roulette Selection, has some bias towards elite Agents.";
	}

}
