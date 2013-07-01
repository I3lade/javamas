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
	while (this.nextStep()) {
	    System.out.println("...");
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
	Thread.currentThread().sleep(5000);
	sc.pause();
	Thread.currentThread().sleep(2000);
	sc.setDelay(200);
	sc.resume();
	Thread.currentThread().sleep(5000);
	sc.stop();
    }
}
