/*
 * MiniAgentGUI.java
 *
 * Created on May 7, 2003, 11:33 AM
 */
package javamas.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import javamas.kernel.AgentGUI;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Mini interface for autolaunchable agent
 *
 * @author Guillaume Monet
 * @version 0.2
 */
public class MiniAgentGUI extends JFrame implements AgentGUI {

    private JScrollPane miniPane;
    private JTextArea miniText;

    /**
     * Creates new MiniAgentGUI
     */
    public MiniAgentGUI() {
	super("Agent Mini Interface");
	miniPane = new JScrollPane();
	miniText = new JTextArea();
	miniPane.setViewportView(miniText);
	miniText.setText("Mini Agent GUI \n");
	miniText.setEditable(false);
	this.getContentPane().add(miniPane, BorderLayout.CENTER);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(400, 300);
	this.setVisible(true);
	this.setVisible(true);
    }

    /**
     *
     * @param str
     */
    @Override
    public void write(String str) {
	miniText.append(str + "\n");
    }

    /**
     *
     */
    @Override
    public void clear() {
	miniText.setText("");
    }

    /**
     *
     * @return
     */
    @Override
    public Component getFrame() {
	return this;
    }
}
