/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class LocalSyncConfig implements SyncerConfig, Serializable {
    private HashMap<String, String> config;
    
    @Override
    public HashMap<String, String> getConfig() {
        return config;
    }

    @Override
    public void setConfig(HashMap<String, String> config) {
        this.config = config;
    }   

    @Override
    public SyncerResource getSyncProvider() {
        return new LocalSyncProvider(config.get("local_path"));
    }
}
