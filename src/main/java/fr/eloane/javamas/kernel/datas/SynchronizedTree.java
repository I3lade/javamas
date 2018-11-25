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
package fr.eloane.javamas.kernel.datas;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Tree Collection : Each elements must be unique
 *
 * @author Guillaume Monet
 * @param <T>
 */
public class SynchronizedTree<T> implements Serializable {

    private static final long serialVersionUID = -1075077374642499790L;

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
    public synchronized void addSubTree(SynchronizedTree<T> t) {
        if (!childs.contains(t)) {
            childs.add(t);
        }
    }

    /**
     *
     * @param element
     */
    public synchronized void removeNode(T element) {
        childs.stream().filter((t) -> (t != null)).forEachOrdered((t) -> {
            if (t.getRoot().equals(element)) {
                t = null;
            } else {
                if (!t.isLeaf()) {
                    t.removeNode(element);
                }
            }
        });
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
            for (SynchronizedTree t : this.childs) {
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
        this.childs.forEach((t) -> {
            ret.add(t.getRoot());
        });
        return ret;
    }

    /**
     * Only on direct childs
     *
     * @param element
     * @return
     */
    public boolean contains(T element) {
        return this.childs.stream().anyMatch((t) -> (t.getRoot().equals(element)));
    }

    /**
     *
     * @param tree
     * @return
     */
    public boolean compare(SynchronizedTree<T> tree) {
        if (this.getRoot().equals(tree.getRoot())) {
            if (tree.isLeaf()) {
                return true;
            }

            boolean ret = true;
            for (SynchronizedTree<T> subtree : tree.getChilds()) {
                if (this.contains(subtree.getRoot())) {
                    ret &= this.getTree(subtree.getRoot()).compare(subtree);
                } else {
                    ret = false;
                    break;
                }
            }
            return ret;
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
            return ((SynchronizedTree) obj).root == this.root;
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
