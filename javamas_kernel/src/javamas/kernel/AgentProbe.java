package javamas.kernel;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Guillaume Monet
 */
public final class AgentProbe implements Serializable, Observer {

    @Override
    public final void update(Observable o, Object arg) {
	if (arg instanceof AgentProbeValue) {
	    this.handleProbe((AgentProbeValue) arg);
	} else {
	    System.out.println(arg.toString());
	}

    }

    /**
     *
     * @param value
     */
    public void handleProbe(AgentProbeValue value) {
	System.out.println(value.toString());
    }
}
