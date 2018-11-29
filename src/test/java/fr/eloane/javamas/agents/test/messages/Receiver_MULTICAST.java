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
package fr.eloane.javamas.agents.test.messages;

import fr.eloane.javamas.kernel.Agent;
import fr.eloane.javamas.kernel.Node;
import fr.eloane.javamas.kernel.exception.UnknownTransport;
import fr.eloane.javamas.kernel.messages.Message;
import fr.eloane.javamas.kernel.transport.TransportFactory;
import fr.eloane.javamas.kernel.transport.TransportMulticast;
import java.util.HashMap;

/*
 * Convoyeur.java
 *
 * Created on April 29, 2003, 2:35 PM
 */
/**
 *
 * @author monet
 * @version
 */
public class Receiver_MULTICAST extends Agent {

    /**
     *
     */
    @Override
    public void activate() {
        System.out.println("RECEIVER" + this.getAddress().getId());
        this.getOrganization().joinGroup("Agent", "Convoyeur");

    }

    /**
     *
     */
    @Override
    public void live() {
        Message<String> mes;
        do {
            mes = (Message<String>) this.waitNextMessage();
            System.out.println(mes);
            this.sendMessage(mes.reply().setContent("I'M HERE"));
        } while (!mes.getContent().equals("KILL"));

    }

    /**
     *
     */
    @Override
    public void end() {
    }

    public static void main(String[] args) {

        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put(TransportMulticast.IP, "239.255.80.84");
            parameters.put(TransportMulticast.PORT, "7889");
            Node.getHandle().addTransport(TransportFactory.getTransport(TransportFactory.TRANSPORT_MULTICAST, parameters));        

            (new Receiver_MULTICAST()).start();
        } catch (UnknownTransport ex) {
            ex.printStackTrace();
        }
    }
}
