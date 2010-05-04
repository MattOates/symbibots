package ga.crossover;

import sim.World;
import ga.Chromosome;
import ga.Crossover;
import ga.Gene;
import ga.GeneticAlgorithm;

/**
 * An implementation of cut and splice crossover, a single but differing loci is found on each 
 * chromosomes and their tails are swapped. Allows for growth and contraction of a chromosome.
 * @author Matt Oates
 */
public class CutAndSpliceCrossover implements Crossover {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public CutAndSpliceCrossover() {}
	
	/**
	 * Implemented crossover method.
	 * @see ga.Crossover#cross(ga.Chromosome, ga.Chromosome)
	 */
	public Chromosome[] cross(Chromosome mother, Chromosome father) {
		Chromosome[] children = new Chromosome[2];
			children[0] = new Chromosome();
			children[1] = new Chromosome();
		int size = mother.getSize();
		int locus = (int) Math.round(GeneticAlgorithm.random() * (size -1));
		
		if(World.IS_TEST) System.out.println("Single Point Crossover, locus is: "+locus);
		
		for (int index = 0; index < size; index++) {
			if (index < locus) {
				try {
					children[0].addGene((Gene)mother.getGene(index).clone());
					children[1].addGene((Gene)father.getGene(index).clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					children[0].addGene((Gene)father.getGene(index).clone());
					children[1].addGene((Gene)mother.getGene(index).clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}	
			}
		}	
		
		if(World.IS_TEST)System.out.println("Child 0: "+children[0]);
		if(World.IS_TEST)System.out.println("Child 1: "+children[1]);
		
		return children;
	}

	/** 
	 * A description of this crossover method.
	 * @see ga.Crossover#toInfoString()
	 */
	public String toInfoString() {
		return "Cut and Splice crossover. A random loci is chosen for each chromosome and their tails are swapped forming two new Chromosomes.";
	}

}
