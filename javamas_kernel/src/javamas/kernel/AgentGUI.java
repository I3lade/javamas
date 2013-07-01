/*
 * AgentGUI.java
 *
 * Created on June 2, 2003, 12:19 PM
 */
package javamas.kernel;

import java.awt.Component;

/**
 *
 * @author Guillaume Monet
 * @version
 */
public interface AgentGUI {

    /**
     *
     * @param str
     */
    public void write(String str);

    /**
     *
     */
    public void clear();

    /**
     *
     * @return
     */
    public Component getFrame();

    /**
     *
     */
    public void close();
}
