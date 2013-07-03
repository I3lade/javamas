package javamas.agents.test.load;

import javamas.kernel.AbstractAgent;
import javamas.kernel.AgentNode;
import javamas.kernel.messages.Message;

/**
 *
 * @author Guillaume Monet
 */
public class Getting extends AbstractAgent {

    private int last = -1;
    private int error = 0;

    @Override
    protected void activate() {
    }

    @Override
    protected void live() {
	while (nextStep()) {
	    Message<String> mes = this.waitNextMessage();
	    int curr = Integer.parseInt(mes.getContent());
	    if (curr != last + 1 && last != -1 && last > 0) {
		error++;
		System.out.println((error/last)*100);
	    }
	    System.out.println(curr);
	    last = curr;
	}

    }

    @Override
    public void end() {
    }

    public static void main(String[] args) {
	AgentNode.getHandle().setLocal(false);
	(new Getting()).start();
    }
}
