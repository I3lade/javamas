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

/**
 *
 * @author Guillaume Monet
 */
public final class AgentScheduler {

    private boolean pause = false;
    private boolean go = true;
    private long delay = 0;
    private long pause_time = 0;

    /**
     *
     * @return
     */
    public synchronized boolean nextStep() {
        try {
            while (pause || pause_time > 0) {
                this.wait(pause_time);
                this.pause_time = 0;
            }
            if (delay > 0) {
                this.wait(delay);
            }
        } catch (InterruptedException ex) {
        }
        return go;
    }

    /**
     *
     */
    public void pause() {
        this.pause = true;
    }

    /**
     *
     * @param time
     */
    public void pause(long time) {
        this.pause_time = time;
    }

    /**
     *
     */
    public synchronized void resume() {
        this.pause = false;
        this.notify();
    }

    /**
     *
     */
    public void stop() {
        this.go = false;
    }

    /**
     *
     * @param delay
     */
    public void setDelay(long delay) {
        this.delay = delay;
    }
}
