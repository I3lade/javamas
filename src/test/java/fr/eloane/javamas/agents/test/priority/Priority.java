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
package fr.eloane.javamas.agents.test.priority;

import fr.eloane.javamas.kernel.Agent;
import fr.eloane.javamas.kernel.messages.Message;

/**
 *
 * @author Guillaume Monet
 */
public class Priority extends Agent {

    @Override
    public void activate() {
        this.getOrganization().joinGroup("TEST", "PRIORITY");
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

        Agent a = new Agent() {
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
        a.getOrganization().joinGroup("TEST", "PRIORITY");
        Message<String> m = new Message<>();
        m.setContent("LOW PRIORITY");
        m.setPriority(Message.LOW_PRIORITY);
        m.setOrganization(a.getOrganization());
        a.sendMessage(m);
        System.out.println("Send Low priority");

        m.setContent("HIGH PRIORITY");
        m.setPriority(Message.HIGH_PRIORITY);
        m.setOrganization(a.getOrganization());
        a.sendMessage(m);
        System.out.println("Send HI priority");

        m.setContent("NORMAL PRIORITY");
        m.setPriority(Message.NORMAL_PRIORITY);
        m.setOrganization(a.getOrganization());
        a.sendMessage(m);
        System.out.println("Send Normal priority");

        m.setContent("EXTREM PRIORITY");
        m.setPriority(Message.EXTREM_PRIORITY);
        m.setOrganization(a.getOrganization());
        a.sendMessage(m);
        System.out.println("Send Extrem priority");

        m.setContent("NORMAL PRIORITY");
        m.setPriority(Message.NORMAL_PRIORITY);
        m.setOrganization(a.getOrganization());
        a.sendMessage(m);
        System.out.println("Send Normal priority");
        p.stop();

    }
}
