package javamas.agents.test.messages;

import java.io.IOException;
import javamas.kernel.AbstractAgent;
import javamas.kernel.AgentNode;
import javamas.kernel.exception.UnknownTransport;
import javamas.kernel.messages.Message;
import javamas.kernel.transport.TransportFactory;

/**
 *
 * @author monet
 * @version
 */
public class Sender_test extends AbstractAgent {

    /**
     *
     */
    @Override
    public void activate() {
	System.out.println("SENDER");
	this.joinGroupe("Fourmis");
    }

    /**
     *
     */
    @Override
    public void live() {
	for (int i = 0; i < 1; i++) {
	    this.broadcastMessage(new Message<>("WHERE ARE YOU"), "Convoyeur");
	    Message<String> mes = this.waitNextMessage();
	    System.out.println(mes.toString());
	}
	this.broadcastMessage(new Message<>("KILL"), "Convoyeur");
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
	(new Sender_test()).start();
    }
}
