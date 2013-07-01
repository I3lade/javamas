package javamas.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import javamas.kernel.AgentGUI;
import javamas.kernel.AbstractAgent;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/*
 * MiniInternalFrame.java
 *
 * Created on May 16, 2003, 2:00 PM
 */
/**
 *
 * @author  Guillaume Monet
 * @version
 */
public final class MiniInternalFrame extends JInternalFrame implements ActionListener, AgentGUI, InternalFrameListener {

	private JScrollPane miniPane;
	private JTextArea miniText;
	private AbstractAgent agent;
	private JButton but;
	private String name;

	/** Creates new MiniAgentGUI
	 * @param name 
	 * @param agent 
	 */
	public MiniInternalFrame(String name, AbstractAgent agent) {

		this.agent = agent;
		this.name = name;
		this.setTitle(name);
		miniPane = new JScrollPane();
		miniText = new JTextArea();
		miniPane.setViewportView(miniText);
		but = new JButton("Save to file");
		this.getContentPane().add(but, BorderLayout.SOUTH);
		but.addActionListener(this);
		this.getContentPane().add(miniPane, BorderLayout.CENTER);
		this.addInternalFrameListener(this);
		this.setSize(200, 200);
		this.setIconifiable(true);
		this.setMaximizable(true);
		this.setClosable(true);
		this.setResizable(true);
		try {
			this.setSelected(true);
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param str
	 */
	@Override
	public void write(String str) {
		miniText.append(str + "\n");
		miniText.setCaretPosition(miniText.getDocument().getLength());
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public Component getFrame() {
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		FileOutputStream out = null;
		JFileChooser file = new JFileChooser();
		if (JFileChooser.APPROVE_OPTION == file.showSaveDialog(this)) {

			try {
				out = new FileOutputStream(file.getSelectedFile());
				out.write(miniText.getText().getBytes());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void clear() {
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		try {
			agent.kill();
			agent = null;
			this.dispose();
			System.gc();
		} catch (NullPointerException ex) {
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
	}
}