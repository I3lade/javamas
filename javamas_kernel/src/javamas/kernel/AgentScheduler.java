package javamas.kernel;

/**
 *
 * @author Guillaume Monet
 */
public final class AgentScheduler {

    private boolean pause = false;
    private boolean go = true;
    private int delay = 0;

    /**
     *
     * @return
     */
    public synchronized boolean nextStep() {
	try {
	    while (pause) {
		this.wait();
	    }
	    if (delay > 0) {
		this.wait(delay);
	    }
	} catch (InterruptedException ex) {
	}
	return go;
    }

    /**
     *
     */
    public void pause() {
	this.pause = true;
    }

    /**
     *
     */
    public synchronized void resume() {
	this.pause = false;
	this.notify();
    }

    /**
     *
     */
    public void stop() {
	this.go = false;
    }

    /**
     *
     * @param delay
     */
    public void setDelay(int delay) {
	this.delay = delay;
    }
}
