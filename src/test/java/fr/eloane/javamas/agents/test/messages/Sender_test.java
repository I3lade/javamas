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
import fr.eloane.javamas.kernel.organization.Organization;
import fr.eloane.javamas.kernel.transport.TransportFactory;
import fr.eloane.javamas.kernel.transport.TransportMulticast;
import fr.eloane.javamas.kernel.transport.TransportUDP;
import java.util.HashMap;

/**
 *
 * @author monet
 * @version
 */
public class Sender_test extends Agent {

    /**
     *
     */
    @Override
    public void activate() {
        System.out.println("SENDER " + this.getAddress().getId());
        this.getOrganization().joinGroup("Agent", "Fourmis");
    }

    /**
     *
     */
    @Override
    public void live() {
        Organization organization = new Organization();
        organization.joinGroup("Agent", "Convoyeur");
        Message mess = new Message<>("WHERE ARE YOU");
        mess.addOrganization(organization);
        this.sendMessage(mess);
        Message<String> mes;
        while (nextStep()) {
            mes = this.waitNextMessage();
            System.out.println(mes);
            System.out.println("RECEVE");
        }
        this.sendMessage(new Message<>("KILL").addOrganization(organization));
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

            HashMap<String, String> parametersUDP = new HashMap<>();
            parametersUDP.put(TransportUDP.DESTINATIONIP, "192.168.0.4");
            parametersUDP.put(TransportUDP.DESTINATIONPORT, "8001");
            parametersUDP.put(TransportUDP.SERVERIP, "192.168.0.4");
            parametersUDP.put(TransportUDP.SERVERPORT, "8000");
            Node.getHandle().addTransport(TransportFactory.getTransport(TransportFactory.TRANSPORT_UDP, parametersUDP));

            (new Sender_test()).start();
        } catch (UnknownTransport ex) {
            ex.printStackTrace();
        }
    }
}
