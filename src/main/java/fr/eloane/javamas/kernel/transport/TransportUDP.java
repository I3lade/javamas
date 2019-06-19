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

import fr.eloane.javamas.kernel.messages.Message;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

/**
 *
 * @author Guillaume Monet
 */
public class TransportUDP extends Transport {

    private DatagramSocket datagramSocket = null;
    private DatagramPacket datagramPacket;
    private ByteArrayInputStream byteArrayInputStream;
    private ObjectInputStream objectInputStream;
    private final byte[] BUFFER = new byte[8192];
    public static final String SERVERIP = "SERVERIP";
    public static final String SERVERPORT = "SERVERPORT";
    public static final String DESTINATIONIP = "DESTINATIONIP";
    public static final String DESTINATIONPORT = "DESTINATIONPORT";

    private InetAddress serverAddress;
    private int serverPort;
    private int destinationPort;
    private InetAddress destinationAddress;

    public TransportUDP(HashMap<String, String> parameters) {
        super(parameters);
        try {
            this.serverAddress = InetAddress.getByName(parameters.get(TransportUDP.SERVERIP));
            this.serverPort = Integer.parseInt(parameters.get(TransportUDP.SERVERPORT));
            this.destinationAddress = InetAddress.getByName(parameters.get(TransportUDP.DESTINATIONIP));
            this.destinationPort = Integer.parseInt(parameters.get(TransportUDP.DESTINATIONPORT));
            this.datagramSocket = new DatagramSocket(this.serverPort, this.serverAddress);
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
            DatagramPacket hi = new DatagramPacket(msg, msg.length, destinationAddress, destinationPort);
            datagramSocket.send(hi);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void kill() {
        this.datagramSocket.close();
    }

    /**
     *
     * @return
     */
    @Override
    public Message waitMessage() {
        try {
            datagramPacket = new DatagramPacket(BUFFER, BUFFER.length);
            datagramSocket.receive(datagramPacket);
            byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Message) objectInputStream.readObject();
        } catch (NullPointerException | IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
