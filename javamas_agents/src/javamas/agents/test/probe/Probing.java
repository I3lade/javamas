package javamas.agents.test.probe;

import javamas.kernel.AbstractAgent;
import javamas.kernel.AgentProbe;
import javamas.kernel.AgentProbeValue;

/**
 *
 * @author Guillaume Monet
 */
public class Probing extends AbstractAgent {

    @Override
    public void activate() {
    }

    @Override
    public void live() {
	this.setDelay(5000);
	int count = 0;
	while (this.nextStep()) {
	    AgentProbeValue<Integer> value = new AgentProbeValue<Integer>("NEW VALUE OF TOTO", count);
	    this.setChanged();
	    this.notifyObservers(value);
	    count ++;
	}

    }

    @Override
    public void end() {
    }

    public static void main(String[] args) {
	AgentProbe o = new AgentProbe();
	Probing pro = new Probing();
	pro.addProbe(o);
	pro.start();
    }
}
