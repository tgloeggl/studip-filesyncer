/*
 * Enable Debugging: VM Options in Project-Properties -> Run:
 * -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog -Dorg.apache.commons.logging.simplelog.showdatetime=true -Dorg.apache.commons.logging.simplelog.log.org.apache.http=DEBUG -Dorg.apache.commons.logging.simplelog.log.org.apache.http.wire=ERROR
 */
package filesyncer;

import com.googlecode.sardine.DavResource;
import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class WebDAVSyncProvider implements SyncerResource {

    private String base_path;
    private String dav_path;
    private String username;
    private String password;
    
    public WebDAVSyncProvider(String base_path, String dav_path, String username, String password) {
        this.base_path = base_path;
        this.dav_path  = dav_path;
        this.username  = username;
        this.password  = password;
    }
    
    @Override
    public List<SyncerFile> getFiles() {
        return this.getFilesRecursive(this.dav_path);
    }
    
    public String parseUrl(String s) {
        try {
            URL u = new URL(s);
            return new URI(
                u.getProtocol(),
                u.getAuthority(),
                u.getPath(),
                u.getQuery(),
                u.getRef()).
                toURL().toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebDAVSyncProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(WebDAVSyncProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    private List<SyncerFile> getFilesRecursive(String dav_path) {
        /*
        String path = this.base_path;

        String[] path_parts = dav_path.split("/");
        for (String part: path_parts) {
            path += URLEncoder.encode(part) + "/";
        }
        */
        
        String path = "";

        path = this.parseUrl(this.base_path + dav_path).toString();

        List<SyncerFile> files = new ArrayList<SyncerFile>();

        Sardine sardine = SardineFactory.begin();
        sardine.setCredentials(this.username, this.password);
        sardine.disableCompression();

        try {
            System.out.println("[LOOKUP] looking up path: " + path);

            java.util.List<DavResource> resources = sardine.list(path, 1);
            
            System.out.println(resources);

            for (DavResource res : resources) {
                //files.add(new WebDAVSyncFile(path, timestamp))

                // System.out.println("##" + res.toString());
                if (!res.toString().equals(dav_path)) {
                    if (!res.isDirectory()) {
                        System.out.println("[INFO] File found: " + path + res.getName());
                        try {
                            files.add(new WebDAVSyncFile(res, sardine.get(this.parseUrl(path + res.getName()))));
                        } catch (IOException ioe) {
                            System.out.println("[ERROR] " + ioe.getMessage());
                        }
                    } else {
                        files.addAll(this.getFilesRecursive(res.toString()));
                    }
                }
            } 
        } catch (IOException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        return files;        
    }

    @Override
    public SyncerFile findByPath(String path) throws FileNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putFile(SyncerFile file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canWrite() {
        return false;
    }
    
}
