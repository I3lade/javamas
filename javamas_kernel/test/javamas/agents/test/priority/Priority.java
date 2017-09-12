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
package javamas.agents.test.priority;

import javamas.kernel.AbstractAgent;
import javamas.kernel.messages.Message;

/**
 *
 * @author Guillaume Monet
 */
public class Priority extends AbstractAgent {

    @Override
    public void activate() {
	this.joinGroupe("PRIORITY");
    }

    @Override
    public void live() {
	while (this.nextStep()) {
	    System.out.println("WAITING");
	}
	System.out.println(this.waitNextMessage().getContent());
	System.out.println(this.waitNextMessage().getContent());
	System.out.println(this.waitNextMessage().getContent());
	System.out.println(this.waitNextMessage().getContent());
	System.out.println(this.waitNextMessage().getContent());
    }

    @Override
    public void end() {
    }

    public static void main(String[] args) {
	Priority p = new Priority();
	p.setDelay(1000);
	p.start();


	AbstractAgent a = new AbstractAgent() {
	    @Override
	    public void activate() {
	    }

	    @Override
	    public void live() {
	    }

	    @Override
	    public void end() {
	    }
	};
	a.joinGroupe("PRIORITY");
	Message<String> m = new Message<>();
	m.setContent("LOW PRIORITY");
	m.setPriority(Message.LOW_PRIORITY);
	a.broadcastMessage(m, "PRIORITY");
	System.out.println("Send Low priority");

	m.setContent("HIGH PRIORITY");
	m.setPriority(Message.HIGH_PRIORITY);
	a.broadcastMessage(m, "PRIORITY");
	System.out.println("Send HI priority");

	m.setContent("NORMAL PRIORITY");
	m.setPriority(Message.NORMAL_PRIORITY);
	a.broadcastMessage(m, "PRIORITY");
	System.out.println("Send Normal priority");

	m.setContent("EXTREM PRIORITY");
	m.setPriority(Message.EXTREM_PRIORITY);
	a.broadcastMessage(m, "PRIORITY");
	System.out.println("Send Extrem priority");

	m.setContent("NORMAL PRIORITY");
	m.setPriority(Message.NORMAL_PRIORITY);
	a.broadcastMessage(m, "PRIORITY");
	System.out.println("Send Normal priority");
	p.stop();

    }
}
