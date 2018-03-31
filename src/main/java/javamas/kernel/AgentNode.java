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
package javamas.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javamas.kernel.messages.Message;
import javamas.kernel.transport.Transport;
import javamas.kernel.datas.SynchronizedTree;

/**
 * Project: JavaMAS: Java Multi-Agents System File: AgentNode.java
 *
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr> @version 1.0
 */
public final class AgentNode implements Observer {

    private static AgentNode comm = null;
    private HashMap<String, AbstractAgent<?>> agents = new HashMap<>();
    private ArrayList<Transport> transports = new ArrayList<>();

    private AgentNode() {
    }

    /**
     *
     * @param t
     */
    public void addTransport(Transport t) {
        transports.add(t);
        t.addObserver(this);
        t.start();
    }

    /**
     *
     * @param t
     */
    public void removeTransport(Transport t) {
        t.deleteObserver(this);
        transports.remove(t);
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
     * @param agt
     */
    public synchronized void register(AbstractAgent<?> agt) {
        agents.put(agt.getAddress().getId(), agt);
    }

    /**
     *
     * @param agt
     */
    public synchronized void unregister(AbstractAgent<?> agt) {
        agents.remove(agt.getAddress().getId());
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
        for (AbstractAgent<?> a : agents.values()) {
            a.pause();
        }
    }

    /**
     *
     */
    public synchronized void resumeAll() {
        for (AbstractAgent<?> a : agents.values()) {
            a.resume();
        }
    }

    /**
     *
     */
    public synchronized void stopAll() {
        for (AbstractAgent<?> a : agents.values()) {
            a.stop();
        }
    }

    /**
     *
     * @param delay
     */
    public synchronized void setDelayAll(int delay) {
        for (AbstractAgent<?> a : agents.values()) {
            a.setDelay(delay);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public AbstractAgent<?> getAgent(String id) {
        return agents.get(id);
    }

    /**
     * TODO: Correct change message If all receivers found on current node : no
     * bcast Compare two organizations send or no Really dirty method need to be
     * improved
     *
     * @param mes
     */
    public synchronized void sendMessage(Message<?> mes) {
        if (mes.getReceivers() != null) {
            for (String id : mes.getReceivers()) {
                if (agents.containsKey(id)) {
                    agents.get(id).pushMessage(mes);
                } else {
                    broadcastMessage(mes);
                }
            }
        }
        if (mes.getOrganizations() != null) {
            for (SynchronizedTree<String> org : mes.getOrganizations()) {
                for (String id : agents.keySet()) {
                    if (!mes.getSender().equals(id)) {
                        for (SynchronizedTree<String> groups : org.getChilds()) {
                            if (agents.get(id).hasGroupe(groups.getRoot())) {
                                if (!groups.isLeaf()) {
                                    for (SynchronizedTree<String> roles : groups.getChilds()) {
                                        if (agents.get(id).hasRole(groups.getRoot(), roles.getRoot())) {
                                            agents.get(id).pushMessage(mes);
                                        }
                                    }
                                } else {
                                    agents.get(id).pushMessage(mes);
                                }
                            }
                        }
                    }
                }
            }
            broadcastMessage(mes);
        }
    }

    /**
     *
     * @param mes
     */
    private void broadcastMessage(Message<?> mes) {
        for (Transport t : transports) {
            t.sendMessage(mes);
        }
    }

    /**
     *
     */
    public void start() {
        //(new Thread(this)).start();
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
    private void kill() {
        for (Transport t : transports) {
            t.close();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Message) {
            this.sendMessage((Message) arg);
        }
    }
}
