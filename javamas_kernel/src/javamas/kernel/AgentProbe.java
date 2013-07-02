package javamas.kernel;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Guillaume Monet
 */
public abstract class AgentProbe implements Serializable, Observer {

    private static final long serialVersionUID = 1L;

    @Override
    public final void update(Observable o, Object arg) {
	if (arg instanceof AgentProbeValue<?>) {
	    this.handleProbe((AgentProbeValue<?>) arg);
	} else {
	    this.handleProbe(arg);
	}
    }

    /**
     *
     * @param value
     */
    public abstract void handleProbe(AgentProbeValue<?> value);

    /**
     *
     * @param value
     */
    public void handleProbe(Object value) {
	System.out.println(value.toString());
    }
}
