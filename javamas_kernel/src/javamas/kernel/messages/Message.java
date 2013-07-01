package javamas.kernel.messages;

import java.io.Serializable;

/**
 * Project: JavaMAS: Java Multi-Agents System File: Message.java
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
 * @param <T> content of the message
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr>
 * @version 1.0
 */
public class Message<T> implements Cloneable, Serializable, Comparable<Message<?>> {

    /**
     * High priority
     */
    public static final int HIGH_PRIORITY = 1;
    /**
     * Low priority
     */
    public static final int LOW_PRIORITY = -1;
    /**
     * Normal priority
     */
    public static final int NORMAL_PRIORITY = 0;
    /**
     * Highest priority for the message
     */
    public static final int EXTREM_PRIORITY = 2;
    private int sender = 0;
    private int receiver = 0;
    private int priority;
    private long time = 0;
    private long expire = 0;
    private static final long serialVersionUID = 12L;
    /**
     *
     */
    protected T content = null;

    /**
     *
     */
    public Message() {
	this.priority = NORMAL_PRIORITY;
	time = System.currentTimeMillis();
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
	this.expire = expire;
    }

    /**
     *
     * @param expire
     */
    public Message(long expire) {
	this.expire = expire;
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
    public final void setSender(int s) {
	this.sender = s;
    }

    /**
     * @param s
     */
    public final void setReceiver(int s) {
	this.receiver = s;
    }

    /**
     * Returns the original sender. This information can be trusted
     *
     * @return
     */
    public final int getSender() {
	return sender;
    }

    /**
     *
     * @return
     */
    public int getPriority() {
	return this.priority;
    }

    /**
     *
     * @param priority
     */
    public void setPriority(int priority) {
	this.priority = priority;
    }

    /**
     * @return
     */
    public final int getReceiver() {
	return receiver;
    }

    /**
     *
     * @return creation
     */
    public final long getTime() {
	return time;
    }

    /**
     *
     * @return expire date
     */
    public final long getExpire() {
	return expire;
    }

    /**
     * @return
     */
    @Override
    public Message<?> clone() {
	try {
	    return (Message<?>) super.clone();
	} catch (CloneNotSupportedException e) {
	    throw new InternalError();
	}
    }

    /**
     *
     * @param mess
     * @return
     */
    @Override
    public int compareTo(Message<?> mess) {
	if (this.priority < mess.priority) {
	    return 1;
	} else if (this.priority > mess.priority) {
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
     * Returns a debug string with enveloppe and content for the message
     *
     * @return message's content + other info
     */
    @Override
    public String toString() {
	return "\n" + this.priority + "\nSender:" + sender + "\n" + " Receiver:" + receiver + "\n" + " Class:" + this.getClass();
    }
}