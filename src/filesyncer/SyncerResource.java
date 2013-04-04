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
public interface SyncerResource {
    public java.util.List<SyncerFile> getFiles();
    public SyncerFile findByPath(String path) throws FileNotFoundException;
    public void putFile(SyncerFile file);
    public boolean canWrite();
}
