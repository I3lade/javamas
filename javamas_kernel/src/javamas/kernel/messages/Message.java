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
package javamas.kernel.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import javamas.kernel.AgentNode;
import javamas.kernel.datas.SynchronizedTree;

/**
 * Project: JavaMAS: Java Multi-Agents System File: Message.java
 *
 * @param <T> content of the message
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr> @version 1.1
 */
public class Message<T> extends HashMap<String, Object> implements Cloneable, Serializable, Comparable<Message<?>> {

    /**
     *
     */
    public static final String MESSAGE_ID = "message-id";
    /**
     *
     */
    public static final String IN_REPLY_TO = "in-reply-to";
    /**
     *
     */
    public static final String REPLY_BY = "reply_by";
    /**
     *
     */
    public static final String REPLY_TO = "reply-to";//Participant in communication
    /**
     *
     */
    public static final String LANGUAGE = "language";//Description of Content
    /**
     *
     */
    public static final String ENCODING = "encoding";//Description of Content
    /**
     *
     */
    public static final String ONTOLOGY = "ontology";//Description of Content
    /**
     *
     */
    public static final String PROTOCOL = "protocol";//Control of conversation  
    /**
     *
     */
    public static final String REPLY_WITH = "reply-with";//Control of conversation
    /**
     *
     */
    public static final String CONVERSATION_ID = "conversation_id";
    /**
     *
     */
    public static final String PRIORITY = "priority";
    /**
     *
     */
    public static final String EXPRIRE = "expire";
    /**
     *
     */
    public static final String CREATED = "created";
    /**
     *
     */
    public static final String SENDER = "sender";
    /**
     *
     */
    public static final String RECEIVERS = "receivers";
    /**
     *
     */
    public static final String RECEIVERS_ORGANIZATIONS = "receivers-organization";
    /**
     *
     */
    public static final int HIGH_PRIORITY = 1;
    /**
     *
     */
    public static final int LOW_PRIORITY = -1;
    /**
     *
     */
    public static final int NORMAL_PRIORITY = 0;
    /**
     *
     */
    public static final int EXTREM_PRIORITY = 2;
    private static AtomicInteger message_inc = new AtomicInteger();
    private static AtomicInteger conversation_inc = new AtomicInteger();
    private static final long serialVersionUID = 12L;
    /**
     *
     */
    protected T content = null;

    /**
     *
     */
    public Message() {
        this.put(Message.PRIORITY, NORMAL_PRIORITY);
        this.put(Message.CREATED, System.currentTimeMillis());
        this.put(Message.MESSAGE_ID, "M:" + message_inc.getAndIncrement() + "@" + AgentNode.getHandle().hashCode());
    }

    /**
     *
     * @param content
     */
    public Message(T content) {
        this();
        this.setContent(content);
    }

    /**
     *
     * @param content
     * @param expire
     */
    public Message(T content, long expire) {
        this();
        this.setContent(content);
        this.put(Message.EXPRIRE, expire);
    }

    /**
     *
     * @param expire
     */
    public Message(long expire) {
        this();
        this.put(Message.EXPRIRE, expire);
    }

    /**
     *
     * @return
     */
    public final String getId() {
        return this.get(MESSAGE_ID).toString();
    }

    /**
     *
     * @return
     */
    public final T getContent() {
        return this.content;
    }

    /**
     *
     * @param content
     */
    public final void setContent(T content) {
        this.content = content;
    }

    /**
     * @param s
     */
    public final void setSender(String s) {
        this.put(Message.SENDER, s);
    }

    /**
     * Returns the original sender. This information can be trusted
     *
     * @return
     */
    public final String getSender() {
        return (String) this.get(Message.SENDER);
    }

    /**
     * @param s
     */
    public final void addReceiver(String s) {
        ArrayList<String> receivers = (ArrayList<String>) this.get(Message.RECEIVERS);
        if (receivers == null) {
            receivers = new ArrayList<>();
        }
        receivers.add(s);
        this.setReceivers(receivers);
    }

    /**
     *
     * @param s
     */
    public final void removeReceiver(String s) {
        ArrayList<String> receivers = (ArrayList<String>) this.get(Message.RECEIVERS);
        if (receivers == null) {
            receivers = new ArrayList<>();
        }
        receivers.remove(s);
        this.setReceivers(receivers);
    }

