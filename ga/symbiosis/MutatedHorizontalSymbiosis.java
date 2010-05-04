package ga.symbiosis;

import java.util.LinkedList;

import ga.Chromosome;
import ga.GeneticAlgorithm;
import ga.Symbiosis;
import game.Agent;

/**
 * An implementation of the Symbiosis operator. Identical to PerfectHorizontalSymbiosis
 * but with the introduction of mutating symbiont chromosomes each round.
 */
public class MutatedHorizontalSymbiosis implements Symbiosis {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public MutatedHorizontalSymbiosis(){}

	/**
	 * @see ga.Symbiosis#symbiose(java.util.LinkedList, java.util.LinkedList)
	 */
	public void symbiose(LinkedList<Agent> newPopulation, LinkedList<Agent> selectedPopulation) {
		LinkedList<Chromosome> symbionts = new LinkedList<Chromosome>();
		double averageFitness = 0.0;
		
		//Work out mean fitness for the selected population
		for (Agent agent: selectedPopulation) {
			averageFitness += agent.getFitness();
		}
		averageFitness /= selectedPopulation.size();
		
		/* If a selected agent is above average place its sexual & symbiotic chromosomes in the symbiont population.
		 * It's possible several of the same symbionts (but different instances) are now in the new symbiotic
		 * population, this gives bias towards succesful symbionts, and introduces ther previous host chromosomes
		 * into the shared population.*/
		for (Agent agent: selectedPopulation) {
			if (agent.getFitness() >= averageFitness) {
				symbionts.addAll(agent.getChromosomes());
			}
		}
		
		//Add a single random symbiont in to an Agent in the new population with the probability P(symbioticProbability)
		for (Agent agent: newPopulation) {
			if (GeneticAlgorithm.random() <= GeneticAlgorithm.symbioticProbability) {
				int randIndex = (int)Math.round(GeneticAlgorithm.random() * (symbionts.size()-1));
					//addChromosome clones for us
					Chromosome chromosome = agent.addChromosome(symbionts.get(randIndex));
					chromosome.mutate(GeneticAlgorithm.alleleMutationProbability, GeneticAlgorithm.geneMutationProbability);
			}
		}
	}

	/**
	 * @see ga.Symbiosis#toInfoString()
	 */
	public String toInfoString() {
		return "Single symbionts are mutated and added to the reproduced generation, symbionts are selected from above average sexual and symbiotic Chromosomes from the previous epoch.";
	}

}
