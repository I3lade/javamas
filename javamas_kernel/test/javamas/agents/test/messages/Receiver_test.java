package javamas.agents.test.messages;

import java.io.IOException;
import javamas.kernel.AbstractAgent;
import javamas.kernel.AgentNode;
import javamas.kernel.exception.UnknownTransport;
import javamas.kernel.messages.Message;
import javamas.kernel.transport.TransportFactory;

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
public class Receiver_test extends AbstractAgent {

    /**
     *
     */
    @Override
    public void activate() {
	System.out.println("RECEIVER");
	this.joinGroupe("Convoyeur");

    }

    /**
     *
     */
    @Override
    public void live() {
	Message<String> mes;
	do {
	    mes = (Message<String>) this.waitNextMessage();
	    System.out.println(mes.toString());
	    Message<String> m = new Message<>("I'M HERE");
	    this.sendMessage(m, mes.getSender());
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
	    AgentNode.getHandle().addTransport(TransportFactory.getTransport(TransportFactory.TRANSPORT_MULTICAST, "239.255.80.84", 7889));
	} catch (UnknownTransport | IOException ex) {
	}
	(new Receiver_test()).start();
    }
}
