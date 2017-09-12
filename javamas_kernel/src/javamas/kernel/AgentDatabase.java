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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import javamas.kernel.utils.FileUtils;

/**
 * Project: JavaMAS: Java Multi-Agents System File: AgentDatabase.java
 *
 * @param <T>
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr> @version 1.1
 */
public final class AgentDatabase<T> extends HashMap<String, T> {

    private static final long serialVersionUID = 1L;
    private File db = null;

    /**
     *
     * @param filename
     * @param load
     */
    public AgentDatabase(String filename, boolean load) {
        super();
        db = new File(FileUtils.HOME + "/" + filename + ".db");
        if (!db.exists()) {
            try {
                db.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (load) {
            this.load();
        }
    }

    /**
     *
     */
    public synchronized void flush() {
        try {
            super.clear();
            db.delete();
            db.createNewFile();
            this.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public synchronized void load() {

        try (FileInputStream stream = new FileInputStream(db); ObjectInputStream in = new ObjectInputStream(stream)) {
            super.clear();
            super.putAll((HashMap<String, T>) in.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
     */
    public synchronized void save() {

        try (FileOutputStream stream = new FileOutputStream(db); ObjectOutputStream out = new ObjectOutputStream(stream)) {
            out.writeObject(super.entrySet());
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
