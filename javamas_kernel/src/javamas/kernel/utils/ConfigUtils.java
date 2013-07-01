package javamas.kernel.utils;

import java.util.Properties;

/**
 *
 * @author guillaume
 * @version 1.1.0
 */
public final class ConfigUtils {

    /**
     *
     */
    public static Properties prop = null;

    /**
     *
     * @return
     */
    public static boolean loadConfig() {
	prop = new Properties();
	try {
	    prop.load(ConfigUtils.class.getResourceAsStream("config.properties"));
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
    }
}
