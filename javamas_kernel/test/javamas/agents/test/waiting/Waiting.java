package javamas.agents.test.waiting;

import javamas.kernel.AbstractAgent;

/**
 *
 * @author Guillaume Monet
 */
public class Waiting extends AbstractAgent {

    public static void main(String[] args) {
	(new Waiting()).start();
    }

    @Override
    public void activate() {
	this.joinGroupe("WAITER");
    }

    @Override
    public void live() {
	this.waitNextMessage(5000);
	this.println("NO WAITING NO MORE");
    }

    @Override
    public void end() {
    }
}
