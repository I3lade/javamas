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

/**
 *
 * @param <T>
 * @author Guillaume Monet
 */
public final class AgentProbeValue<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final T value;
    private final String desc;

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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "PROBE VALUE : " + desc + " " + value.toString();
    }
}
