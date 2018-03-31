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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import javamas.kernel.messages.Message;

/**
 *
 * @author Guillaume Monet
 */
public abstract class Transport extends Observable implements Runnable {

    private boolean stop = false;
    /**
     *
     */
    protected InetAddress ip;
    /**
     *
     */
    protected int port;
    private ArrayList<String> messages = new ArrayList<>();

    /**
     *
     * @param ip
     * @param port
     * @throws UnknownHostException
     */
    public Transport(String ip, int port) throws UnknownHostException {
        this.ip = InetAddress.getByName(ip);
        this.port = port;
    }

    /**
     *
     */
    public final void start() {
        (new Thread(this)).start();
    }

    /**
     *
     * @param mess
     */
    public abstract void sendMessage(Message<?> mess);

    /**
     *
     */
    public void close() {
        stop = true;
        kill();
    }

    /**
     *
     */
    public abstract void kill();

    @Override
    public final void run() {
        while (!stop) {
            Object mes = waitMessage();
            if (mes instanceof Message) {
                Message m = (Message) mes;
                if (!messages.contains(m.getId())) {
                    this.messages.add(m.getId());
                    this.setChanged();
                    this.notifyObservers(m);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public abstract Object waitMessage();
}
