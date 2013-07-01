package javamas.gui.editor;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.StyledEditorKit;


/*
 * JAgentEditor.java
 *
 * Created on 1 mai 2003, 09:52
 */
/**
 *
 * @author Guillaume Monet
 * @version 1.0
 */
public class JavaEditor extends JFrame implements KeyListener, ActionListener {

    private JToolBar jToolBar2;
    private JButton new_but;
    private JButton save_but;
    private JButton comp_but;
    private JButton quit_but;
    private JScrollPane jScrollPane1;
    private JEditorPane jEditorPane1;
    private String name;
    private String folder;
    private boolean saved = true;

    /**
     * Creates new JAgentEditor
     */
    public JavaEditor() {
	try {

	    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	} catch (ClassNotFoundException ex) {
	} catch (InstantiationException ex) {
	} catch (IllegalAccessException ex) {
	} catch (UnsupportedLookAndFeelException ex) {
	}
	initGUI();
	this.newAgent();
    }

    /**
     *
     * @param path
     */
    public JavaEditor(String path) {
	initGUI();
	load(path);
    }

    /**
     *
     * @param folder
     * @param file
     */
    public JavaEditor(String folder, String file) {
	initGUI();
	newClass(folder, file);
    }

    /**
     * intitialize the GUI
     */
    public void initGUI() {
	jToolBar2 = new JToolBar();
	new_but = new JButton();
	save_but = new JButton();
	comp_but = new JButton();
	quit_but = new JButton();
	jScrollPane1 = new JScrollPane();
	jEditorPane1 = new JEditorPane();
	jEditorPane1.setEditorKit(new StyledEditorKit());
	jEditorPane1.setEditable(true);
	jEditorPane1.setDocument(new JavaDocument(jEditorPane1));
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setTitle("JAgentEditor");

	jEditorPane1.addKeyListener(this);

	new_but.setText("New");
	jToolBar2.add(new_but);
	new_but.addActionListener(this);

	save_but.setText("Save");
	jToolBar2.add(save_but);
	save_but.addActionListener(this);
	save_but.setEnabled(false);

	comp_but.setText("Compile");
	jToolBar2.add(comp_but);
	comp_but.addActionListener(this);

	quit_but.setText("Quit");
	jToolBar2.add(quit_but);
	quit_but.addActionListener(this);

	getContentPane().add(jToolBar2, java.awt.BorderLayout.NORTH);

	jScrollPane1.setViewportView(jEditorPane1);

	getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
	this.setSize(800, 600);
	this.setVisible(true);

    }

    /**
     * action performed methods
     *
     * @param e the event
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
	if (e.getSource().equals(new_but)) {
	    this.newAgent();

	}
	if (e.getSource().equals(save_but)) {
	    if (name == null) {
		String inputValue = JOptionPane.showInputDialog("Enter un nom d'agent");
		this.setTitle(inputValue + ".java");
		name = inputValue;

	    }
	    saved = true;
	    this.setTitle(this.getTitle().replaceAll("<modified>", ""));
	    save_but.setEnabled(false);
	    this.save();
	}

	if (e.getSource().equals(comp_but)) {
	    save();
	    JFrame frame = new JFrame("Console");
	    frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    JTextArea area = new JTextArea();
	    area.setEditable(false);
	    JScrollPane pane = new JScrollPane(area);
	    frame.getContentPane().add(pane);
	    frame.setSize(640, 480);
	    try {
		Process p = Runtime.getRuntime().exec("javac agents/" + folder + "/" + name + ".java");
		BufferedReader br1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String log = "";
		String temp;
		while ((temp = br1.readLine()) != null) {
		    log += temp + "\n";
		}
		while ((temp = br2.readLine()) != null) {
		    log += temp + "\n";
		}
		log += "Compiled Agent For MAS";
		area.setText(log);
		area.setCaretPosition(area.getDocument().getLength());
		frame.setVisible(true);

	    } catch (Exception ex) {
		System.out.println(e);
	    }
	}
	if (e.getSource().equals(quit_but)) {
	    this.save();
	    this.dispose();
	}
    }

    /**
     * main class
     *
     * @param args should be null
     */
    public static void main(String args[]) {
	new JavaEditor();
    }

    /**
     *
     */
    public void newAgent() {
	String ret = "";
	String temp = "";
	try {
	    BufferedReader read = new BufferedReader(new FileReader("kernel/AgentSkeleton.java"));
	    while ((temp = read.readLine()) != null) {
		ret += temp + "\n";
	    }
	} catch (IOException ex) {
	    System.out.println(ex);
	}


	String inputValue = JOptionPane.showInputDialog("Enter un nom d'agent");
	this.setTitle(inputValue + ".java");
	name = inputValue;
	folder = name;
	ret = ret.replaceAll("AgentSkeleton", name);
	jEditorPane1.setText(ret);
	File folder = new File("agents/" + name);
	folder.mkdir();
    }

    /**
     *
     * @param folder
     * @param name
     */
    public void newClass(String folder, String name) {
	String ret = "";
	String temp = "";
	try {
	    BufferedReader read = new BufferedReader(new FileReader("kernel/ClassSkeleton.java"));
	    while ((temp = read.readLine()) != null) {
		ret += temp + "\n";
	    }
	} catch (IOException ex) {
	    System.out.println(ex);
	}



	this.setTitle(name + ".java");
	this.name = name;
	this.folder = folder;
	ret = ret.replaceAll("ClassSkeleton", name);
	ret = ret.replaceAll("ClassPackage", folder);
	jEditorPane1.setText(ret);
    }

    /**
     * save the file
     */
    public void save() {
	FileOutputStream out = null;

	try {
	    //Ouvre le fichier en ?criture
	    out = new FileOutputStream(new File("agents/" + folder + "/" + name + ".java"));
	    out.write(jEditorPane1.getText().getBytes());
	    out.flush();
	    out.close();
	} catch (IOException e) {
	    System.out.println(e);
	}
    }

    /**
     *
     * @param path
     */
    public void load(String path) {
	String ret = "";
	String temp = "";
	ArrayList vect = new ArrayList();
	try {
	    StringTokenizer st = new StringTokenizer(path);
	    while (st.hasMoreTokens()) {
		vect.add(st.nextToken("/"));
	    }
	} catch (Exception e) {
	    System.out.println("Wrong URL");
	}

	name = ("" + (vect.get(vect.size() - 1))).replaceAll(".java", "");
	folder = "" + vect.remove(vect.get(vect.size() - 1));
	this.setTitle(name + ".java");
	try {
	    BufferedReader read = new BufferedReader(new FileReader(path));
	    while ((temp = read.readLine()) != null) {
		ret += temp + "\n";
	    }
	} catch (IOException ex) {
	    System.out.println(ex);
	}
	jEditorPane1.setText(ret);
    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
	if (saved == true) {
	    this.saved = false;
	    this.setTitle(this.getTitle() + "<modified>");
	    save_but.setEnabled(true);
	}
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent keyEvent) {
    }
}