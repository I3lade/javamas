package javamas.agents.test.messages;

/**
 *
 * @author guillaume-monet
 */
public class TestAgent {

    public static void main(String[] args) {
	(new Receiver_test()).start();
	(new Sender_test()).start();
    }
}
