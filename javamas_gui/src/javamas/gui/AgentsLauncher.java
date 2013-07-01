package javamas.gui;

import javamas.gui.editor.JavaEditor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import javamas.kernel.AbstractAgent;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.ToolTipManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 * 
 * @author guillaume-monet
 */
public class AgentsLauncher extends JFrame {

	/**
	 * 
	 */
	public static AgentsLauncher agent = null;
	private JScrollPane folderPane;
	private JTree FolderTree = new JTree();
	private JDesktopPane desktop_pane_agent;
	private JPanel jPanel2;
	private JScrollPane debugPane;
	private JTextArea debug_text_area;
	private File agents;
	private JEditorPane html;
	private boolean run_exec = false;

	/** return unique instqnce of agent launcher
	 * @return the instance
	 */
	public static AgentsLauncher getHandle() {
		if (agent == null) {
			agent = new AgentsLauncher();
		}
		return agent;
	}

	private AgentsLauncher() {
		this.setTitle("JAMAS JAva Multi-Agents System");
		this.setResizable(false);
		folderPane = new JScrollPane();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		desktop_pane_agent = new JDesktopPane();
		jPanel2 = new JPanel();

		debug_text_area = new JTextArea();
		debug_text_area.setEditable(false);
		debug_text_area.setBackground(new Color(204, 204, 204));
		getContentPane().setLayout(new AbsoluteLayout());

		this.createTree();

		getContentPane().add(folderPane, new AbsoluteConstraints(10, 10, 160, 490));
		desktop_pane_agent.setBorder(new EtchedBorder());
		getContentPane().add(desktop_pane_agent, new AbsoluteConstraints(191, 11, 600, 490));

		jPanel2.setLayout(new BorderLayout());

		jPanel2.setBorder(new TitledBorder("Debug"));
		debug_text_area.setEditable(false);
		debugPane = new JScrollPane(debug_text_area);

		jPanel2.add(debugPane, BorderLayout.CENTER);

		getContentPane().add(jPanel2, new AbsoluteConstraints(10, 510, 790, 80));
		new Console();
		pack();
		setVisible(true);
	}

