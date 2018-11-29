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
package fr.eloane.javamas.kernel.transport;

import java.util.Observable;
import fr.eloane.javamas.kernel.messages.Message;
import java.util.HashMap;

/**
 *
 * @author Guillaume Monet
 */
public abstract class Transport extends Observable implements Runnable {

    private boolean stop = false;
    protected HashMap<String, String> parameters;

    /**
     *
     * @param parameters
     */
    public Transport(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     *
     */
    public final void start() {
        (new Thread(this)).start();
    }

    /**
     *
     * @param mess
     */
    public abstract void sendMessage(Message<?> mess);

    /**
     *
     */
    public void close() {
        stop = true;
        kill();
    }

    /**
     *
     */
    public abstract void kill();

    @Override
    public final void run() {
        while (!stop) {
            Message message = waitMessage();
            this.setChanged();
            this.notifyObservers(message);
        }
    }

    /**
     *
     * @return
     */
    public abstract Message waitMessage();
}
