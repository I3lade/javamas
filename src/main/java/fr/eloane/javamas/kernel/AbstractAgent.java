/*
 * The MIT License
 *
 * Copyright 2018 Guillaume Monet.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.eloane.javamas.kernel;

import fr.eloane.javamas.kernel.sensors.Sensor;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Guillaume Monet
 */
public abstract class AbstractAgent extends Observable implements Serializable, Observer, Runnable {

    private static final long serialVersionUID = -4353825708388962018L;

    protected transient boolean daemon = false;

    /**
     * Initialize of the Agent can be overridden
     */
    protected void init() {
    }

    /**
     *
     */
    protected abstract void activate();

    /**
     * life of the agent
     */
    protected abstract void live();

    /**
     * end of the agent
     */
    protected abstract void end();

    /**
     *
     */
    protected void reset() {

    }

    protected void kill() {

    }

    /**
     * Start the life cycle
     */
    public final void start() {
        Thread th = new Thread(this);
        th.setDaemon(daemon);
        th.start();
    }

    /**
     * Life of the agent destroy all objects from the agents
     */
    @Override
    public final void run() {
        this.init();
        this.activate();
        this.live();
        this.end();
        this.kill();
    }

    /**
     * Method to override if sensors want to be handled Handle trigger of the
     * sensor (pattern observer is used)
     *
     * @param sensor sensor that trigger the event
     */
    protected void handleSensor(Sensor<?> sensor) {
        System.out.println(sensor.getValue().toString());
    }

    /**
     * Handle update for observer pattern
     *
     * @param o observable that trigger the update event
     * @param arg
     * @throws ClassCastException
     */
    @Override
    public final void update(Observable o, Object arg) throws ClassCastException {
        if (o instanceof Sensor<?>) {
            handleSensor((Sensor<?>) o);
        } else {
            throw new ClassCastException("Can't cast " + o.getClass() + " to " + Sensor.class);
        }
    }

}
