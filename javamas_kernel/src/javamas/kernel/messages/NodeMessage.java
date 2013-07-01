package javamas.kernel.messages;

import java.io.Serializable;

/**
 *
 * @author Guillaume Monet
 */
public class NodeMessage implements Serializable, Comparable<NodeMessage> {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    public int hashcode = 0;
    /**
     *
     */
    public String groupe = null;
    /**
     *
     */
    public String role = null;
    /**
     *
     */
    public Message mes = null;
    /**
     *
     */
    public int priority = 0;
    /**
     *
     */
    public int node = 0;

    @Override
    public int compareTo(NodeMessage o) {
	if (o.priority > this.priority) {
	    return 1;
	} else if (o.priority < this.priority) {
	    return -1;
	} else {
	    return 0;
	}
    }
}
