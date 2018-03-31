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
import java.util.ArrayList;

/**
 * Tree Collection : Each elements must be unique
 *
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 * @version 1.0
 *
 * @param <T> @author Guillaume Monet
 */
public class SynchronizedTree<T> implements Serializable {

    private T root;
    private ArrayList<SynchronizedTree<T>> childs;
    private double weight = 0;

    /**
     *
     */
    public SynchronizedTree() {
        this.childs = new ArrayList<>();
        this.root = null;
    }

    /**
     *
     * @param element
     */
    public SynchronizedTree(T element) {
        this();
        this.root = element;
    }

    /**
     *
     * @param element
     * @return
     */
    public synchronized SynchronizedTree<T> addNode(T element) {
        return this.addNode(element, 0);
    }

    /**
     *
     * @param element
     * @param weight
     * @return
     */
    public synchronized SynchronizedTree<T> addNode(T element, double weight) {
        SynchronizedTree t = new SynchronizedTree(element);
        t.setWeight(weight);
        if (!childs.contains(t)) {
            childs.add(t);
        }
        return childs.get(childs.indexOf(t));
    }

    /**
     *
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     *
     * @param t
     */
    public synchronized void addTree(SynchronizedTree t) {
        if (!childs.contains(t)) {
            childs.add(t);
        }
    }

    /**
     *
     * @param element
     */
    public synchronized void removeNode(T element) {
        for (SynchronizedTree<T> t : childs) {
            if (t != null) {
                if (t.getRoot().equals(element)) {
                    t = null;
                } else {
                    if (!t.isLeaf()) {
                        t.removeNode(element);
                    }
                }
            }
        }
    }

    /**
     *
     * @param element
     * @return
     */
    public synchronized SynchronizedTree<T> getTree(T element) {
        if (root.equals(element)) {
            return this;
        } else {
            SynchronizedTree found = null;
            for (SynchronizedTree t : childs) {
                found = t.getTree(element);
                if (found != null) {
                    break;
                }
            }
            return found;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<SynchronizedTree<T>> getChilds() {
        return this.childs;
    }

    /**
     *
     * @return
     */
    public ArrayList<T> getChildsAsList() {
        ArrayList<T> ret = new ArrayList<>();
        for (SynchronizedTree<T> t : this.childs) {
            ret.add(t.getRoot());
        }
        return ret;
    }

    /**
     * Only on direct childs
     *
     * @param element
     * @return
     */
    public boolean contains(T element) {
        for (SynchronizedTree<T> t : this.childs) {
            if (t.getRoot().equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public T getRoot() {
        return this.root;
    }

    /**
     *
     * @param element
     * @return
     */
    public ArrayList<T> getPath(T element) {
        return null;
    }

    /**
     *
     * @return
     */
    public boolean isLeaf() {
        return this.childs.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SynchronizedTree) {
            if (((SynchronizedTree) obj).root == this.root) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param prepend
     * @return
     */
    public String print(String prepend) {
        prepend = " " + prepend;
        String ret = "" + root;
        for (SynchronizedTree t : childs) {
            ret += "\n" + prepend + t.print(prepend);
        }
        return ret;
    }

    @Override
    public String toString() {
        return print("-");
    }
}
