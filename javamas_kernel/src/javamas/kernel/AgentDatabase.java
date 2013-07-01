package javamas.kernel;

import java.io.*;
import java.util.HashMap;
import javamas.kernel.utils.FileUtils;

/**
 * Project: JavaMAS: Java Multi-Agents System File: AgentDatabase.java
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *
 * @param <D> 
 * @link http://guillaume.monet.free.fr
 * @copyright 2003-2013 Guillaume Monet
 *
 * @author Guillaume Monet <guillaume dot monet at free dot fr>
 * @version 1.0
 */
public final class AgentDatabase<D> extends HashMap<String, D> {

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
	try {
	    FileInputStream stream = new FileInputStream(db);
	    ObjectInputStream in = new ObjectInputStream(stream);
	    super.clear();
	    super.putAll((HashMap<String, D>) in.readObject());
	    in.close();
	    stream.close();
	} catch (Exception e) {
	}
    }

    /**
     *
     */
    public synchronized void save() {
	try {
	    FileOutputStream stream = new FileOutputStream(db);
	    ObjectOutputStream out = new ObjectOutputStream(stream);
	    out.writeObject(super.entrySet());
	    out.flush();
	    out.close();
	    stream.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
