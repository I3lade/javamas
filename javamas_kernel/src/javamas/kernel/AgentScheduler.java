package javamas.kernel;

/**
 *
 * @author Guillaume Monet
 */
public final class AgentScheduler {

    private boolean pause = false;
    private boolean go = true;
    private long delay = 0;
    private long pause_time = 0;

    /**
     *
     * @return
     */
    public synchronized boolean nextStep() {
	try {
	    while (pause || pause_time > 0) {
		this.wait(pause_time);
		this.pause_time = 0;
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
     * @param time
     */
    public void pause(long time) {
	this.pause_time = time;
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
    public void setDelay(long delay) {
	this.delay = delay;
    }
}
