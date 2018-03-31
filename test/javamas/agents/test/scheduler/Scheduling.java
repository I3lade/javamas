/**
 * JavaMas : Java Multi-Agents System Copyright (C) 2013 Guillaume Monet
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
