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

import java.io.Serializable;
import java.util.Observable;

/**
 *
 * @param <T>
 * @author Guillaume Monet
 */
public class AgentSensor<T> extends Observable implements Serializable {

    private static final long serialVersionUID = 1L;
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
    private int type;
    private T value;

    /**
     *
     * @param type
     */
    public AgentSensor(int type) {
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
