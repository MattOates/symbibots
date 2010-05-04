package ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import ga.Gene;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A GUI widget created and maintained by its respective Gene. Used to edit the genes expression and allele
 * @author Matt Oates
 * @see ga.Gene
 */
public class GenePanel extends JPanel implements ChangeListener {
	
	private static final long serialVersionUID = 3949514140168721189L;

	private Gene gene;
		
	private JSpinner allele;
	
	private JSpinner expression;
	
	private boolean isEditable = false;
	
	public GenePanel (Gene gene) {
		this.gene = gene;
		allele = new JSpinner(new SpinnerNumberModel(gene.getAllele(), 0, 0xff, 1));
		allele.addChangeListener(this);
		expression = new JSpinner(new SpinnerNumberModel(gene.getExpression(), 0, 0xff, 1));
		expression.addChangeListener(this);
		
		this.setLayout(new GridLayout(2,0));
		
		TitledBorder title = BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
					gene.getClass().getSimpleName());
		title.setTitleJustification(TitledBorder.LEFT);
		this.setBorder(title);
		JLabel label = new JLabel("Allele:");
		label.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(label);
		this.add(allele);
		label = new JLabel("Expressing:");
		label.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(label);
		this.add(expression);
		setMinimumSize(new Dimension(300, 100));
		setPreferredSize(new Dimension(300, 100));
	}
	
	public GenePanel (Gene gene, boolean isEditable) {
		this(gene);
		setEditable(isEditable);
	}

	/**
	 * @param isEditable
	 */
	private void setEditable(boolean isEditable) {
		if (isEditable != this.isEditable) {
			allele.setEnabled(isEditable);
			expression.setEnabled(isEditable);
		}
	}
	
	/**
	 * @return the isEditable
	 */
	public boolean isEditable() {
		return isEditable;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == allele) {
			gene.setAllele((int)(Integer)allele.getValue());
		}
		else if (e.getSource() == expression) {
			gene.setExpression((int)(Integer)expression.getValue());
		}
	}

	/**
	 * 
	 */
	public synchronized void updateData() {
		allele.setValue(gene.getAllele());
		expression.setValue(gene.getExpression());
	}
	
}
