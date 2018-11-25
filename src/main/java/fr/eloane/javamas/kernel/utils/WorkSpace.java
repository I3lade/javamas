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
package fr.eloane.javamas.kernel.utils;

import java.io.File;

/**
 * Genere ou charge l'espace de travail ~/JavaMAS/agents : sources des agents
 * ~/JavaMAS/build : binaires des agents ~/JavaMAS/deploy : jar des agents
 * ~/JavaMAS/docs : docs des agents ~/JavaMAS/tmp : repertoire temporaires
 *
 * @author guillaume-monet
 */
public abstract class WorkSpace {

    private WorkSpace() {
    }

    /**
     *
     * @throws SecurityException
     */
    public static void checkWorkSpace() throws SecurityException {
        File agents = new File(FileUtils.HOME + FileUtils.SEPARATOR + "JavaMAS" + FileUtils.SEPARATOR + "agents");
        File build = new File(FileUtils.HOME + FileUtils.SEPARATOR + "JavaMAS" + FileUtils.SEPARATOR + "build");
        File deploy = new File(FileUtils.HOME + FileUtils.SEPARATOR + "JavaMAS" + FileUtils.SEPARATOR + "deploy");
        File docs = new File(FileUtils.HOME + FileUtils.SEPARATOR + "JavaMAS" + FileUtils.SEPARATOR + "docs");
        File tmp = new File(FileUtils.HOME + FileUtils.SEPARATOR + "JavaMAS" + FileUtils.SEPARATOR + "tmp");
        agents.mkdirs();
        build.mkdirs();
        deploy.mkdirs();
        docs.mkdirs();
        tmp.mkdirs();
    }
}
