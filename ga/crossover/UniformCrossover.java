package ga.crossover;

import sim.World;
import ga.Chromosome;
import ga.Crossover;
import ga.Gene;
import ga.GeneticAlgorithm;

/**
 * An implementation of single point crossover, a single loci is found on both 
 * chromosomes and their tails are swapped.
 * @author Matt Oates
 */
public class UniformCrossover implements Crossover {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public UniformCrossover() {}
	
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
		return "Uniform crossover. Every other gene in each chromosome is swapped. This is the most destructive form of crossing. Not useful if your genes are highly coupled!";
	}

}
