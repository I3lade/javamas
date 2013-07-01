package javamas.kernel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import javamas.kernel.messages.Message;
import javamas.kernel.messages.NodeMessage;

/**
 * Project: JavaMAS: Java Multi-Agents System File: AgentNode.java
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr>
 * @version 1.0
 *
 */
public final class AgentNode implements Runnable {

    private static AgentNode comm = null;
    private volatile HashMap<Integer, AbstractAgent> agents = new HashMap<>();
    private volatile HashMap<String, ArrayList<Integer>> groups = new HashMap<>();
    private String ip = "239.255.80.84";
    private int port = 7889;
    private MulticastSocket sok = null;
    private int node = 0;
    private boolean local = true;
    private boolean stop = false;

    private AgentNode() {
	try {
	    this.node = this.hashCode();
	    sok = new MulticastSocket(port);
	    sok.joinGroup(InetAddress.getByName(ip));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     *
     * @return
     */
    public static AgentNode getHandle() {
	if (comm == null) {
	    comm = new AgentNode();
	    comm.start();
	}
	return comm;
    }

    /**
     *
     * @param local
     */
    public void setLocal(boolean local) {
	this.local = local;
    }

    /**
     *
     * @param agt
     */
    public synchronized void register(AbstractAgent agt) {
	agents.put(agt.getHashcode(), agt);
    }

    /**
     *
     * @param agt
     */
    public synchronized void unregister(AbstractAgent agt) {
	agents.remove(agt.getHashcode());
	if (agents.isEmpty()) {
	    stop();
	    AgentNode.comm = null;
	    System.out.println("Node Killed");
	}
    }

    /**
     *
     */
    public synchronized void pauseAll() {
	for (AbstractAgent a : agents.values()) {
	    a.pause();
	}
    }

    /**
     *
     */
    public synchronized void resumeAll() {
	for (AbstractAgent a : agents.values()) {
	    a.resume();
	}
    }

    /**
     *
     */
    public synchronized void stopAll() {
	for (AbstractAgent a : agents.values()) {
	    a.stop();
	}
    }

    /**
     *
     * @param delay
     */
    public synchronized void setDelayAll(int delay) {
	for (AbstractAgent a : agents.values()) {
	    a.setDelay(delay);
	}
    }

    /**
     *
     * @param hashcode
     * @return
     */
    public AbstractAgent getAgent(int hashcode) {
	return agents.get(hashcode);
    }

    /**
     *
     * @param groupe
     * @param role
     * @return
     */
    public synchronized ArrayList<Integer> getAgentsWithRole(String groupe, String role) {
	ArrayList<Integer> list = new ArrayList<>();
	for (int hashcode : agents.keySet()) {
	    if (agents.get(new Integer(hashcode)).hasGroupe(groupe)) {
		if (agents.get(new Integer(hashcode)).hasRole(role, groupe) || role == null) {
		    list.add(hashcode);
		}
	    }
	}
	return list;
    }

    /**
     *
     * @param hashcode
     * @param mes
     */
    public synchronized void sendMessage(int hashcode, Message mes) {
	if (agents.containsKey(new Integer(hashcode))) {
	    agents.get(hashcode).pushMessage(mes);
	} else {
	    broadcastMessage(mes, hashcode, null, null);
	}
    }

    /**
     *
     * @param groupe
     * @param role
     * @param mes
     */
    public synchronized void broadcastMessage(String groupe, String role, Message mes) {
	this.broadcastMessage(groupe, role, mes, true);
    }

    /**
     *
     * @param groupe
     * @param role
     * @param mes
     * @param bcast
     */
    public synchronized void broadcastMessage(String groupe, String role, Message mes, boolean bcast) {
	for (int hashcode : agents.keySet()) {
	    if (mes.getSender() != hashcode) {
		if (agents.get(hashcode).hasGroupe(groupe)) {
		    if (role == null || agents.get(hashcode).hasRole(groupe, role)) {
			agents.get(hashcode).pushMessage(mes);
		    }
		}
	    }
	}
	if (bcast && !local) {
	    this.broadcastMessage(mes, 0, groupe, role);
	}
    }

    /**
     *
     */
    public void start() {
	(new Thread(this)).start();
    }

    /**
     *
     */
    public void stop() {
	this.kill();
    }

    /**
     *
     */
    public void kill() {
	stop = true;
	sok.close();
    }

    private void broadcastMessage(Message mes, int hashcode, String groupe, String role) {
	NodeMessage message = new NodeMessage();
	message.hashcode = hashcode;
	message.groupe = groupe;
	message.role = role;
	message.mes = mes;
	message.node = this.node;
	try {
	    ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(bout);
	    out.writeObject(message);
	    out.close();
	    byte[] msg = bout.toByteArray();
	    DatagramPacket hi = new DatagramPacket(msg, msg.length, InetAddress.getByName(ip), port);
	    sok.send(hi);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void run() {
	DatagramPacket recv;
	ByteArrayInputStream bin;
	ObjectInputStream in;
	while (!stop) {
	    try {
		byte[] buf = new byte[8192];
		recv = new DatagramPacket(buf, buf.length);
		sok.receive(recv);
		bin = new ByteArrayInputStream(recv.getData());
		in = new ObjectInputStream(bin);
		NodeMessage mes = (NodeMessage) in.readObject();
		if (mes.node != node) {
		    if (mes.hashcode > 0) {
			sendMessage(mes.hashcode, mes.mes);
		    } else {
			broadcastMessage(mes.groupe, mes.role, mes.mes, false);
		    }
		}
	    } catch (NullPointerException | SocketException e) {
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }
}
