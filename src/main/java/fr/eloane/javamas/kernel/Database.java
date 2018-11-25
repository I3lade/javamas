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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import fr.eloane.javamas.kernel.utils.FileUtils;

/**
 * 
 * @author Guillaume Monet
 * @param <T> 
 */
public final class Database<T> extends HashMap<String, T> {

    private static final long serialVersionUID = 1L;
    private File db = null;

    /**
     *
     * @param filename
     * @param load
     */
    public Database(String filename, boolean load) {
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
