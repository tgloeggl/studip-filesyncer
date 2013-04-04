/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import com.googlecode.sardine.DavResource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class WebDAVSyncFile implements SyncerFile {

    private DavResource res;
    private String full_path;
    private InputStream is;

    public WebDAVSyncFile(DavResource res, InputStream is) {
        this.res = res;
        this.is  = is;
        // this.full_path = full_path;
    }
    
    @Override
    public String getMD5() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPath() {
        return res.getPath();
    }

    @Override
    public Date getTimestamp() {
        return res.getModified();
    }

    @Override
    public InputStream getStream() throws MalformedURLException, IOException {
        return this.is;
        /*
        System.out.println("[STREAM - HREF] " + this.full_path + this.res.getName());
        return new URL(this.full_path + this.res.getName()).openStream();
        */
    }
    
}
