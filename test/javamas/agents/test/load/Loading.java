package javamas.agents.test.load;

import javamas.kernel.AbstractAgent;
import javamas.kernel.messages.Message;

/**
 *
 * @author Guillaume Monet
 */
public class Loading extends AbstractAgent {

    @Override
    protected void activate() {
    }

    @Override
    protected void live() {
	int cpt = 0;
	while (this.nextStep()) {
	    Message<String> mess = new Message<>("" + cpt);
	    String toto = null;
	    this.broadcastMessage(mess,toto);
	    cpt++;
	}
    }

    @Override
    public void end() {
    }

    public static void main(String[] args) {
	Loading ld = new Loading();
	//ld.setDelay(1);
	ld.start();
    }
}
