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
package javamas.kernel.transport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import javamas.kernel.messages.Message;

/**
 *
 * @author Guillaume Monet
 * @version 1.0
 */
public final class TransportMulticast extends Transport {

    private MulticastSocket sok = null;
    private DatagramPacket recv;
    private ByteArrayInputStream bin;
    private ObjectInputStream in;
    private final byte[] BUFFER = new byte[8192];

    /**
     *
     * @param ip
     * @param port
     * @throws UnknownHostException
     * @throws IOException
     */
    public TransportMulticast(String ip, int port) throws UnknownHostException, IOException {
        super(ip, port);
        this.sok = new MulticastSocket(this.port);
        this.sok.joinGroup(this.ip);
    }

    /**
     *
     * @param mess
     */
    @Override
    public void sendMessage(Message<?> mess) {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream(bout)) {
            out.writeObject(mess);
            byte[] msg = bout.toByteArray();
            DatagramPacket hi = new DatagramPacket(msg, msg.length, ip, port);
            sok.send(hi);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void kill() {
        this.sok.close();
    }

    /**
     *
     * @return
     */
    @Override
    public Message waitMessage() {
        try {
            recv = new DatagramPacket(BUFFER, BUFFER.length);
            sok.receive(recv);
            bin = new ByteArrayInputStream(recv.getData());
            in = new ObjectInputStream(bin);
            return (Message) in.readObject();
        } catch (NullPointerException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
