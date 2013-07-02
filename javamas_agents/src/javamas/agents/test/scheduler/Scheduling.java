package javamas.agents.test.scheduler;

import javamas.kernel.AbstractAgent;

/**
 *
 * @author Guillaume Monet
 */
public class Scheduling extends AbstractAgent {

    @Override
    public void activate() {
	System.out.println("HELLO WORLD");
    }

    @Override
    public void live() {
	int cpt = 0;
	while (this.nextStep()) {
	    System.out.println("..." + cpt++);
	}
    }

    @Override
    public void end() {
	System.out.println("BYE");
    }

    public static void main(String[] args) throws InterruptedException {
	Scheduling sc = new Scheduling();
	sc.setDelay(1000);
	sc.start();
	Thread.currentThread().sleep(2000);
	sc.pause();
	Thread.currentThread().sleep(2000);
	sc.resume();
	Thread.currentThread().sleep(2000);
	sc.pause(2000);
	Thread.currentThread().sleep(5000);
	sc.stop();
    }
}
