/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.io.FileNotFoundException;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class Syncer {
    private SyncerResource local;
    private SyncerResource remote;

    public Syncer(SyncerResource local, SyncerResource remote) {
        this.local = local;
        this.remote = remote;
    }
    
    public void sync() {
        for (SyncerFile remote_file : remote.getFiles()) {
            System.out.println(remote_file.getPath());
            try {
                SyncerFile local_file = local.findByPath(remote_file.getPath());
                //if (!local_file.getMD5().equals(remote_file.getMD5())) {
                if (!local_file.getTimestamp().equals(remote_file.getTimestamp())) {
                    if (remote.canWrite() && local_file.getTimestamp().after(remote_file.getTimestamp())) {
                        remote.putFile(local_file);
                    } else {
                        local.putFile(remote_file);
                    }
                }
            } catch (FileNotFoundException e) {
                local.putFile(remote_file);
            }
        }
    }
}
