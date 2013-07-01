package javamas.kernel.utils;

/**
 *
 * @author guillaume
 * @version 1.0.0
 */
public abstract class FileUtils {

    /**
     *
     */
    public final static String HOME = System.getProperty("user.home");
    /**
     *
     */
    public final static String TEMP = System.getProperty("java.io.tmpdir");
    /**
     *
     */
    public static String CURRENT = "";
    /**
     *
     */
    public final static String SEPARATOR = System.getProperty("file.separator");

    private FileUtils() {
    }
}
