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
package fr.eloane.javamas.agents.test.cgr;

import fr.eloane.javamas.kernel.datas.SynchronizedTree;
import fr.eloane.javamas.kernel.organization.Organization;

/**
 *
 * @author Guillaume Monet
 */
public class CGR {

    public static void main(String[] args) {
        Organization manager = new Organization("TEST");
        manager.addRole("WORLD", "BEEGROUP", "BEE");
        manager.addRole("WORLD", "BEEGROUP", "FOLLOWER");
        manager.addRole("WORLD", "QUEEN", "BEE");
        manager.addRole("WORLD", "QUEEN", "LEADER");

        System.out.println(manager);

        System.out.println(manager.isInCommunity("WORLD"));
        System.out.println(manager.isInCommunity("WLD"));
        System.out.println(manager.isInGroup("WORLD", "BEEGROUP"));
        System.out.println(manager.isInGroup("WORLD", "QUEEN"));
        System.out.println(manager.isInGroup("WORLD", "TOTO"));
        System.out.println(manager.hasRole("WORLD", "QUEEN", "BEE"));
        System.out.println(manager.hasRole("WORLD", "QUEEN", "ROBERT"));
        SynchronizedTree<String> tree = new SynchronizedTree<>("TEST");
        tree.addNode("WORLD").addNode("BEEGROUP");
        tree.addNode("WORLD").addNode("QUEEN").addNode("BE");
        System.out.println("-----------"+tree);
        System.out.println(manager.compare(tree));

    }
}
