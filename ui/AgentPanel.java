package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

import ga.Chromosome;
import ga.Gene;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import sim.Agent;
import sim.agents.UserAgent;

/**
 * The main Agent GUI widget, created and maintained by the respective agent.
 * @author Matt Oates
 */
public class AgentPanel extends JPanel implements TreeSelectionListener, ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6900824116059210813L;

	private Agent agent;
	
	private JTree chromosomes;
	
	private JSplitPane geneticPane;
	
	private AgentInfoPanel infoPanel;
	
	private SidePanel parentPanel;
	
	private JScrollPane chromosomesView;
	
	private DefaultMutableTreeNode root;
	
	public AgentPanel (Agent agent) {
		super(new GridLayout(0,1));
		this.agent = agent;
		
		//Setup the infoPanel
		infoPanel = new AgentInfoPanel(agent, this);
		
		//View the first Gene as default
		GenePanel geneView = agent.getChromosome().getGenes().getFirst().getPanel();
		
		//Create the nodes.
		if (agent instanceof UserAgent)
        	root = new DefaultMutableTreeNode("User Chromosome");
		else
			root = new DefaultMutableTreeNode("Chromosomes");
        createNodes(root);
        chromosomes = new JTree(root);
        chromosomes.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        chromosomes.addTreeSelectionListener(this);
		
        chromosomesView = new JScrollPane(chromosomes);
        chromosomesView.setMinimumSize(new Dimension(250, 200));
        chromosomesView.setPreferredSize(new Dimension(250, 200));
        
        //Put the chromosome tree view and the gene view in a split pane.
        geneticPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        geneticPane.setDividerLocation(100);
        geneticPane.setTopComponent(geneView);
        geneticPane.setBottomComponent(chromosomesView);
        
        
        
        //JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        add(infoPanel);
        add(geneticPane);
	}

	/**
	 * Setup the Chromosome and Gene tree for this Agent.
	 * @param root
	 */
	private void createNodes(DefaultMutableTreeNode root) {
		DefaultMutableTreeNode chromosomeNode = null;
        DefaultMutableTreeNode geneNode = null;
        if (agent instanceof UserAgent)
        	chromosomeNode = new DefaultMutableTreeNode("All Genes");
        else
        	chromosomeNode = new DefaultMutableTreeNode("Sexual");
        root.add(chromosomeNode);
        for (Gene gene: agent.getChromosome().getGenes()) {
        	geneNode = new DefaultMutableTreeNode(gene);
        	chromosomeNode.add(geneNode);
        }
        
        List<Chromosome> symbionts = agent.getSymbioticChromosomes();
        if (symbionts != null) {   
	        ListIterator<Chromosome> iter = symbionts.listIterator();
	        while (iter.hasNext()) {
	        	Chromosome chromosome = iter.next();
	        	chromosomeNode = new DefaultMutableTreeNode("Symbiotic "+iter.nextIndex());
	            root.add(chromosomeNode);
	            
	            for (Gene gene: chromosome.getGenes()) {
	            	geneNode = new DefaultMutableTreeNode(gene);
	            	chromosomeNode.add(geneNode);
	            }
	        }
        }
	}
	
	/**
	 * Handle chromosome tree click events.
	 */
	public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = 
        	(DefaultMutableTreeNode) chromosomes.getLastSelectedPathComponent();

        if (node == null) return;
        Gene gene = (Gene)node.getUserObject();
        if (node.isLeaf()) {
            geneticPane.setTopComponent(gene.getPanel());
        }
	}
	
	/**
	 * Update information on changes in the agent
	 */
	public synchronized void updateData() {
		infoPanel.updateData();
		infoPanel.repaint();
	}
	
	public synchronized void updateTree() {
		if (agent instanceof UserAgent)
        	root = new DefaultMutableTreeNode("User Chromosome");
		else
			root = new DefaultMutableTreeNode("Chromosomes");
		createNodes(root);
	    chromosomes = new JTree(root);
	    chromosomes.getSelectionModel().setSelectionMode
	            (TreeSelectionModel.SINGLE_TREE_SELECTION);
	    chromosomes.addTreeSelectionListener(this);
		
	    geneticPane.remove(chromosomesView);	    
	    chromosomesView = new JScrollPane(chromosomes);
	    chromosomesView.setMinimumSize(new Dimension(250, 200));
	    chromosomesView.setPreferredSize(new Dimension(250, 200));
        geneticPane.setBottomComponent(chromosomesView);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Close Agent View") {
			parentPanel.removeAgent(agent);
		}		
	}

	/**
	 * @return the agent
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	/**
	 * @return the chromosomes
	 */
	public JTree getChromosomes() {
		return chromosomes;
	}

	/**
	 * @param chromosomes the chromosomes to set
	 */
	public void setChromosomes(JTree chromosomes) {
		this.chromosomes = chromosomes;
	}

	/**
	 * @return the geneticPane
	 */
	public JSplitPane getGeneticPane() {
		return geneticPane;
	}

	/**
	 * @param geneticPane the geneticPane to set
	 */
	public void setGeneticPane(JSplitPane geneticPane) {
		this.geneticPane = geneticPane;
	}

	/**
	 * @return the infoPanel
	 */
	public AgentInfoPanel getInfoPanel() {
		return infoPanel;
	}

	/**
	 * @param infoPanel the infoPanel to set
	 */
	public void setInfoPanel(AgentInfoPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	/**
	 * @return the parentPanel
	 */
	public SidePanel getParentPanel() {
		return parentPanel;
	}

	/**
	 * @param parentPanel the parentPanel to set
	 */
	public void setParentPanel(SidePanel parentPanel) {
		this.parentPanel = parentPanel;
	}
	
}