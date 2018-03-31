package javamas.kernel.utils;

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
