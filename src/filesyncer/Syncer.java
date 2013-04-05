/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class Syncer {
    private SyncerResource local;
    private SyncerResource remote;

    /**
     * creates a new syncer
     * 
     * @param local
     * @param remote 
     */
    public Syncer(SyncerResource local, SyncerResource remote) {
        this.local = local;
        this.remote = remote;
    }
    
    /**
     * sync the two sources
     */
    public void sync() {
        for (SyncerFile remote_file : remote.getFiles()) {
            System.out.println("[SYNCER REMOTE-FILE] " + remote_file.getPath());
            try {
                SyncerFile local_file = local.findByPath(remote_file.getPath());
                
                // get last modification date for local and remote file
                Date remote_timestamp = remote_file.getTimestamp();
                Date local_timestamp = local_file.getTimestamp();
                
                System.out.println("[SYNCER REMOTE-TIMESTAMP] " + remote_timestamp);
                System.out.println("[SYNCER LOCAL-TIMESTAMP] " + local_timestamp);
                
                /*
                 * if the modification times differ, check which one is
                 * newer and overwrite the older file with the newer one
                 */

                if (!local_timestamp.equals(remote_timestamp) /* || local_file.getLength() != remote_file.getLength() */) {
                    if (remote.canWrite() && local_timestamp.after(remote_timestamp)) {
                        remote.putFile(local_file, local_timestamp);
                    } else {
                        local.putFile(remote_file, remote_timestamp);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                Date remote_timestamp = remote_file.getTimestamp();
                local.putFile(remote_file, remote_timestamp);
            }
        }
    }
}
