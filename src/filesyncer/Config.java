/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class Config {
    static ArrayList pipelines = new ArrayList();
    static String config_filename = "config.dat";

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
    
    static public void storeConfig() {
        try {
            FileOutputStream fileOut = new FileOutputStream(Config.config_filename);
            ObjectOutputStream out =  new ObjectOutputStream(fileOut);
            out.writeObject(Config.pipelines);
            out.close();
            fileOut.close();
            System.out.println("[CONFIG] Configuration file saved to " + Config.config_filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static public void loadConfig() {
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(Config.config_filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Config.pipelines = (ArrayList) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("[CONFIG] Configuration file loaded from " + Config.config_filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileIn.close();
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}