    /**
     *
     * @param r
     */
    public final void addReceivers(ArrayList<String> r) {
        ArrayList<String> receivers = (ArrayList<String>) this.get(Message.RECEIVERS);
        if (receivers == null) {
            receivers = new ArrayList<>();
        }
        receivers.addAll(r);
        this.setReceivers(receivers);
    }

    /**
     *
     * @param r
     */
    public final void setReceivers(ArrayList<String> r) {
        this.put(Message.RECEIVERS, r);
    }

    /**
     * @return
     */
    public final ArrayList<String> getReceivers() {
        ArrayList<String> receivers = (ArrayList<String>) this.get(Message.RECEIVERS);
        if (receivers == null) {
            receivers = new ArrayList<>();
        }
        return receivers;
    }

    /**
     *
     */
    public final void removeReceivers() {
        ArrayList<String> receivers = (ArrayList<String>) this.get(Message.RECEIVERS);
        if (receivers == null) {
            receivers = new ArrayList<>();
        }
        receivers.removeAll(receivers);
        this.setReceivers(receivers);
    }

    /**
     *
     * @param t
     */
    public final void addOrganization(SynchronizedTree<String> t) {
        ArrayList<SynchronizedTree<String>> organizations = (ArrayList<SynchronizedTree<String>>) this.get(Message.RECEIVERS_ORGANIZATIONS);
        if (organizations == null) {
            organizations = new ArrayList<>();
        }
        organizations.add(t);
        this.put(Message.RECEIVERS_ORGANIZATIONS, organizations);
    }

    /**
     *
     * @param t
     */
    public final void removeOrganization(SynchronizedTree<String> t) {
        ArrayList<SynchronizedTree<String>> organizations = (ArrayList<SynchronizedTree<String>>) this.get(Message.RECEIVERS_ORGANIZATIONS);
        if (organizations == null) {
            organizations = new ArrayList<>();
        }
        organizations.remove(t);
        this.put(Message.RECEIVERS_ORGANIZATIONS, organizations);
    }

    /**
     *
     * @return
     */
    public final ArrayList<SynchronizedTree<String>> getOrganizations() {
        return (ArrayList<SynchronizedTree<String>>) this.get(Message.RECEIVERS_ORGANIZATIONS);
    }

    /**
     *
     * @return
     */
    public final int getPriority() {
        return Integer.parseInt(this.get(Message.PRIORITY).toString());
    }

    /**
     *
     * @param priority
     */
    public final void setPriority(int priority) {
        this.put(Message.PRIORITY, priority);
    }

    /**
     *
     * @return creation
     */
    public final long getTime() {
        return Long.parseLong(this.get(Message.CREATED).toString());
    }

    /**
     *
     * @return expire date
     */
    public final long getExpire() {
        return Long.parseLong(this.get(Message.EXPRIRE).toString());
    }

    /**
     *
     * @param key
     * @param value
     */
    public final void setField(String key, Object value) {
        if (!key.equals(Message.CREATED) && !key.equals(Message.MESSAGE_ID)) {
            this.put(key, value);
        }
    }

    /**
     * @return
     */
    @Override
    public Message<?> clone() {
        return (Message<?>) super.clone();
    }

    /**
     *
     * @param mess
     * @return
     */
    @Override
    public int compareTo(Message<?> mess) {
        if (this.getPriority() < mess.getPriority()) {
            return 1;
        } else if (this.getPriority() > mess.getPriority()) {
            return -1;
        } else {
            if (this.getTime() < mess.getTime()) {
                return 1;
            } else if (this.getTime() > mess.getTime()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Reply this message
     *
     * @return a new message with sender id as receiver and conversation id and
     * same fields
     */
    public Message reply() {
        return null;
    }

    /**
     *
     * @return
     */
    public String generateConversationId() {
        return "C:" + Message.conversation_inc.getAndIncrement() + "@" + AgentNode.getHandle().hashCode();
    }

    /**
     * Returns a debug string with enveloppe and content for the message
     *
     * @return message's content + other info
     */
    @Override
    public String toString() {
        String ret = "";
        for (Entry<String, Object> s : this.entrySet()) {
            ret += s.getKey() + ":" + s.getValue().toString().replace("\n", "") + "\n";
        }
        ret += "Content:\n" + this.getContent().toString() + "\n";
        return ret;
    }
}
