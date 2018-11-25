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
package fr.eloane.javamas.kernel.transport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import fr.eloane.javamas.kernel.messages.Message;
import java.net.InetAddress;
import java.util.HashMap;

/**
 *
 * @author Guillaume Monet
 * @version 1.0
 */
public final class TransportMulticast extends Transport {

    private MulticastSocket multicastSocket = null;
    private DatagramPacket datagramPacket;
    private ByteArrayInputStream byteArrayInputStream;
    private ObjectInputStream objectInputStream;
    private final byte[] BUFFER = new byte[8192];
    public static final String IP = "IP";
    public static final String PORT = "PORT";

    private InetAddress ip;
    private int port;

    public TransportMulticast(HashMap<String, String> parameters) {
        super(parameters);
        try {
            this.ip = InetAddress.getByName(parameters.get(TransportMulticast.IP));
            this.port = Integer.parseInt(parameters.get(TransportMulticast.PORT));
            this.multicastSocket = new MulticastSocket(this.port);
            this.multicastSocket.joinGroup(ip);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            multicastSocket.setTimeToLive(255);
            multicastSocket.send(hi);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void kill() {
        this.multicastSocket.close();
    }

    /**
     *
     * @return
     */
    @Override
    public Message waitMessage() {
        try {
            datagramPacket = new DatagramPacket(BUFFER, BUFFER.length);
            multicastSocket.receive(datagramPacket);
            byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            System.out.println("RECEIVE MULTICAST");
            return (Message) objectInputStream.readObject();
        } catch (NullPointerException | IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