	/**
	 * @param str  */
	public void setDebugText(String str) {
		try {
			debug_text_area.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		AgentsLauncher.getHandle();
	}

	/**
	 * @param files
	 * @param name
	 * @return  
	 */
	public DefaultMutableTreeNode addNode(File[] files, String name) {
		DefaultMutableTreeNode temp = new DefaultMutableTreeNode(name);
		for (int inc = 0; inc < files.length; inc++) {
			if (!files[inc].isDirectory()) {
				try {
					if (files[inc].getName().toLowerCase().endsWith(".class") && files[inc].getName().indexOf('$') == -1 || (files[inc].getName().toLowerCase().endsWith(".html") || files[inc].getName().toLowerCase().endsWith(".htm")) && files[inc].getName().indexOf('$') == -1 || files[inc].getName().toLowerCase().endsWith(".java")) {
						DefaultMutableTreeNode child = new DefaultMutableTreeNode(files[inc].getName());
						temp.add(child);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				temp.add(addNode(files[inc].listFiles(), files[inc].getName()));
			}
		}
		return temp;
	}

	/**
	 * @param tr  
	 */
	public void checkFiles(TreePath tr) {

		String file = "/";
		String cl = agents.getPath();

		for (int inc = 1; inc < tr.getPathCount(); inc++) {
			file += "/" + tr.getPathComponent(inc);
		}

		for (int inc = 1; inc < tr.getPathCount(); inc++) {
			cl += "." + tr.getPathComponent(inc);
		}

		if (file.toLowerCase().endsWith(".html") || file.toLowerCase().endsWith(".htm")) {
			JScrollPane scroller = new JScrollPane();
			try {
				File theFile = new File(agents.getAbsolutePath() + file);
				html = new JEditorPane(theFile.toURL());
				html.setEditable(false);
				html.addHyperlinkListener(createHyperLinkListener());
				JViewport vp = scroller.getViewport();
				vp.add(html);
			} catch (java.net.MalformedURLException ex) {
				System.out.println("Malformed URL: " + ex);
			} catch (IOException ex) {
				System.out.println("IOException: " + ex);
			} catch (Exception ex) {
				System.out.println("Erreur lors du chargement de la page d'aide" + ex);
			}

			JFrame splashScreen = new JFrame();
			splashScreen.setSize(640, 480);
			splashScreen.setTitle(file);
			splashScreen.getContentPane().add(scroller);
			splashScreen.setVisible(true);
		}

		if (file.toLowerCase().endsWith(".class")) {
			(new Launcher(cl)).start();
		}
	}

	/**
	 * @return  */
	public HyperlinkListener createHyperLinkListener() {
		return new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					if (e instanceof HTMLFrameHyperlinkEvent) {
						((HTMLDocument) html.getDocument()).processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent) e);
					} else {
						try {
							html.setPage(e.getURL());
						} catch (IOException ioe) {
							System.out.println("IOE: " + ioe);
						}
					}
				}
			}
		};
	}

	/**
	 * 
	 */
	public void createTree() {
		agents = new File("agents");
		File[] files = agents.listFiles();
		FolderTree.removeAll();
		FolderTree = new JTree(addNode(files, "agents"));
		FolderTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		ToolTipManager.sharedInstance().registerComponent(FolderTree);
		FolderTree.setCellRenderer(new TreeRenderer());
		final MenuPopUp pop = new MenuPopUp();
		FolderTree.add(pop);
		MouseListener ml = new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				TreePath selPath = FolderTree.getPathForLocation(e.getX(), e.getY());
				if (e.getModifiers() == e.BUTTON1_MASK) {
					int selRow = FolderTree.getRowForLocation(e.getX(), e.getY());

					if (selRow != -1) {
						if (e.getClickCount() == 2) {
							checkFiles(selPath);
						}
					}
				} else {
					if (FolderTree.getRowForLocation(e.getX(), e.getY()) != -1 && ((DefaultMutableTreeNode) FolderTree.getPathForLocation(e.getX(), e.getY()).getLastPathComponent()).isLeaf()) {
						FolderTree.setSelectionRow(FolderTree.getRowForLocation(e.getX(), e.getY()));
						pop.show(e.getComponent(), e.getX(), e.getY(), selPath);
					} else {
						if (FolderTree.getRowForLocation(e.getX(), e.getY()) != -1 && ((DefaultMutableTreeNode) FolderTree.getPathForLocation(e.getX(), e.getY()).getLastPathComponent()).isRoot()) {
							FolderTree.setSelectionRow(FolderTree.getRowForLocation(e.getX(), e.getY()));
							(new PopCreate()).show(e.getComponent(), e.getX(), e.getY());

						} else {
							createTree();
						}
					}
				}


			}
		};
		FolderTree.addMouseListener(ml);
		folderPane.setViewportView(FolderTree);
	}

	class TreeRenderer extends DefaultTreeCellRenderer {

		public TreeRenderer() {
		}

		/**
		 * @param tree
		 * @param value
		 * @param sel
		 * @param expanded
		 * @param leaf
		 * @param row
		 * @param hasFocus
		 * @return  */
		public Component getTreeCellRendererComponent(
				JTree tree,
				Object value,
				boolean sel,
				boolean expanded,
				boolean leaf,
				int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(
					tree, value, sel,
					expanded, leaf, row,
					hasFocus);
			if (value.toString().endsWith(".class")) {
				ImageIcon i = new ImageIcon("kernel/agent.jpg");
				i.setImage(i.getImage().getScaledInstance(15, 20, 1));
				setIcon(i);
				setToolTipText("An agent");
			} else {
				if (value.toString().toLowerCase().endsWith(".html") || value.toString().toLowerCase().endsWith(".htm")) {
					ImageIcon i = new ImageIcon("kernel/html.gif");
					i.setImage(i.getImage().getScaledInstance(15, 20, 1));
					setIcon(i);
					setToolTipText("HTML files");
				} else {
					if (value.toString().endsWith(".java")) {
						ImageIcon i = new ImageIcon("kernel/javalogo.gif");
						i.setImage(i.getImage().getScaledInstance(15, 20, 1));
						setIcon(i);
						setToolTipText("Java source");
					} else {
						setToolTipText("Directory");
					}
				}
			}
			return this;
		}
	}

	class MenuPopUp extends JPopupMenu implements ActionListener {

		private JMenuItem laun = new JMenuItem("Launch");
		private JMenuItem edit = new JMenuItem("Edit");
		private JMenuItem exec = new JMenuItem("Create AutoExec");
		private JMenuItem doc = new JMenuItem("Generate MiniDoc");
		private TreePath tr = null;

		public MenuPopUp() {
			super();
			laun.addActionListener(this);
			edit.addActionListener(this);
			exec.addActionListener(this);
			doc.addActionListener(this);


			this.add(laun);
			this.add(edit);
			this.add(exec);
			this.add(doc);

		}

		/**
		 * @param invoker
		 * @param x
		 * @param y
		 * @param p  */
		public void show(Component invoker, int x, int y, TreePath p) {
			tr = p;
			String type = "";
			if (((DefaultMutableTreeNode) p.getLastPathComponent()).toString().toLowerCase().endsWith(".class")) {
				type = ".class";
			}

			if (((DefaultMutableTreeNode) p.getLastPathComponent()).toString().toLowerCase().endsWith(".htm") || ((DefaultMutableTreeNode) p.getLastPathComponent()).toString().toLowerCase().endsWith(".html")) {
				type = ".html";
			}

			if (((DefaultMutableTreeNode) p.getLastPathComponent()).toString().toLowerCase().endsWith(".java")) {
				type = ".java";
			}

			if (type.equalsIgnoreCase(".class")) {
				laun.setEnabled(true);
				edit.setEnabled(false);
				if (!run_exec) {
					exec.setEnabled(true);
				} else {
					exec.setEnabled(false);
				}
				doc.setEnabled(true);

			}
			if (type.equalsIgnoreCase(".java")) {
				laun.setEnabled(false);
				edit.setEnabled(true);
				exec.setEnabled(false);
				doc.setEnabled(false);

			}
			if (type.equalsIgnoreCase(".html")) {
				laun.setEnabled(true);
				edit.setEnabled(false);
				exec.setEnabled(false);
				doc.setEnabled(false);

			}
			this.show(invoker, x, y);
		}

		/**
		 * @param e  */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(laun)) {
				checkFiles(tr);
			}
			if (e.getSource().equals(edit)) {
				String file = "";
				for (int inc = 1; inc < tr.getPathCount(); inc++) {
					file += "/" + tr.getPathComponent(inc);
				}
				new JavaEditor(agents.getAbsolutePath() + file);
			}
			if (e.getSource().equals(exec)) {
				String file = "/";
				for (int inc = 1; inc < tr.getPathCount(); inc++) {
					file += "/" + tr.getPathComponent(inc);
				}
				System.out.println("Waiting creation");
				this.setVisible(false);
				run_exec = true;
				(new GenerateExec(agents.getAbsolutePath() + file.replaceAll(".class", ""))).start();

			}

			if (e.getSource().equals(doc)) {
				String file = "/";
				String cl = agents.getPath();

				for (int inc = 1; inc < tr.getPathCount() - 1; inc++) {
					file += "/" + tr.getPathComponent(inc);
				}

				for (int inc = 1; inc < tr.getPathCount(); inc++) {
					cl += "." + tr.getPathComponent(inc);
				}
				try {
					new CheckClass(Class.forName(cl.replaceAll(".class", "")), agents.getAbsolutePath() + file);
					this.setVisible(false);
					createTree();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	class PopCreate extends JPopupMenu implements ActionListener {

		private JMenuItem laun = new JMenuItem("Create Agent");
		private JMenuItem ref = new JMenuItem("Refresh tree");

		public PopCreate() {
			super();
			laun.addActionListener(this);
			this.add(laun);
			ref.addActionListener(this);
			this.add(ref);
		}

		/**
		 * @param e  */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(laun)) {
				new JavaEditor();
				this.setVisible(false);
			}
			if (e.getSource().equals(ref)) {
				createTree();
			}
		}
	}

	class Launcher extends Thread {

		private Class clprog;

		/**
		 * @param adr  */
		public Launcher(String adr) {
			try {
				clprog = Class.forName(adr.replaceAll(".class", ""));
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

		public void run() {
			try {
				Object instance = clprog.newInstance();
				AbstractAgent agt = ((AbstractAgent) instance);
				MiniInternalFrame frame = new MiniInternalFrame(((AbstractAgent) instance).getAgentAddress().getPublic_name(), agt);
				desktop_pane_agent.add((JInternalFrame) frame.getFrame());
				((JInternalFrame) frame.getFrame()).setVisible(true);
				((JInternalFrame) frame.getFrame()).moveToFront();
				try {
					agt.setGUI(frame);
					agt.activate();
					agt.live();
					agt.end();
					agt.kill();
					agt = null;
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class Console {

		PipedInputStream piOut;
		PipedInputStream piErr;
		PipedOutputStream poOut;
		PipedOutputStream poErr;

		public Console() {
			try {
				piOut = new PipedInputStream();
				poOut = new PipedOutputStream(piOut);
				System.setOut(new PrintStream(poOut, true));
				piErr = new PipedInputStream();
				poErr = new PipedOutputStream(piErr);
				System.setErr(new PrintStream(poErr, true));
				new ReaderThread(piOut).start();
				new ReaderThread(piErr).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		class ReaderThread extends Thread {

			PipedInputStream pi;

			ReaderThread(PipedInputStream pi) {
				this.pi = pi;
			}

			public void run() {
				BufferedReader br1 = new BufferedReader(new InputStreamReader(pi));
				while (true) {
					String temp;
					try {
						if ((temp = br1.readLine()) != null) {
							debug_text_area.append(temp + "\n");
							System.out.flush();
							System.err.flush();
							debug_text_area.setCaretPosition(debug_text_area.getDocument().getLength());
						}
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}

			}
		}
	}

	class GenerateExec extends Thread {

		private String str = "";

		/**
		 * @param str  */
		public GenerateExec(String str) {
			this.str = str;

		}

		public void run() {
			new JarBuilder(str);
			System.out.println("AutoExec created");
			run_exec = false;
		}
	}
}