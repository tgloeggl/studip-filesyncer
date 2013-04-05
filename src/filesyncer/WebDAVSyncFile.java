/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import com.googlecode.sardine.DavResource;
import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class WebDAVSyncFile implements SyncerFile {

    private DavResource res;
    private String full_path;
    private String username;
    private String password;

    public WebDAVSyncFile(DavResource res, String full_path, String username, String password) {
        this.res = res;
        this.full_path = full_path;
        this.username = username;
        this.password = password;
    }
    
    @Override
    public String getMD5() {
        /*
        Sardine sardine = SardineFactory.begin();
        sardine.setCredentials(this.username, this.password);
        sardine.disableCompression();
        System.out.println("[WEBDAV - MD5] getting md5-hash for file");
        try {
            Map<String,String> newProps = new HashMap<String, String>();
            newProps.put("md5", "blubbersoup");
            sardine.setCustomProps(this.full_path, newProps, null);
        } catch (IOException ioe) {
            System.out.println("[WEBDAV ##1] " + ioe.getMessage());
        }
        
        try {
            List<DavResource> resources = sardine.getResources(this.full_path);
            Map<String,String> props = resources.get(0).getCustomProps();
            System.out.println(props.get("md5"));
            System.out.println(props);
        } catch (IOException ioe) {
            System.out.println("[WEBDAV ##2] " + ioe.getMessage());
        }
        
        
        return "";
        */
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
        Sardine sardine = SardineFactory.begin();
        sardine.setCredentials(this.username, this.password);
        sardine.disableCompression();
        return sardine.get(this.full_path);

        /*
        System.out.println("[STREAM - HREF] " + this.full_path + this.res.getName());
        return new URL(this.full_path + this.res.getName()).openStream();
        */
    }

    @Override
    public long getLength() {
        return res.getContentLength();
    }
    
}