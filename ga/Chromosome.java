package ga;

import game.Agent;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * A Chromosome acted upon by the GA, an Agent contains a LinkedList of these ojects
 * as its "genome". The Chromosome is a wrapper around a LinkedList of Genes.
 * @author  Matt Oates
 * @see ga.Gene
 * @see game.Agent
 */
public class Chromosome implements Cloneable {
	
	private LinkedList<Gene> genes = new LinkedList<Gene>();
	
	private Agent parent;
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public Chromosome() {
		super();
	}
	
	/**
	 * Default constructor used in XML serialization.
	 * @param parent The parent Agent this Chromosome acts on.
	 */
	public Chromosome(Agent parent) {
		super();
		this.parent = parent;
		
		//Get a chromosome of random genes for an initial population.
		for (int index = 0; index < GeneticAlgorithm.chromosomeSize; index++) {
			genes.add(GeneFactory.getRandomGene());			
		}
	}
	
	/**
	 * Overridden implementation of equals that checks if all of this chromosomes genes are semantically equal.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Chromosome)) return false;
		Chromosome chromosome = (Chromosome) object;
		ListIterator iter = chromosome.getGenes().listIterator();
		while (iter.hasNext()) {
			int index = iter.nextIndex();
			Gene gene = (Gene)iter.next();
			if (!gene.equals(genes.get(index))) return false;
		}
		
		return true;
	}
	
	/**
	 * Overridden clone implementation, gets a deep copy of this chromosome, calls the clone in Gene.
	 * @return A deep copy of this chromosome object.
	 * @see java.lang.Object#clone()
	 * @see ga.Gene#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Chromosome chromosome = new Chromosome();
		for (Gene gene : genes) {
			chromosome.addGene((Gene)gene.clone());
		}
		return chromosome;
	}
	
	/**
	 * Express this chromosomes current phenotype on to its parent agent.
	 */
	public void express() {
		ListIterator<Gene> geneiter = genes.listIterator();
		while (geneiter.hasNext()) {
			Gene next = geneiter.next();
			//If there is a next Gene to the "right" of this one pass that in.
			if (geneiter.hasNext())
				next.express(parent, genes.get(geneiter.nextIndex()));
			else
				//Otherwise loop around to the begining of the chromosome
				next.express(parent, genes.get(0));
		}
	}
	
	/**
	 * Resets the chromosome to its start round state, using a delegate method all gene expression values are returned to the allele value.
	 * @see ga.Gene#reset()
	 */
	public void reset() {
		for (Gene gene : genes) gene.reset();
	}
	
	/**
	 * Attempt to mutate each gene in the chromosome, both the gene phenotype and default level of
	 * expression will be effected.
	 * @param probability The probability of succesfully mutating each gene.
	 */
	public void mutate(double probability) {
		ListIterator<Gene> geneiter = genes.listIterator();
		
		while (geneiter.hasNext()) {
			Gene gene = geneiter.next();
			//Do we mutate?
			if (probability != 0 && GeneticAlgorithm.random() <= probability) {
				int allele = gene.getAllele();
				gene = GeneFactory.getRandomGene();
				gene.setAllele(allele);
				//Replace the old gene with the new one with the same allele number
				geneiter.set(gene);			
				gene.setAllele((int)Math.round(GeneticAlgorithm.random() * 255));
			}
		}
	}
	
	/**
	 * Attempt to mutate each gene in the chromosome, both the gene phenotype and default level of
	 * expression will be effected. The reason to do these seperately is that promotion and supression
	 * of the genes expresssion is specific to the allele value and not the penotype. So the balance of
	 * expression within a chromosome can evolve seperately from the specific phenotype. You still want
	 * to do both since a different expression level might be better suited to a specific phenotype.
	 * @param probAllele The probability of succesfully mutating the allele or default level
	 *                   of expression for each gene.
	 * @param probGene The probability of succesfully mutating the gene phenotype.
	 */
	public void mutate(double probAllele, double probGene) {
		ListIterator<Gene> geneiter = genes.listIterator();
		
		while (geneiter.hasNext()) {
			Gene gene = geneiter.next();
			//Do we mutate gene?
			if (probGene != 0 && GeneticAlgorithm.random() <= probGene ) {
				int allele = gene.getAllele();
				gene = GeneFactory.getRandomGene();
				gene.setAllele(allele);
				//Replace the old gene with the new one with the same allele number
				geneiter.set(gene);
			}
			
			//Do we mutate alleles?
			if (probAllele != 0 && GeneticAlgorithm.random() <= probAllele ) {
				gene.setAllele((int)Math.round(GeneticAlgorithm.random() * 255));
			}
			//Reset the gene expression to be the new or old default state
			gene.reset();
		}
	}
	
	/**
	 * Attempt to mutate each gene in the chromosome, only the default level of
	 * expression will be effected. 
	 * @param probability
	 */
	public void mutateAlleles(double probability) {
		for (Gene gene : genes) {
			//Do we mutate?
			if (probability != 0 && GeneticAlgorithm.random() <= probability) {
				gene.setAllele((int)Math.round(GeneticAlgorithm.random() * 255));
			}
		}
	}
	
	/**
	 * Attempt to mutate each gene in the chromosome, only the gene phenotype will be effected. 
	 * @param probability
	 */
	public void mutateGenes(double probability) {
		ListIterator<Gene> geneiter = genes.listIterator();
		
		while (geneiter.hasNext()) {
			Gene gene = geneiter.next();
			//Do we mutate?
			if (probability != 0 && GeneticAlgorithm.random() <= probability) {
				int allele = gene.getAllele();
				gene = GeneFactory.getRandomGene();
				gene.setAllele(allele);
				geneiter.set(gene);
			}
		}
	}
	
	/**
	 * Return a default UserAgent Chromosome, for now set this all to VoidGene.
	 * @param parent The UserAgent this chromosome is for.
	 * @return chromosome The UserAgents default chromosome.
	 */
	public static Chromosome getDefaultUserChromosome() {
		Chromosome chromosome = new Chromosome();
		for (Gene gene: GeneFactory.getGeneList()) {
			gene.setAllele(0);
			gene.setExpression(0);
			chromosome.addGene(gene);
		}
		return chromosome;
	}

	/**
	 * Overridden toString, prints out the chromosome as a list of simple class names and hex allele values.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		string.append("{");
		for(Gene gene : genes) {
			string.append(gene);
			string.append(", ");
		}
		string.append("}");
		return string.toString();
	}

	/**
	 * @return  the genes
	 */
	public LinkedList<Gene> getGenes() {
		return genes;
	}

	/**
	 * @param genes  the genes to set
	 */
	public void setGenes(LinkedList<Gene> genes) {
		this.genes = genes;
	}

	/**
	 * @return  the parent
	 */
	public Agent getParent() {
		return parent;
	}

	/**
	 * @param parent  the parent to set
	 */
	public void setParent(Agent parent) {
		this.parent = parent;
	}
	
	/**
	 * @return the size
	 */
	public int getSize() {
		return genes.size();
	}
	
	/**
	 * @return get a specific gene
	 */
	public Gene getGene(int index) {
		return genes.get(index);
	}
	
	/**
	 * Add a gene object to this chromosomes list.
	 */
	public void addGene(Gene gene) {
		genes.add(gene);
	}

}
