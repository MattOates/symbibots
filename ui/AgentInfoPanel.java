package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.*;

import sim.Agent;
import sim.agents.UserAgent;

/**
 * A panel that displays the Entity and Agent specific data about a give agent.
 * This is both created and updated by its respective Agent.
 * @author Matt Oates
 */
public class AgentInfoPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8670209076926834106L;
	
	private JTextArea info;
	private Agent agent;
	private AgentPanel parent;
	
	public AgentInfoPanel (Agent agent, AgentPanel parent) {
		super(new BorderLayout());
		this.agent = agent;
		this.parent = parent;
		info = new JTextArea(agent.toString());
		info.setEditable(false);
		
		String titlestring = "Agent Stats";
		if (agent instanceof UserAgent)
			titlestring = "Player Stats";
		
		TitledBorder title = BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),titlestring);
		title.setTitleJustification(TitledBorder.LEFT);
		info.setBorder(title);
		this.add(new JScrollPane(info), BorderLayout.CENTER);
		
		if (!(agent instanceof UserAgent)) {
			JButton close = new JButton("Close Agent View", new ImageIcon("images"+File.separator+"cancel.png"));
			close.addActionListener(parent);
			this.add(close, BorderLayout.SOUTH);
		}
		
		setMinimumSize(new Dimension(300, 100));
		setPreferredSize(new Dimension(300, 100));
	}
	
	/**
	 * 
	 */
	public synchronized void updateData() {
		info.setText(agent.toString());
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
	 * @return the info
	 */
	public JTextArea getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(JTextArea info) {
		this.info = info;
	}

	/**
	 * @return the parent
	 */
	public AgentPanel getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(AgentPanel parent) {
		this.parent = parent;
	}
	
}
