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
package fr.eloane.javamas.agents.test.scheduler;

import fr.eloane.javamas.kernel.Agent;

/**
 *
 * @author Guillaume Monet
 */
public class Scheduling extends Agent {

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
