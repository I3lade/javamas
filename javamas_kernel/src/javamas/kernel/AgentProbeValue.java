package javamas.kernel;

import java.io.Serializable;

/**
 *
 * @param <T> 
 * @author Guillaume Monet
 */
public final class AgentProbeValue<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private T value;
    private String desc;

    /**
     *
     * @param desc
     * @param value
     */
    public AgentProbeValue(String desc, T value) {
	this.desc = desc;
	this.value = value;
    }

    /**
     *
     * @return
     */
    public Class<?> getClassValue() {
	return value.getClass();
    }

    /**
     *
     * @return
     */
    public String getDescription() {
	return desc;
    }

    @Override
    public String toString() {
	return "PROBE VALUE : "+ desc + " " + value.toString();
    }
}
