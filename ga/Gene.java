package ga;

import sim.Agent;
import ui.GenePanel;

/**
 * The abstract Gene defenition, defines what a Gene must be able to do.
 * @author  Matt Oates
 */
public abstract class Gene implements Cloneable {
	
	private int allele;
	
	private int expression;
	
	/**
	 * Handle on a GUI panel for this gene if it exists, makesure we don't serialize this.
	 */
	private transient GenePanel panel = null;
	
	/**
	 * Default constructor, used when opening up an initial random gene.
	 */
	public Gene () {
		super();
		allele = (int) Math.round(255 * GeneticAlgorithm.random());
		expression = allele;
	}
	
	/**
	 * Construct a gene with a specific allele.
	 */
	public Gene (int allele) {
		this.allele = allele;
	}
	
	/**
	 * Is this gene of the same implementing type and allele.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (! (object instanceof Gene)) return false;
		Gene gene = (Gene)object;
		if (gene.getName() != getName()) return false;
		if (gene.getAllele() != getAllele()) return false;
		return true; 
	}
	
	/**
	 * All Gene implementations must be able to express their phenotype on an Agent or another Gene.
	 * @param agent The agent to express the phenotype on.
	 * @param next The next gene to express the phenotype on.
	 */
	public abstract void express(Agent agent, Gene next);
	
	/**
	 * All Gene implementations must give a textual description of what they do suitable for future use in the GUI.
	 * @return A String description of the genes phenotype.
	 */
	public abstract String toInfoString();
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName().replace("Gene", "") + Integer.toHexString(allele) + "[" + Integer.toHexString(expression) + "]";
	}
	
	/**
	 * Creates a deep copy of this gene as if it was new.
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Gene gene = (Gene)super.clone();
		//Reset the gene to its initial state
		gene.expression = gene.allele;
		//Need a new panel since expression will likely be different for the cloned gene.
		gene.panel = null;
		return gene;
	}
	
	/**
	 * Convenience method for getting the simple class name of the implementing gene class.
	 * @return The class name as a String.
	 */
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * Reset this gene to its expressive starting state. The expression value is set back to the genes allele.
	 * @see ga.Chromosome#reset()
	 */
	public void reset() {
		expression = allele;
		if (panel != null) panel.updateData();
	}
	
	/**
	 * @return  the allele
	 */
	public int getAllele() {
		return allele;
	}
	/**
	 * @param allele  the allele to set
	 */
	public void setAllele(int allele) {
		this.allele = allele & 0xff;
		if (panel != null) panel.updateData();
	}
	/**
	 * @return  the expression
	 */
	public int getExpression() {
		return expression;
	}
	/**
	 * @param expression  the expression to set
	 */
	public void setExpression(int expression) {
		this.expression = expression & 0xff;
		if (panel != null) panel.updateData();
	}

	/**
	 * @return the panel
	 */
	public GenePanel getPanel() {
		//Create a panel if one doesn't exist, deals with it being transient
		if (panel == null) setPanel(new GenePanel(this));
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public synchronized void setPanel(GenePanel panel) {
		this.panel = panel;
	}
	
}
