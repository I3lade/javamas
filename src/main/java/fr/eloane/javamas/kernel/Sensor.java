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
package fr.eloane.javamas.kernel;

import java.io.Serializable;
import java.util.Observable;

/**
 *
 * @param <T>
 * @author Guillaume Monet
 */
public class Sensor<T> extends Observable implements Serializable {

    /**
     *
     */
    public static final int SPEED = 1;
    /**
     *
     */
    public static final int PRESSURE = 2;
    /**
     *
     */
    public static final int THERMAL = 3;
    /**
     *
     */
    public static final int BRIGHTNESS = 4;
    /**
     *
     */
    public static final int CONTACT = 5;
    private static final long serialVersionUID = -5754532731305532805L;
    private int type;
    private T value;

    /**
     *
     * @param type
     */
    public Sensor(int type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public T getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(T value) {
        this.value = value;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.value.toString();
    }
}
