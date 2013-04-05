/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.util.ArrayList;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class Config {
    static ArrayList pipelines = new ArrayList();

    public static ArrayList getPipelines() {
        return pipelines;
    }

    public static void setPipelines(ArrayList pipelines) {
        Config.pipelines = pipelines;
    }

    static public void addPipeline(SyncerPipeline pipeline) {
        Config.pipelines.add(pipeline);

        /*
        System.out.println("[CONFIG - ADD PIPELINE]");
        SyncerPipeline pl = (SyncerPipeline)Config.pipelines.get(Config.pipelines.size() - 1);
        System.out.println(pl.getConfigFrom().getConfig());
        System.out.println(pl.getConfigTo().getConfig());
        */
    }
    
    static public SyncerPipeline getPipeline() {
        return null;
    }
}