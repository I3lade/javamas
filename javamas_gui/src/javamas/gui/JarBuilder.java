package javamas.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author monet
 * @version
 */
public class JarBuilder {

	/**
	 * Creates new GenerateAutoExec
	 *
	 * @param path
	 */
	public JarBuilder(String path) {
		ArrayList vect = new ArrayList();
		try {
			StringTokenizer st = new StringTokenizer(path);
			while (st.hasMoreTokens()) {
				vect.add(st.nextToken("/"));
			}
		} catch (Exception e) {
			System.out.println("Wrong URL");
		}

		String name = ("" + (vect.get(vect.size() - 1))).replaceAll(".java", "");
		String folder = "" + vect.remove(vect.get(vect.size() - 1));
		String ret = "import gui.*;\nimport kernel.*;\nimport agents." + folder + ".*;\npublic class " + name + "_exec{\npublic static void main(String[] args){\nMiniAgentGUI gui = new MiniAgentGUI();\n AgentLibrary lib= (AgentLibrary)new " + name + "();\nlib.setGUI(gui);\nlib.initGUI();\nlib.activate();\nlib.live();\nlib.end();\nlib.kill();}\n}";
		FileOutputStream out = null;
		try {
			//Ouvre le fichier en ?criture
			out = new FileOutputStream(new File(name + "_exec.java"));
			out.write(ret.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String man = "Main-Class: " + name + "_exec\n";
		try {
			//Ouvre le fichier en ?criture
			out = new FileOutputStream(new File("MANIFEST.txt"));
			out.write(man.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Process c = Runtime.getRuntime().exec("javac " + name + "_exec.java");
			c.waitFor();
			Process p = Runtime.getRuntime().exec("jar -cmf MANIFEST.txt autolaunch/" + name + "_mas.jar " + name + "_exec.class kernel GUI agents/" + folder + "/");
			p.waitFor();
			System.out.println("Created Agent " + name + "_mas.jar");
			new File(name + "_exec.java").delete();
			new File(name + "_exec.class").delete();
			new File("MANIFEST.txt").delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
