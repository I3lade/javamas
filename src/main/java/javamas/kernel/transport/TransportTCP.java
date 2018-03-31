package javamas.kernel.transport;

import java.net.UnknownHostException;
import javamas.kernel.messages.Message;

/**
 *
 * @author Guillaume Monet
 */
public class TransportTCP extends Transport {

    /**
     *
     * @param ip
     * @param port
     * @throws UnknownHostException
     */
    public TransportTCP(String ip, int port) throws UnknownHostException {
        super(ip, port);
    }

    /**
     *
     * @param mess
     */
    @Override
    public void sendMessage(Message<?> mess) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void kill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    @Override
    public Object waitMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
