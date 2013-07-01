package javamas.kernel;

import java.io.Serializable;
import java.util.Observable;

/**
 *
 * @param <T> 
 * @author Guillaume Monet
 */
public class AgentSensor<T> extends Observable implements Serializable {

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

    @Override
    public String toString() {
	return this.value.toString();
    }
}
