package ga;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * An abstract factory for getting hold of implementing Gene objects.
 * Genes from the genes package can be instantiated by name or at random.
 * A listing of all currently available genes can be retreived to. Currently all reflection exceptions are supressed.
 * @author Matt Oates
 * @see ga.genes
 */
public abstract class GeneFactory {
	
	/**
	 * Retrieve a list of instantiated Gene examples from the genes package.
	 * @return An ArrayList of Gene objects.
	 */
	public static ArrayList<Gene> getGeneList() {
		Gene gene = null;
		ArrayList<Gene> list = new ArrayList<Gene>();
		File genespkg = new File("ga"+File.separator+"genes");
		//Get a list of all the GeneClasses available in the Genes package.
		String[] genes = genespkg.list(new GeneFilter());
		
		for ( String genename : genes ) {
			genename = genename.replaceAll("\\.java", "");
			
			try {
				gene = (Gene) Class.forName("ga.genes." + genename, true, Thread.currentThread().getContextClassLoader()).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(gene);
		}
		return list;
	}
	
	/**
	 * Get a gene implementation by name.
	 * @param name The class name that should be instantiated.
	 * @return An instantiated copy of the gene requested.
	 */
	public static Gene getGene(String name) {
		Gene gene = null;
		if (! name.endsWith("Gene")) name += "Gene";
		
		try {
			gene = (Gene) Class.forName("ga.genes." + name, true, Thread.currentThread().getContextClassLoader()).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gene;		
	}
	
	/**
	 * Get a gene by name and set its allele to a specified value.
	 * @param name The class name of the implementing class to retrieve.
	 * @param allele The allele to be set.
	 * @return A new instance of the requested gene.
	 */
	public static Gene getGene(String name, int allele) {
		Gene gene = null;
		if (! name.endsWith("Gene")) name += "Gene";
		
		try {
			gene = (Gene) Class.forName("ga.genes." + name, true, Thread.currentThread().getContextClassLoader()).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gene.setAllele(allele);
		gene.setExpression(allele);
		return gene;		
	}
	
	/**
	 * From all the available gene implementations return a new random instance of one.
	 * @return The new gene instance.
	 */
	public static Gene getRandomGene() {
		Gene gene = null;
		String genename = "";
		File genespkg = new File("ga"+File.separator+"genes");

		//Get a list of all the GeneClasses available in the Genes package.
		String[] genes = genespkg.list(new GeneFilter());
		
		//Get a random GeneClass name from that list.
		int index = (int) Math.round((genes.length - 1) * GeneticAlgorithm.random());
		//System.out.println(index);
		//System.out.println("Number of genes found: " + genes.length);
		genename = genes[index].replaceAll("\\.java", "");
		//System.out.println("Getting an instance of " + genename);
		
		try {
			gene = (Gene) Class.forName("ga.genes." + genename, true, Thread.currentThread().getContextClassLoader()).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gene;
	}	
}

/**
 * An inner class for filtering Gene implementations from other classes that might be present in the ga.genes package.
 * All Gene implementations must have the Gene postfix.
 */
class GeneFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith("Gene.java"));
    }
}
