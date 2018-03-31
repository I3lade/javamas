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
package javamas.kernel.datas;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Store and to remove T No null elements allowed in this queue
 *
 * @param <T>
 * @author Guillaume Monet
 * @version 1.5
 */
public class SynchronizedQueue<T> extends LinkedList<T> implements Serializable, Cloneable {

    /**
     *
     */
    public static final long serialVersionUID = 1L;

    /**
     *
     */
    public SynchronizedQueue() {
        super();
    }

    /**
     *
     * @param queue
     */
    public SynchronizedQueue(SynchronizedQueue<T> queue) {
        super(queue);
    }

    /**
     * push object in the list of data
     *
     * @param obj the object to store in the queue
     * @see pop()
     * @see pop(int wait)
     */
    @Override
    public synchronized void push(T obj) {
        super.add(obj);
        this.notify();
    }

    /**
     * return the fist of all object in the queue and remove it from the queue
     *
     * @return object
     * @see pop(int wait)
     */
    @Override
    public synchronized T pop() {
        return this.pop(0);
    }

    /**
     *
     * @param wait
     * @return T or null if stack empty
     */
    public synchronized T pop(int wait) {
        try {
            if (wait == 0) {
                while (this.size() == 0) {
                    this.wait();
                }
            } else {
                this.wait(wait);
            }
        } catch (InterruptedException e) {
        }
        return this.poll();
    }

    /**
     *
     * @return
     */
    public synchronized T getNextElement() {
        return this.peek();
    }

    /**
     * remove all elements from the queue
     */
    public synchronized void flush() {
        this.removeAll(Arrays.asList(this.toArray()));
    }

    /**
     * clone the current Stack
     *
     * @return new Queue
     */
    @Override
    public SynchronizedQueue<T> clone() {
        return new SynchronizedQueue<>(this);
    }
}
