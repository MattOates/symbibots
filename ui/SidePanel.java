package ui;

import game.Agent;
import game.UserAgent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Main tabbed container for holding user panels.
 * @author Matt Oates
 */
public class SidePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 780487651577695337L;
	
	private UserAgent player;
	
	private JTabbedPane agents = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

	public SidePanel(MainWindow ui) {
		super(new GridLayout(1,0));
		setMinimumSize(new Dimension(250, 500));
		setPreferredSize(new Dimension(250, 500));
		add(agents);
	}
	
	public synchronized void addAgent(Agent agent) {
		agent.getPanel().setParentPanel(this);
		agents.addTab("Agent #"+agent.hashCode(), new ImageIcon("images"+File.separator+"agenttab.png"), agent.getPanel(), "Explore Agent #"+agent.hashCode());
	}
	
	public synchronized void removeAgent(Agent agent) {
		agent.getPanel().setParentPanel(null);
		agents.remove(agent.getPanel());
	}
	
	public synchronized void removeAllAgents() {
		int numOfAgents = agents.getTabCount() -1;
		for (int i=0; i < numOfAgents; i++) {
			AgentPanel panel = (AgentPanel)agents.getComponentAt(i);
			panel.setParentPanel(null);
			agents.remove(1);
		}
	}
	
	public void addPlayer(UserAgent player) {
		this.player = player;
		player.getPanel().setParentPanel(this);
		agents.addTab("Player 1", new ImageIcon("images"+File.separator+"playertab.png"), player.getPanel(), "Explore Player1's game agent.");
	}
	
	public void removePlayer(UserAgent player) {
		this.player = null;
		player.getPanel().setParentPanel(null);
		agents.remove(player.getPanel());
	}

	/**
	 * @return the agents
	 */
	public JTabbedPane getAgents() {
		return agents;
	}

	/**
	 * @param agents the agents to set
	 */
	public void setAgents(JTabbedPane agents) {
		this.agents = agents;
	}

	/**
	 * @return the player
	 */
	public UserAgent getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(UserAgent player) {
		this.player = player;
	}
	
}
