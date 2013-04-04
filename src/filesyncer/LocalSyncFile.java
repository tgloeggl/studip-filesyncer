/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class LocalSyncFile implements SyncerFile {

    private File file;
    
    public LocalSyncFile(File file) {
        this.file = file;
    }

    @Override
    public String getMD5() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public Date getTimestamp() {
        return new Date(file.lastModified());
    }

    @Override
    public InputStream getStream() throws MalformedURLException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
