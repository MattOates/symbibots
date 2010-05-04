package ui;

import ga.Crossover;
import ga.GeneticAlgorithm;
import ga.GeneticAlgorithmFactory;
import ga.Selection;
import ga.Symbiosis;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.*;

/**
 * The GA editor wizard dialog.
 * @author Matt Oates
 *
 */
public class GADialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 2894591644769820635L;

	private JPanel wizard = new JPanel();
	
	private JPanel form;
	
	private Hashtable<String, Selection> selects  = GeneticAlgorithmFactory.getSelectionHash();
	private JComboBox selection = new JComboBox(selects.keySet().toArray());
	private JTextArea selectinfo = new JTextArea( 
			selects.get(selection.getSelectedItem()).toInfoString()
			,3,30);
	
	private	Hashtable<String, Crossover> crosses  = GeneticAlgorithmFactory.getCrossoverHash();
	private JComboBox crossover = new JComboBox(crosses.keySet().toArray());
	private JTextArea crossinfo = new JTextArea(
			crosses.get(crossover.getSelectedItem()).toInfoString()
			,3,30);
	
	private	Hashtable<String, Symbiosis> symbis  = GeneticAlgorithmFactory.getSymbiosisHash();
	private JComboBox symbiosis = new JComboBox(symbis.keySet().toArray());
	private JTextArea symbinfo = new JTextArea(
			symbis.get(symbiosis.getSelectedItem()).toInfoString()
			,3,30);
	
	private JSpinner crossoverProbability = new JSpinner(new SpinnerNumberModel(GeneticAlgorithm.crossoverProbability, 0.0, 1.0, 0.01)); 
	private JSpinner geneMutationProbability = new JSpinner(new SpinnerNumberModel(GeneticAlgorithm.geneMutationProbability, 0.0, 1.0, 0.01));
	private JSpinner alleleMutationProbability = new JSpinner(new SpinnerNumberModel(GeneticAlgorithm.alleleMutationProbability, 0.0, 1.0, 0.01));
	private JSpinner symbioticProbability = new JSpinner(new SpinnerNumberModel(GeneticAlgorithm.symbioticProbability, 0.0, 1.0, 0.01));

	private boolean accepted = false;
	
	public GADialog(Frame frame) {
		super (frame, true);
		this.setLayout(new BorderLayout());
		this.add(new ImagePanel("images/gaw_title.png", false, false), BorderLayout.NORTH);
		ImagePanel temp = new ImagePanel("images/gaw_side_bar.png", false, false);
		temp.setBackground(Color.BLACK);
		this.add(temp, BorderLayout.WEST);
		wizard.setLayout(new CardLayout());
		this.add(wizard, BorderLayout.CENTER);
		
		setResizable(false);
		
		//Setup wizard controls
		JButton next = new JButton("Next");
		next.setIcon(new ImageIcon("images/next.png"));
		next.addActionListener(this);
		
		JButton back = new JButton("Back");
		back.setIcon(new ImageIcon("images/back.png"));
		back.addActionListener(this);
		
		JButton accept = new JButton("Accept");
		accept.setIcon(new ImageIcon("images/accept.png"));
		accept.addActionListener(this);
		
		//Setup first page of the wizard, setting GA operators
		form = new JPanel();
		form.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		//Setup selection
		selection.addActionListener(this);
		selection.setActionCommand("Selection");
		//Set default selection
		selection.setSelectedItem("Tournament");
		
		//Setup crossover
		crossover.addActionListener(this);
		crossover.setActionCommand("Crossover");
		//Set default crossover
		crossover.setSelectedItem("SinglePoint");
		
		//Setup symbiosis
		symbiosis.addActionListener(this);
		symbiosis.setActionCommand("Symbiosis");
		//Set default crossover
		symbiosis.setSelectedItem("PerfectHorizontal");
		
		JTextArea intro = new JTextArea("Symbibots uses a Genetic Algorithm to create waves of enemies for you to fight. If you know about Genetic Algorithms or want to fiddle with the workings of the game this is your chance! Otherwise the defaults should work just fine, click 'Next' then 'Accept' and you are set to go.");
		intro.setEditable(false);
        intro.setLineWrap(true);
        intro.setWrapStyleWord(true);
        intro.setPreferredSize(new Dimension(600,75));
        intro.setBackground(getParent().getBackground());
        
        selectinfo.setEditable(false);
        selectinfo.setLineWrap(true);
        selectinfo.setWrapStyleWord(true);
        selectinfo.setPreferredSize(new Dimension(200,75));
        selectinfo.setBackground(getParent().getBackground());
        
        crossinfo.setEditable(false);
        crossinfo.setLineWrap(true);
        crossinfo.setWrapStyleWord(true);
        crossinfo.setPreferredSize(new Dimension(200,75));
        crossinfo.setBackground(getParent().getBackground());
        
        symbinfo.setEditable(false);
        symbinfo.setLineWrap(true);
        symbinfo.setWrapStyleWord(true);
        symbinfo.setPreferredSize(new Dimension(200,75));
        symbinfo.setBackground(getParent().getBackground());
		
        c.insets = new Insets(5,5,5,5);
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.PAGE_START;
        form.add(intro, c);
        
        c.gridwidth = 1;
        
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
		form.add(new JLabel("Selection:"),c);
		c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
		form.add(selection, c);
		c.gridx = 2;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        form.add(selectinfo, c);
		
		c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
		form.add(new JLabel("Crossover:"), c);
		c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
		form.add(crossover, c);
        c.gridx = 2;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
		form.add(crossinfo, c);
		
		c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
		form.add(new JLabel("Symbiosis:"), c);
		c.gridx = 1;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
		form.add(symbiosis, c);
        c.gridx = 2;
        c.gridy = 3;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
		form.add(symbinfo, c);
		
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        JSeparator sep = new JSeparator();
		form.add(sep, c);
		
		c.insets = new Insets(5,5,5,5);
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 25;
        c.anchor = GridBagConstraints.LAST_LINE_END;
		form.add(next, c);
		
		wizard.add("Operators", form);
		
		//Setup second page of the wizard, setting GA parameters
		form = new JPanel();
		form.setLayout(new GridBagLayout());
		
        c.insets = new Insets(5,5,5,5);
		
		c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        form.add(new JLabel("Crossover Probability:"), c);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        form.add(crossoverProbability, c);
		
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_END;
        form.add(new JLabel("Phenotype Mutation Probability:"), c);
        c.gridx = 2;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        form.add(geneMutationProbability, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_END;
        form.add(new JLabel("Allele Expression Mutation Probability:"), c);
        c.gridx = 2;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        form.add(alleleMutationProbability,c);
        
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_END;
        form.add(new JLabel("Symbiogenesis Probability:"), c);
        c.gridx = 2;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
		form.add(symbioticProbability, c);
		
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        sep = new JSeparator();
		form.add(sep, c);
		
        c.insets = new Insets(5,5,5,5);
		c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 1;
        c.ipadx = 25;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.fill = GridBagConstraints.NONE;
		form.add(back, c);
		c.gridx = 3;
        c.gridy = 5;
        c.ipadx = 25;
        c.anchor = GridBagConstraints.LAST_LINE_END;
		form.add(accept, c);
		
		wizard.add("Parameters", form);
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Selection") {
			selectinfo.setText( selects.get(selection.getSelectedItem()).toInfoString());
		}
		//Change which crossover operator to be used, if none set probabilty to zero.
		else if(e.getActionCommand() == "Crossover") {
			crossinfo.setText( crosses.get(crossover.getSelectedItem()).toInfoString());
			if (crossover.getSelectedItem().equals("None")) {
				crossoverProbability.setValue(0.0);
			}
		}
		//Change which symbiotic operator to be used, if None set the probability to zero.
		else if(e.getActionCommand() == "Symbiosis") {
			symbinfo.setText( symbis.get(symbiosis.getSelectedItem()).toInfoString());
			if (symbiosis.getSelectedItem().equals("None")) {
				symbioticProbability.setValue(0.0);
			}
		}
		//Move to next wizard screen using the slideshow layout.
		else if (e.getActionCommand() == "Next") {
			CardLayout cards = (CardLayout)wizard.getLayout();
			cards.next(wizard);
		}
		//Move to previous wizard screen using the slideshow layout.
		else if (e.getActionCommand() == "Back") {
			CardLayout cards = (CardLayout)wizard.getLayout();
			cards.previous(wizard);
		}
		else if (e.getActionCommand() == "Accept") {
			CardLayout cards = (CardLayout)wizard.getLayout();
			cards.first(wizard);
			accepted = true;
			this.setVisible(false);
		}
	}

	/**
	 * @return
	 */
	public void setGA(GeneticAlgorithm ga) {		
		ga.setSelection(selects.get(selection.getSelectedItem()));
		ga.setCrossover(crosses.get(crossover.getSelectedItem()));
		ga.setSymbiosis(symbis.get(symbiosis.getSelectedItem()));
		GeneticAlgorithm.crossoverProbability = (double)(Double)crossoverProbability.getValue();
		GeneticAlgorithm.geneMutationProbability = (double)(Double)geneMutationProbability.getValue();
		GeneticAlgorithm.alleleMutationProbability = (double)(Double)alleleMutationProbability.getValue();
		GeneticAlgorithm.symbioticProbability = (double)(Double)symbioticProbability.getValue();
	}

	/**
	 * @return the accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		//When we reshow the dialog reset accepted
		if (visible == true) {
			accepted = false;
		}
		super.setVisible(visible);
	}
}
