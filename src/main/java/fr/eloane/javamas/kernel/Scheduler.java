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

import java.io.Serializable;

/**
 *
 * @author Guillaume Monet
 */
public class Scheduler implements Serializable {

    private static final long serialVersionUID = 992501499415467420L;

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
