/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.util.HashMap;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class WebDAVSyncConfig implements SyncerConfig {
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
        return new WebDAVSyncProvider(
            config.get("base_path"), config.get("dav_path"),
            config.get("username"),  config.get("password")
        );
    }
    
}
