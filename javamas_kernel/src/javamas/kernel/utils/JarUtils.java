package javamas.kernel.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author guillaume
 * @version 1.0.0
 */
public final class JarUtils {

    private JarUtils() {
    }

    /**
     *
     * @param cls
     * @param filename
     * @return
     */
    public static String loadContentFile(Class cls, String filename) {
	String ret = "";
	BufferedReader rd = new BufferedReader(new InputStreamReader(cls.getResourceAsStream(filename)));
	String line;
	try {
	    while ((line = rd.readLine()) != null) {
		ret += line;
	    }
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	return ret;
    }
}
