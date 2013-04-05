/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public interface SyncerFile {
    public String getMD5();
    public String getPath();
    public Date getTimestamp();
    public long getLength();
    public InputStream getStream() throws MalformedURLException, IOException;
}
