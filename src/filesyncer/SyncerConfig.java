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
public interface SyncerConfig {
    public HashMap<String, String> getConfig();
    public void setConfig(HashMap<String, String> config);
    public SyncerResource getSyncProvider();
}
