package javamas.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author  Guillaume Monet
 * @version
 */
public class CheckClass {

	/**
	 * 
	 * @param c
	 * @param path 
	 */
	public CheckClass(Class c, String path) {
		String ret = c.getName() + "<br>Super Class : " + c.getSuperclass().getName() + "<br><hr>Constructor(s)<hr>";
		Constructor[] ctors = c.getConstructors();
		for (int i = 0; i < ctors.length; i++) {
			ret += ctors[i] + "<br>";
		}


		ret += "<br><hr>Var(s)<hr>";
		Field[] fld = c.getDeclaredFields();
		for (int i = 0; i < fld.length; i++) {
			ret += "" + fld[i].getName() + " : " + fld[i].getType().getName() + "<br>";
		}


		ret += "<br><hr>Methode(s)<hr>";
		Method[] methodes = c.getDeclaredMethods();
		for (int i = 0; i < methodes.length; i++) {
			ret += "" + methodes[i].toString() + "<br>";
		}
		ret += "<br><hr>InnerClass(s)<hr>";
		Class[] cl = c.getDeclaredClasses();
		for (int i = 0; i < cl.length; i++) {
			ret += "<a href=" + cl[i].getName() + "_doc.html>" + cl[i].getName() + "<a><br>";
			new CheckClass(cl[i], path);
		}
		FileOutputStream out = null;
		try {
			//Ouvre le fichier en ?criture
			out = new FileOutputStream(new File(path + "/" + c.getName() + "_doc.html"));
			out.write(ret.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}